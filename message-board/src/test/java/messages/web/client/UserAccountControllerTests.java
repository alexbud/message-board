package messages.web.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import messages.orm.UserAccount;
import messages.orm.UserAuthority;
import messages.orm.UserRole;
import messages.repository.user.UserAccountService;
import messages.web.UserAccountController;
import nl.captcha.Captcha;
import nl.captcha.Captcha.Builder;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;

/**
 * An integration abstract test base for {@link UserAccountController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-config.xml" })
@Transactional
public abstract class UserAccountControllerTests {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private MailSender mailSender;

	private UserAccountController controller;
	
	private Captcha captcha;
	
	private MockHttpSession session;

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		this.controller = new UserAccountController(this.userAccountService, this.mailSender);
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
		
		session = new MockHttpSession();
		
		 //captcha
        Builder builder = new Captcha.Builder(100, 100);
        ColoredEdgesWordRenderer coloredEdgesWordRenderer = new ColoredEdgesWordRenderer();
        builder.addText(coloredEdgesWordRenderer);
        this.captcha = builder.build();
        this.captcha.getImage();
        session.setAttribute(Captcha.NAME, this.captcha);
	}

	/**
	 * Test for {@link UserAccountController#userDetails(String, org.springframework.ui.Model)}.
	 */
	@Test
	public void testHandleUserDetailsRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		final String username = "admin";
		String viewName = this.controller.userDetails(username, model);
		UserAccount user = (UserAccount) model.get("user");
		assertEquals("userDetails", viewName);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertNotNull(user.getTimestamp());
	}

	/**
	 * Test for {@link UserAccountController#getEditUser(String, org.springframework.ui.Model)}.
	 */
	@Test
	public void testHandleGetEditUserRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		final String username = "admin";
		String viewName = this.controller.getEditUser(username, model);
		UserAccount user = (UserAccount) model.get("user");
		assertEquals("userForm", viewName);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertNotNull(user.getTimestamp());
	}

	/**
	 * Test for {@link UserAccountController#getCreateUser(String, org.springframework.ui.Model)}.
	 */
	@Test
	public void testHandleGetCreateUserRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String viewName = this.controller.getCreateUser(model);
		UserAccount user = (UserAccount) model.get("user");
		assertEquals("userForm", viewName);
		assertNotNull(user);
		assertNull(user.getPassword());
	}
	
	/**
	 * Test for {@link UserAccountController#postCreateUser(UserACcount, org.springframework.validation.BindingResult)}.
	 */
	@Test
	public void testHandlePostCreateUserRequestWrongCaptcha() throws NoSuchAlgorithmException {
		UserAccount user = new UserAccount("user", "password", "password", UserRole.ROLE_MEMBER);
		// no captcha for the user
		assertNull(user.getCaptcha());
		String viewName = this.controller.postCreateUser(user, new BeanPropertyBindingResult(user, "user"), this.session);
		assertEquals("userForm", viewName);
		assertNotNull(user);
		assertNotNull(user.getPassword());
	}

	/**
	 * Test for {@link UserAccountController#postCreateUser(UserACcount, org.springframework.validation.BindingResult)}.
	 */
	@Test
	public void testHandlePostCreateUserRequest() throws NoSuchAlgorithmException {
		UserAccount user = new UserAccount("user", "password", "password", UserRole.ROLE_MEMBER);
		user.setCaptcha(this.captcha.getAnswer());
		String viewName = this.controller.postCreateUser(user, new BeanPropertyBindingResult(user, "user"), this.session);
		assertEquals("redirect:/board/users/userDetails?username=" + user.getUsername(), viewName);
		assertNotNull(user);
		assertNotNull(user.getPassword());
		// test
		ExtendedModelMap model = new ExtendedModelMap();
		this.controller.userDetails(user.getUsername(), model);
		UserAccount retrievedUser = (UserAccount) model.get("user");
		assertEquals(user.getUsername(), retrievedUser.getUsername());
		assertEquals(user.getPassword(), retrievedUser.getPassword());
		assertEquals(user.getAuthorities(), retrievedUser.getAuthorities());
		assertNotNull(user.getTimestamp());
	}

	/**
	 * Test for {@link UserAccountController#postEditUser(UserAccount, org.springframework.validation.BindingResult)}.
	 */
	@Test
	public void testHandlePostEditUserRequest() throws NoSuchAlgorithmException {
		// let's create a user account first
		UserAccount user = new UserAccount("user", "password", "password", UserRole.ROLE_MEMBER);
		this.controller.postCreateUser(user, new BeanPropertyBindingResult(user, "user"), this.session);
		assertNotNull(user.getPassword());
		// update some field
		final UserRole newAuthority = UserRole.ROLE_VIEWER;
		user.addAuthority(newAuthority);
		String viewName = this.controller.postEditUser(user, new BeanPropertyBindingResult(user, "user"));
		assertEquals("redirect:/board/users/userDetails?username=" + user.getUsername(), viewName);
		assertNotNull(user);
		assertNotNull(user.getPassword());
		assertNotNull(user.getTimestamp());
		// test
		ExtendedModelMap model = new ExtendedModelMap();
		this.controller.userDetails(user.getUsername(), model);
		UserAccount retrievedUser = (UserAccount) model.get("user");
		List<UserAuthority> authorities = retrievedUser.getAuthorities();
		assertNotNull(authorities);
		assertEquals(newAuthority, authorities.get(authorities.size() - 1).getAuthority());
	}

	/**
	 * Test for {@link UserAccountController#removeUser(String, org.springframework.ui.Model)}.
	 */
	@Test
	public void testHandleRemoveUserRequest() throws NoSuchAlgorithmException {
		final int initialUserSize = this.userAccountService.getAllUsers().size();
		assertEquals(4, initialUserSize);
		// let's create a user first
		UserAccount user = new UserAccount("newuser", "newpassword", "newpassword", UserRole.ROLE_MEMBER);
		user.setCaptcha(this.captcha.getAnswer());
		this.controller.postCreateUser(user, new BeanPropertyBindingResult(user, "user"), this.session);
		assertNotNull(user.getPassword());
		final int size = this.userAccountService.getAllUsers().size();
		assertEquals(initialUserSize + 1, size);
		// remove user
		String viewName = this.controller.removeUser(user.getUsername());
		assertEquals("redirect:/board/users/userSummary", viewName);
		assertEquals(initialUserSize, this.userAccountService.getAllUsers().size());
		// still exists?
		assertNull(this.userAccountService.findByUsername(user.getUsername()));
		// remove another user
		this.controller.removeUser("admin");
	}

	/**
	 * Test for {@link UserAccountController#userSummary(org.springframework.ui.Model)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testHandleUserSummaryRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String viewName = this.controller.userSummary(model);
		List<UserAccount> users = (List<UserAccount>) model.get("users");
		assertEquals("userSummary", viewName);
		assertNotNull(users);
		assertEquals(4, users.size());
		assertEquals("admin", users.get(0).getUsername());
	}
}
