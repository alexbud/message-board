package messages.repository;

import java.util.List;

import messages.orm.Message;

/**
 * Manages access to message information.
 */
public interface MessageService {

	/**
	 * Get all messages in the system
	 * 
	 * @return all messages
	 */
	public List<Message> getAllMessages();

	/**
	 * Find a message by its number.
	 * 
	 * @param id
	 *            the message id
	 * @return the message
	 */
	public Message getMessage(Integer id);

	/**
	 * Persists a new message.
	 * 
	 * @param message
	 *            The new message to persist
	 */
	public void create(Message message);

	/**
	 * Removes a message.
	 * 
	 * @param message
	 *            The new message to persist
	 */
	public void remove(Message message);

	/**
	 * Takes a changed message and persists any changes made to it.
	 * 
	 * @param message
	 *            The message with changes
	 */
	public void update(Message message);
}
