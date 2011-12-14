package messages.repository.user;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import messages.orm.UserAccount;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * User account service manager, uses JPA to find, create, update, remove user
 * accounts.
 */
@Repository
public class JpaUserAccountService implements UserAccountService {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @see messages.repository.user.UserAccountService#getAllUsers()
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserAccount> getAllUsers() {
		return this.entityManager.createQuery("from UserAccount")
				.getResultList();
	}

	/**
	 * @see messages.repository.user.UserAccountService#findByUsername(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public UserAccount findByUsername(String username) {
		return (UserAccount) this.entityManager.find(UserAccount.class,
				username);
	}

	/**
	 * @see messages.repository.user.UserAccountService#create(messages.orm.UserAccount)
	 */
	@Transactional
	public void create(UserAccount user) {
		user.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.entityManager.persist(user);
	}

	/**
	 * @see messages.repository.user.UserAccountService#remove(messages.orm.UserAccount)
	 */
	@Transactional
	public void remove(UserAccount user) {
		this.entityManager.remove(user);
		this.entityManager.flush();
	}

	/**
	 * @see messages.repository.user.UserAccountService#update(messages.orm.UserAccount)
	 */
	@Transactional
	public void update(UserAccount user) {
		user.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.entityManager.merge(user);
	}
}