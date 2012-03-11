package messages.web.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import messages.orm.Message;
import messages.repository.message.MessageService;
import messages.web.MessageController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;

/**
 * An integration abstract test base for {@link MessageController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-config.xml" })
public abstract class MessageControllerTests {

	@Autowired
	private MessageService messageService;

	@Autowired
	private MailSender mailSender;

	private MessageController controller;

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		this.controller = new MessageController(this.messageService,
				this.mailSender);
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
	}

	/**
	 * Test for
	 * {@link MessageController#messageDetails(int, org.springframework.ui.Model)}
	 */
	@Test
	@Transactional
	public void testHandleMessageDetailsRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		final Integer id = 0;
		String viewName = this.controller.messageDetails(id, model);
		Message message = (Message) model.get("message");
		assertEquals("messageDetails", viewName);
		assertNotNull(message);
		assertEquals(id, message.getEntityId());
		assertNotNull(message.getTimestamp());
	}

	/**
	 * Test for
	 * {@link MessageController#getEditMessage(int, org.springframework.ui.Model)
	 */
	@Test
	@Transactional
	public void testHandleGetEditMessageRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		final Integer id = 0;
		String viewName = this.controller.getEditMessage(id, model);
		Message message = (Message) model.get("message");
		assertEquals("messageForm", viewName);
		assertNotNull(message);
		assertEquals(id, message.getEntityId());
		assertNotNull(message.getTimestamp());
	}

	/**
	 * Test for
	 * {@link MessageController#getCreateMessage(int, org.springframework.ui.Model)
	 */
	@Test
	@Transactional
	public void testHandleGetCreateMessageRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String viewName = this.controller.getCreateMessage(model);
		Message message = (Message) model.get("message");
		assertEquals("messageForm", viewName);
		assertNotNull(message);
		assertNull(message.getEntityId());
	}

	/**
	 * Test for
	 * {@link MessageController#postCreateMessage(Message, org.springframework.validation.BindingResult)
	 */
	@Test
	@Transactional
	public void testHandlePostCreateMessageRequest() {
		Message message = new Message("title");
		String viewName = this.controller.postCreateMessage(message,
				new BeanPropertyBindingResult(message, "message"));
		assertEquals("redirect:/board/messages/messageDetails?entityId="
				+ message.getEntityId(), viewName);
		assertNotNull(message);
		assertNotNull(message.getEntityId());
		// test
		ExtendedModelMap model = new ExtendedModelMap();
		this.controller.messageDetails(message.getEntityId(), model);
		Message retrievedMessage = (Message) model.get("message");
		assertEquals(message.getTitle(), retrievedMessage.getTitle());
		assertEquals(message.getContent(), retrievedMessage.getContent());
		assertEquals(message.getPrincipal(), retrievedMessage.getPrincipal());
		assertEquals(message.getUrl(), retrievedMessage.getUrl());
		assertEquals(message.getEntityId(), retrievedMessage.getEntityId());
		assertNotNull(message.getTimestamp());
	}

	/**
	 * Test for
	 * {@link MessageController#postEditMessage(Message, org.springframework.validation.BindingResult)
	 */
	@Test
	@Transactional
	public void testHandlePostEditMessageRequest() {
		// let's create a message first
		Message message = new Message("title");
		this.controller.postCreateMessage(message,
				new BeanPropertyBindingResult(message, "message"));
		assertNotNull(message.getEntityId());
		// update some field
		final String newTitle = "new Title";
		message.setTitle(newTitle);
		String viewName = this.controller.postEditMessage(message,
				new BeanPropertyBindingResult(message, "message"));
		assertEquals("redirect:/board/messages/messageDetails?entityId="
				+ message.getEntityId(), viewName);
		assertNotNull(message);
		assertNotNull(message.getEntityId());
		assertNotNull(message.getTimestamp());
		// test
		ExtendedModelMap model = new ExtendedModelMap();
		this.controller.messageDetails(message.getEntityId(), model);
		Message retrievedMessage = (Message) model.get("message");
		assertEquals(newTitle, retrievedMessage.getTitle());
	}

	/**
	 * Test for
	 * {@link MessageController#removeMessage(int, org.springframework.ui.Model)
	 */
	@Test
	@Transactional
	public void testHandleRemoveMessageRequest() {
		// let's create a message first
		Message message = new Message("title");
		this.controller.postCreateMessage(message,
				new BeanPropertyBindingResult(message, "message"));
		assertNotNull(message.getEntityId());
		// remove message
		String viewName = this.controller.removeMessage(message.getEntityId());
		assertEquals("redirect:/board/messages/messageSummary", viewName);
		// still exists?
		ExtendedModelMap model = new ExtendedModelMap();
		this.controller.messageDetails(message.getEntityId(), model);
		try {
			((Message) model.get("message")).getEntityId();
			fail("should throw an exception");
		} catch (Exception e) {
			// as expected
		}
	}

	/**
	 * Test for
	 * {@link MessageController#messageSummary(org.springframework.ui.Model)}
	 */
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testHandleMessageSummaryRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String viewName = this.controller.messageSummary(model);
		List<Message> messages = (List<Message>) model.get("messages");
		assertEquals("messageSummary", viewName);
		assertNotNull(messages);
		assertEquals(4, messages.size());
		assertEquals(Integer.valueOf(0), messages.get(0).getEntityId());
	}
}
