package messages.repository.user;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import messages.orm.UserAccount;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Manages access to user account information.
 */
public interface UserAccountService {

	/**
	 * Get all user accounts in the system.
	 * 
	 * @return all user accounts
	 */
	@RolesAllowed("ROLE_ADMIN")
	public List<UserAccount> getAllUsers();

	/**
	 * Find a user by its username.
	 * 
	 * @param username
	 * @return the user account
	 */
	public UserAccount findByUsername(String username);

	/**
	 * Persists a new user.
	 * 
	 * @param user the new user to persist.
	 * @throws NoSuchAlgorithmException
	 */
	public void create(UserAccount user) throws NoSuchAlgorithmException;

	/**
	 * Removes a user.
	 * 
	 * @param user the user to remove.
	 */
	@RolesAllowed("ROLE_ADMIN")
	public void remove(UserAccount user);

	/**
	 * Takes a changed user and persists any changes made to it.
	 * 
	 * @param user The user with changes
	 * @throws NoSuchAlgorithmException
	 */
	@PreAuthorize("(hasRole('ROLE_ADMIN')) or (#user.username == principal.username)")
	public void update(UserAccount user) throws NoSuchAlgorithmException;
}
