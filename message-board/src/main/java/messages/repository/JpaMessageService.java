package messages.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import messages.orm.Message;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Message service manager, uses JPA to find, create, update, remove messages.
 */
@Repository
public class JpaMessageService implements MessageService {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @see messages.repository.MessageService#getAllMessages()
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessages() {
		return this.entityManager.createQuery("from Message").getResultList();
	}

	/**
	 * @see messages.repository.MessageService#getMessage(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Message getMessage(Integer id) {
		return (Message) this.entityManager.find(Message.class, id);
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
		this.entityManager.persist(message);
	}

	/**
	 * @see messages.repository.MessageService#remove(messages.orm.Message)
	 */
	@Transactional
	@PreAuthorize("(hasRole('ROLE_ADMIN')) or (#message.principal == principal.username)")
	public void remove(Message message) {
		this.entityManager.remove(message);
		this.entityManager.flush();
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
		this.entityManager.merge(message);
	}
}