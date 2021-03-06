package messages.repository.message;

import java.sql.Timestamp;
import java.util.List;

import messages.orm.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Message service manager, uses Hibernate to find, create, update, remove
 * messages.
 */
@Repository
public class HibernateMessageService implements MessageService {
	
	private Log log = LogFactory.getLog(this.getClass());

	private SessionFactory sessionFactory;

	/**
	 * Creates a new Hibernate message service.
	 * 
	 * @param sessionFactory the Hibernate session factory
	 */
	public HibernateMessageService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @see messages.repository.message.MessageService#getAllMessages()
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessages() {
		return this.getCurrentSession().createQuery("from Message").list();
	}

	/**
	 * @see messages.repository.message.MessageService#getMessage(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Message getMessage(Integer id) {
		return (Message) this.getCurrentSession().load(Message.class, id);
	}

	/**
	 * @see messages.repository.message.MessageService#create(messages.orm.Message)
	 */
	@Transactional
	public void create(Message message) {
		log.info("Create a message: " + message);
		String principal = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		Assert.notNull(principal);
		message.setPrincipal(principal);
		message.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.getCurrentSession().persist(message);
	}

	/**
	 * @see messages.repository.message.MessageService#remove(messages.orm.Message)
	 */
	@Transactional
	public void remove(Message message) {
		log.info("Remove a message: " + message);
		this.getCurrentSession().delete(message);
		this.getCurrentSession().flush();
	}

	/**
	 * @see messages.repository.message.MessageService#update(messages.orm.Message)
	 */
	@Transactional
	public void update(Message message) {
		log.info("Update a message: " + message);
		String principal = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		Assert.notNull(principal);
		message.setPrincipal(principal);
		message.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.getCurrentSession().update(message);
	}

	/**
	 * Returns the session associated with the ongoing message transaction.
	 * 
	 * @return the transactional session
	 */
	protected Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
}