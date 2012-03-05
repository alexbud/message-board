package messages.repository.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

import messages.orm.UserAccount;
import messages.orm.UserAuthority;
import messages.orm.UserRole;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * An integration test for {@link UserAccountService}. Test uses in-memory
 * database.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-config.xml" })
public abstract class UserAccountServiceTest {

	@Autowired
	private UserAccountService userAccountService;

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
	}

	/**
	 * Test for {@link UserAccountService#getAllUsers()}.
	 */
	@Test
	@Transactional
	public void testGetAllUsers() {
		List<UserAccount> users = this.userAccountService.getAllUsers();
		assertNotNull(users.get(0).getAuthorities());
		assertTrue(users.size() > 0);
	}

	/**
	 * Test for {@link UserAccountService#findByUsername(String)}.
	 */
	@Test
	@Transactional
	public void testFindByUsername() {
		final String username = "admin";
		UserAccount account = this.userAccountService.findByUsername(username);
		assertNotNull(account);
		assertNotNull(account.getTimestamp());
	}

	/**
	 * Test for {@link UserAccountService#update(UserAccount)}.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	@Transactional
	public void testUpdateAccount() throws NoSuchAlgorithmException {
		final String username = "admin";
		UserAccount oldAccount = this.userAccountService.findByUsername(username);
		Timestamp oldtimestamp = oldAccount.getTimestamp();
		assertNotNull(oldtimestamp);
		final List<UserAuthority> authorities = oldAccount.getAuthorities();
		assertNotNull(authorities);
		final UserRole newAuthority = UserRole.ROLE_VIEWER;
		oldAccount.addAuthority(UserRole.ROLE_VIEWER);
		final String passwordOld = oldAccount.getPassword();
		oldAccount.setPasswordConfirm(passwordOld);
		this.userAccountService.update(oldAccount);
		UserAccount newAccount = this.userAccountService.findByUsername(username);
		assertNotSame("new password wasn't hashed", passwordOld, newAccount.getPassword());
		Object[] newAuthorities = newAccount.getAuthorities().toArray();
		assertEquals("Did not persist the authorities change", newAuthority,
				((UserAuthority) newAuthorities[newAuthorities.length - 1]).getAuthority());
		Timestamp newtimestamp = newAccount.getTimestamp();
		assertNotNull(newtimestamp);
		assertFalse(newtimestamp.equals(oldtimestamp));
	}

	/**
	 * Test for {@link UserAccountService#update(UserAccount)}. Authorization
	 * test for update and delete a account.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	@Transactional
	public void testUpdateDeleteAccountNotAuthorized() throws NoSuchAlgorithmException {
		// ROLE_ADMIN user
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
		// create account by admin user
		UserAccount account = new UserAccount();
		account.setUsername("name");
		account.setPassword("password");
		account.setPasswordConfirm("password");
		account.addAuthority(UserRole.ROLE_MEMBER);
		this.userAccountService.create(account);

		// ROLE_MEMBER user can't update another account
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("member", "password"));
		UserAccount retrievedAccount = this.userAccountService.findByUsername("admin");
		retrievedAccount.setPassword("New password");
		retrievedAccount.setPasswordConfirm("New password");
		try {
			this.userAccountService.update(retrievedAccount);
			fail("Should throw AccessDenied exception because member is not allowed to update an account");
		} catch (AccessDeniedException e) {
			// as expected
		}
		// ROLE_MEMBER user can't delete an account
		try {
			this.userAccountService.remove(retrievedAccount);
			fail("Should throw AccessDenied exception because member is not allowed to remove an account");
		} catch (AccessDeniedException e) {
			// as expected
		}
		// ROLE_ADMIN user can update and remove all accounts
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
		this.userAccountService.update(retrievedAccount);
		this.userAccountService.remove(retrievedAccount);
	}

	/**
	 * Test for {@link UserAccountService#create(UserAccount)}.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	@Transactional
	public void testCreateAccount() throws NoSuchAlgorithmException {
		UserAccount account = new UserAccount();
		account.setUsername("name");
		final String password = "password";
		account.setPassword(password);
		account.setPasswordConfirm("password");
		account.addAuthority(UserRole.ROLE_MEMBER);
		this.userAccountService.create(account);
		assertEquals(5, this.userAccountService.getAllUsers().size());
		String passwordHashed = this.userAccountService.findByUsername(account.getUsername()).getPassword();
		assertNotNull(passwordHashed);
		assertNotSame("new password wasn't hashed", password, passwordHashed);
		assertNotNull(account.getTimestamp());
		// test mandatory field validation
		account = new UserAccount();
		try {
			this.userAccountService.create(account);
			fail("should trigger an exception");
		} catch (Exception e) {
			// as expected
		}
		account.setUsername("username");
		account.setPassword("password");
		account.setPasswordConfirm("password");
		account.addAuthority(UserRole.ROLE_MEMBER);
		this.userAccountService.create(account);
	}

	/**
	 * Test for {@link UserAccountService#remove(UserAccount)}.
	 */
	@Test
	@Transactional
	public void testRemoveAccount() {
		final String username = "admin";
		UserAccount account = this.userAccountService.findByUsername(username);
		assertEquals(username, account.getUsername());
		assertTrue(account.getAuthorities().size() > 1);
		this.userAccountService.remove(account);
		try {
			// already removed
			this.userAccountService.findByUsername(username).getPassword();
			fail("should throw an exception");
		} catch (Exception e) {
			// as expected
		}
	}
}
