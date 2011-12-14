package messages.orm;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import messages.web.Url;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO message entity class.
 */
@Entity
@Table(name = "T_MESSAGE")
public class Message {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer entityId;

	@Column(name = "TS")
	private Timestamp timestamp;

	@Column(name = "TITLE")
	@NotEmpty(message = "Title is a mandatory field")
	@Length(max = 50, message = "Max length for title field is 50")
	private String title;

	@Column(name = "CONTENT")
	@Length(max = 140, message = "Max length for content field is 140")
	private String content;

	@Column(name = "URL")
	@Url(message = "Invalid URL. Must start with http://")
	private String url;

	@Column(name = "PRINCIPAL")
	private String principal;

	/**
	 * 
	 */
	public Message() {
	}

	/**
	 * @param title
	 */
	public Message(String title) {
		this.title = title;
	}

	/**
	 * Returns the entity identifier used to internally distinguish this entity
	 * among other entities of the same type in the system. Should typically
	 * only be called by privileged data access infrastructure code such as an
	 * Object Relational Mapper (ORM) and not by application code.
	 * 
	 * @return the internal entity identifier
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * Sets the internal entity identifier - should only be called by privileged
	 * data access code (repositories that work with an Object Relational Mapper
	 * (ORM)). Should never be set by application code explicitly.
	 * 
	 * @param entityId
	 *            the internal entity identifier
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return principal the principal creator
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * Sets the principal creator for this message.
	 * 
	 * @param principal
	 *            the principal creator
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * @return timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the time of message creation or last update.
	 * 
	 * @param timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return title The title for this message
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title for this message.
	 * 
	 * @param title
	 *            The title for this message
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return content The content for this message
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content for this message.
	 * 
	 * @param content
	 *            The content for this message
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return url The url for this message
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url for this message.
	 * 
	 * @param url
	 *            The url for this message
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
		return "Id = " + entityId + ", time = " + timestamp + ", principal = "
				+ principal + ", title = " + title + ", content = " + content
				+ ", url = " + url;
	}
}