package messages.repository.user;

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
	@RolesAllowed("ROLE_MEMBER")
	public UserAccount findByUsername(String username);

	/**
	 * Persists a new user.
	 * 
	 * @param user
	 *            The new user to persist.
	 */
	public void create(UserAccount user);

	/**
	 * Removes a user.
	 * 
	 * @param user
	 *            The user to remove.
	 */
	@RolesAllowed("ROLE_ADMIN")
	public void remove(UserAccount user);

	/**
	 * Takes a changed user and persists any changes made to it.
	 * 
	 * @param user
	 *            The user with changes
	 */
	@PreAuthorize("(hasRole('ROLE_ADMIN')) or (#user.username == principal.username)")
	public void update(UserAccount user);
}
