package messages.orm;

/**
 * Enumeration for user authority roles. See {@link UserAuthority}.
 */
public enum UserRole {

	/**
	 * administrator
	 */
	ROLE_ADMIN,

	/**
	 * member, registered user
	 */
	ROLE_MEMBER,

	/**
	 * viewer, read-only
	 */
	ROLE_VIEWER;

}
