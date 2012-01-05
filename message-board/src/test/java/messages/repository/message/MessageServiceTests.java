package messages.repository.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.List;

import messages.orm.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * An integration test for {@link MessageService}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-config.xml" })
public abstract class MessageServiceTests {

	@Autowired
	private MessageService messageService;

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
	}

	/**
	 * Test for {@link MessageService#getAllMessages()}.
	 */
	@Test
	public void testGetAllMessages() {
		List<Message> messages = this.messageService.getAllMessages();
		assertEquals("Wrong number of messages", 4, messages.size());
	}

	/**
	 * Test for {@link MessageService#getMessage()}.
	 */
	@Test
	@Transactional
	public void testGetMessage() {
		Message message = this.messageService.getMessage(Integer.valueOf(0));
		// assert the returned message contains what you expect
		assertNotNull("message should never be null", message);
		assertEquals("wrong entity id", Integer.valueOf(0),
				message.getEntityId());
		assertEquals("wrong title", "message one", message.getTitle());
		assertEquals("wrong content", "Hello message 1", message.getContent());
		assertEquals("wrong sender", "admin", message.getPrincipal());
		assertEquals("wrong url", "http://www.hotmail.com/one",
				message.getUrl());
		assertNotNull(message.getTimestamp());
	}

	/**
	 * Test for {@link MessageService#update(Message)}.
	 */
	@Test
	@Transactional
	public void testUpdateMessage() {
		Message oldMessage = this.messageService.getMessage(Integer.valueOf(0));
		Timestamp oldtimestamp = oldMessage.getTimestamp();
		assertNotNull(oldtimestamp);
		final String newTitle = "New title";
		oldMessage.setTitle(newTitle);
		this.messageService.update(oldMessage);
		Message newMessage = this.messageService.getMessage(Integer.valueOf(0));
		assertEquals("Did not persist the name change", newTitle,
				newMessage.getTitle());
		Timestamp newtimestamp = newMessage.getTimestamp();
		assertNotNull(newtimestamp);
		assertFalse(newtimestamp.equals(oldtimestamp));
	}

	/**
	 * Test for {@link MessageService#update(Message)}. Authorization test for
	 * update and delete a message.
	 */
	@Test
	@Transactional
	public void testUpdateDeleteMessageNotAuthorized() {
		// ROLE_MEMBER user
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("member", "password"));
		// create message by member user
		Message message = new Message();
		final String title = "title";
		message.setTitle(title);
		final String content = "content";
		message.setContent(content);
		final String url = "http://url";
		message.setUrl(url);
		this.messageService.create(message);
		assertEquals("member", message.getPrincipal());

		// other ROLE_MEMBER user can't update nad delete
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("member2", "password"));
		Message retrivedMessage = this.messageService.getMessage(message
				.getEntityId());
		retrivedMessage.setTitle("New Title");
		try {
			this.messageService.update(retrivedMessage);
			fail("Should throw AccessDenied exception because member2 is not creator of an updated message");
		} catch (AccessDeniedException e) {
			// as expected
		}
		try {
			this.messageService.remove(retrivedMessage);
			fail("Should throw AccessDenied exception because member2 is not creator of an updated message");
		} catch (AccessDeniedException e) {
			// as expected
		}
		// ROLE_ADMIN user can update and remove
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
		this.messageService.update(retrivedMessage);
		this.messageService.remove(retrivedMessage);
	}

	/**
	 * Test for {@link MessageService#create(Message)}.
	 */
	@Test
	@Transactional
	public void testCreateMessage() {
		final Integer id = 4;
		try {
			// initial amount of test entities is 4
			this.messageService.getMessage(id).getEntityId();
			fail("should throw an exception");
		} catch (Exception e) {
			// as expected
		}
		Message message = new Message();
		final String title = "title";
		message.setTitle(title);
		final String content = "content";
		message.setContent(content);
		final String url = "http://url";
		message.setUrl(url);
		this.messageService.create(message);
		assertEquals(5, this.messageService.getAllMessages().size());
		assertNotNull(this.messageService.getMessage(message.getEntityId())
				.getEntityId());
		assertNotNull(message.getTimestamp());
		// test mandatory title field validation
		message = new Message();
		try {
			this.messageService.create(message);
			fail("should trigger an exception");
		} catch (Exception e) {
			// as expected
		}
		message.setTitle("title");
		this.messageService.create(message);
	}

	/**
	 * Test for {@link MessageService#create(Message)}.
	 */
	@Test
	@Transactional
	@ExpectedException(AccessDeniedException.class)
	public void testCreateMessageForbiddenForViewer() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("viewer", "password"));
		this.testCreateMessage();
	}

	/**
	 * Test for {@link MessageService#remove(Message)}.
	 */
	@Test
	@Transactional
	public void testRemoveMessage() {
		final Integer id = 3;
		Message message = this.messageService.getMessage(id);
		assertEquals(id, message.getEntityId());
		this.messageService.remove(message);
		try {
			// already removed
			this.messageService.getMessage(id).getEntityId();
			fail("should throw an exception");
		} catch (Exception e) {
			// as expected
		}
	}
}
