package messages.repository.user;

import java.sql.Timestamp;
import java.util.List;

import messages.orm.UserAccount;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * User account service manager, uses Hibernate to find, create, update, remove
 * user accounts.
 */
@Repository
public class HibernateUserAccountService implements UserAccountService {

	private SessionFactory sessionFactory;

	/**
	 * Creates a new Hibernate user account service.
	 * 
	 * @param sessionFactory
	 *            the Hibernate session factory
	 */
	public HibernateUserAccountService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @see messages.repository.user.UserAccountService#getAllUsers()
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserAccount> getAllUsers() {
		return this.getCurrentSession().createQuery("from UserAccount").list();
	}

	/**
	 * @see messages.repository.user.UserAccountService#findByUsername(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public UserAccount findByUsername(String username) {
		return (UserAccount) this.getCurrentSession().load(UserAccount.class,
				username);
	}

	/**
	 * @see messages.repository.user.UserAccountService#create(messages.orm.UserAccount)
	 */
	@Transactional
	public void create(UserAccount user) {
		user.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.getCurrentSession().persist(user);
	}

	/**
	 * @see messages.repository.user.UserAccountService#remove(messages.orm.UserAccount)
	 */
	@Transactional
	public void remove(UserAccount user) {
		this.getCurrentSession().delete(user);
		this.getCurrentSession().flush();
	}

	/**
	 * @see messages.repository.user.UserAccountService#update(messages.orm.UserAccount)
	 */
	@Transactional
	public void update(UserAccount user) {
		user.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.getCurrentSession().update(user);
	}

	/**
	 * Returns the session associated with the ongoing user account transaction.
	 * 
	 * @return the transactional session
	 */
	protected Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
}