package messages.orm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO entity user account class.
 */
@Entity
@Table(name = "T_USER")
public class UserAccount {

	@Id
	@Column(name = "USERNAME")
	@NotEmpty(message = "Username is a mandatory field")
	private String username;

	@Column(name = "PASSWORD")
	@Size(min = 6, message = "Password is a mandatory field. Length must be at least 6 characters")
	private String password;

	@Column(name = "TS")
	private Timestamp timestamp;

	@Transient
	private String passwordConfirm;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USERNAME")
	private List<UserAuthority> authorities;

	/**
	 * 
	 */
	public UserAccount() {
	}

	/**
	 * @param username
	 * @param password
	 * @param authorities
	 */
	public UserAccount(String username, String password, String passwordConfirm, UserRole authority) {
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.addAuthority(authority);
	}

	/**
	 * @return timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the time of account creation or last update.
	 * 
	 * @param timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return password confirm
	 */
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	/**
	 * @param passwordConfirm
	 */
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	/**
	 * @return list of authorities
	 */
	public List<UserAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authority
	 */
	public void addAuthority(UserRole authority) {
		if (this.authorities == null) {
			this.authorities = new ArrayList<UserAuthority>();
		}
		UserAuthority userAuthority = new UserAuthority(this.getUsername(), authority);
		this.authorities.add(userAuthority);
	}

	/**
	 * @return <code>true</code> if password and password confirmation matches
	 */
	@AssertTrue(message = "Confirm password field should be equal than password field")
	boolean isPasswordConfirm() {
		if (this.password == null) {
			return true;
		}
		return this.password.equals(this.passwordConfirm);
	}

	/**
	 * Generates a hash from password to be stored as a password in database.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	@PrePersist
	public void preparePasswordSave() throws NoSuchAlgorithmException {
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		algorithm.reset();
		algorithm.update(this.getPassword().getBytes());
		byte[] digest = algorithm.digest();
		byte[] encoded = Base64.encodeBase64(digest);
		String value = new String(encoded);
		this.setPassword(value);
		this.setPasswordConfirm(value);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Timestamp: ").append(this.getTimestamp()).append(", ");
		sb.append("UserName: ").append(this.getUsername()).append(", ");
		sb.append("Password: ").append(this.getPassword()).append(", ");
		sb.append("Password confirm: ").append(this.getPasswordConfirm()).append(", ");
		sb.append("Authorities: ").append(this.getAuthorities());
		return sb.toString();
	}
}
