package messages.orm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * Tests for {@link UserAccount}.
 */
public class UserAccountTest {

	/**
	 * Test for {@link UserAccount#isPasswordConfirm()}.
	 */
	@Test
	public void testIsPasswordConfirm() {
		UserAccount account = new UserAccount();
		assertNull(account.getPassword());
		assertTrue(account.isPasswordConfirm());
		final String password = "password";
		account.setPassword(password);
		assertNotNull(account.getPassword());
		assertFalse(account.isPasswordConfirm());
		account.setPasswordConfirm("siygsyigd");
		assertFalse(account.isPasswordConfirm());
		account.setPasswordConfirm(password);
		assertTrue(account.isPasswordConfirm());
	}

	/**
	 * Test for {@link UserAccount#preparePasswordSave()}.
	 */
	@Test
	public void testPreparePasswordSave() throws NoSuchAlgorithmException {
		UserAccount account = new UserAccount();
		final String password = "password";
		account.setPassword(password);
		account.preparePasswordSave();
		assertNotNull(account.getPassword());
		assertTrue(account.getPassword().length() > 25);
		assertFalse(account.getPassword().equals(password));
		assertEquals(account.getPassword(), account.getPasswordConfirm());
	}
}
