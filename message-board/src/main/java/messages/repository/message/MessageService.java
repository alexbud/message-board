package messages.repository.message;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import messages.orm.Message;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Manages access to message information.
 */
public interface MessageService {

	/**
	 * Get all messages in the system
	 * 
	 * @return all messages
	 */
	@PreAuthorize("isAuthenticated()")
	public List<Message> getAllMessages();

	/**
	 * Find a message by its number.
	 * 
	 * @param id
	 *            the message id
	 * @return the message
	 */
	@PreAuthorize("isAuthenticated()")
	public Message getMessage(Integer id);

	/**
	 * Persists a new message.
	 * 
	 * @param message
	 *            The new message to persist
	 */
	@RolesAllowed("ROLE_MEMBER")
	public void create(Message message);

	/**
	 * Removes a message.
	 * 
	 * @param message
	 *            The message to remove
	 */
	@PreAuthorize("(hasRole('ROLE_ADMIN')) or (#message.principal == principal.username)")
	public void remove(Message message);

	/**
	 * Takes a changed message and persists any changes made to it.
	 * 
	 * @param message
	 *            The message with changes
	 */
	@PreAuthorize("(hasRole('ROLE_ADMIN')) or (#message.principal == principal.username)")
	public void update(Message message);
}
