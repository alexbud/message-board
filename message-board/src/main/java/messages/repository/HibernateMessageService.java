package messages.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import messages.orm.Message;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.access.prepost.PreAuthorize;
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

	private SessionFactory sessionFactory;

	/**
	 * Creates a new Hibernate message service.
	 * 
	 * @param sessionFactory
	 *            the Hibernate session factory
	 */
	public HibernateMessageService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @see messages.repository.MessageService#getAllMessages()
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessages() {
		return this.getCurrentSession().createQuery("from Message").list();
	}

	/**
	 * @see messages.repository.MessageService#getMessage(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Message getMessage(Integer id) {
		return (Message) this.getCurrentSession().load(Message.class, id);
	}

	/**
	 * @see messages.repository.MessageService#create(messages.orm.Message)
	 */
	@Transactional
	@RolesAllowed("ROLE_MEMBER")
	public void create(Message message) {
		String principal = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		Assert.notNull(principal);
		message.setPrincipal(principal);
		message.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.getCurrentSession().persist(message);
	}

	/**
	 * @see messages.repository.MessageService#remove(messages.orm.Message)
	 */
	@Transactional
	@PreAuthorize("(hasRole('ROLE_ADMIN')) or (#message.principal == principal.username)")
	public void remove(Message message) {
		this.getCurrentSession().delete(message);
		this.getCurrentSession().flush();
	}

	/**
	 * @see messages.repository.MessageService#update(messages.orm.Message)
	 */
	@Transactional
	@PreAuthorize("(hasRole('ROLE_ADMIN')) or (#message.principal == principal.username)")
	public void update(Message message) {
		String principal = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		Assert.notNull(principal);
		message.setPrincipal(principal);
		message.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.getCurrentSession().update(message);
	}

	/**
	 * Returns the session associated with the ongoing reward transaction.
	 * 
	 * @return the transactional session
	 */
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}