package messages.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * POJO entity user authorities class.
 * 
 * @See {@link UserAccount}
 */
@Entity
@Table(name = "T_AUTHORITIES")
public class UserAuthority {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "AUTHORITY")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Authority must be set")
	private UserRole authority;

	/**
	 * 
	 */
	public UserAuthority() {
	}

	/**
	 * @param authority
	 */
	public UserAuthority(String username, UserRole authority) {
		this.username = username;
		this.authority = authority;
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
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
	 * @return authority
	 */
	public UserRole getAuthority() {
		return authority;
	}

	/**
	 * @param authority
	 */
	public void setAuthority(UserRole authority) {
		this.authority = authority;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Username: ").append(this.getUsername()).append(", ");
		sb.append("Authority: ").append(this.getAuthority());
		return sb.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAuthority other = (UserAuthority) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
