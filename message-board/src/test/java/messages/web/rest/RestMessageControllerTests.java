package messages.web.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import messages.orm.Message;
import messages.repository.message.MessageService;
import messages.ws.types.MessageResponseFull;
import messages.ws.types.MessageResponseShort;
import messages.ws.types.MessagesListFull;
import messages.ws.types.MessagesListShort;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Tests for {@link RestMessageController}.
 * 
 * @author alexei
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RestMessageControllerTests {

	@Mock
	private MessageService messageService;

	private RestMessageController restMessageController;

	@Before
	public void setup() {
		this.restMessageController = new RestMessageController(this.messageService);
	}

	/**
	 * Test for {@link RestMessageController#listMessagesFullApp()}.
	 */
	@Test
	public void testListMessagesFullApp() {
		Message message1 = new Message();
		final int id1 = 1;
		message1.setEntityId(id1);
		final String title1 = "title1";
		message1.setTitle(title1);
		final String url1 = "url1";
		message1.setUrl(url1);
		final String content1 = "content1";
		message1.setContent(content1);
		final String principal1 = "principal1";
		message1.setPrincipal(principal1);

		Message message2 = new Message();
		final int id2 = 2;
		message2.setEntityId(id2);
		final String title2 = "title2";
		message2.setTitle(title2);
		final String url2 = "url2";
		message2.setUrl(url2);
		final String content2 = "content2";
		message2.setContent(content2);
		final String principal2 = "principal2";
		message2.setPrincipal(principal2);

		List<Message> messages = new ArrayList<Message>();
		messages.add(message1);
		messages.add(message2);

		when(this.messageService.getAllMessages()).thenReturn(messages);

		MessagesListFull listMessagesFull = this.restMessageController.listMessagesFullApp();

		List<MessageResponseFull> list = listMessagesFull.getMessages();

		assertEquals(String.valueOf(id1), list.get(0).getId());
		assertEquals(title1, list.get(0).getTitle());
		assertEquals(content1, list.get(0).getContent());
		assertEquals(url1, list.get(0).getUrl());
		assertEquals(principal1, list.get(0).getPrincipal());

		assertEquals(String.valueOf(id2), list.get(1).getId());
		assertEquals(title2, list.get(1).getTitle());
		assertEquals(content2, list.get(1).getContent());
		assertEquals(url2, list.get(1).getUrl());
		assertEquals(principal2, list.get(1).getPrincipal());

		verify(this.messageService).getAllMessages();
	}

	/**
	 * Test for {@link RestMessageController#listMessagesShortApp()}.
	 */
	@Test
	public void testListMessagesShortApp() {
		Message message1 = new Message();
		final int id1 = 1;
		message1.setEntityId(id1);
		final String title1 = "title1";
		message1.setTitle(title1);
		final String url1 = "url1";
		message1.setUrl(url1);
		final String content1 = "content1";
		message1.setContent(content1);
		final String principal1 = "principal1";
		message1.setPrincipal(principal1);

		Message message2 = new Message();
		final int id2 = 2;
		message2.setEntityId(id2);
		final String title2 = "title2";
		message2.setTitle(title2);
		final String url2 = "url2";
		message2.setUrl(url2);
		final String content2 = "content2";
		message2.setContent(content2);
		final String principal2 = "principal2";
		message2.setPrincipal(principal2);

		List<Message> messages = new ArrayList<Message>();
		messages.add(message1);
		messages.add(message2);

		when(this.messageService.getAllMessages()).thenReturn(messages);

		MessagesListShort listMessagesShort = this.restMessageController.listMessagesShortApp();

		List<MessageResponseShort> list = listMessagesShort.getMessages();

		assertEquals(title1, list.get(0).getTitle());
		assertEquals(content1, list.get(0).getContent());
		assertEquals(principal1, list.get(0).getPrincipal());

		assertEquals(title2, list.get(1).getTitle());
		assertEquals(content2, list.get(1).getContent());
		assertEquals(principal2, list.get(1).getPrincipal());

		verify(this.messageService).getAllMessages();
	}

	/**
	 * Test for {@link RestMessageController#listMessagesFullBrowser()}.
	 */
	@Test
	public void testListMessagesFullBrowser() {
		Message message1 = new Message();
		final int id1 = 1;
		message1.setEntityId(id1);
		final String title1 = "title1";
		message1.setTitle(title1);
		final String url1 = "url1";
		message1.setUrl(url1);
		final String content1 = "content1";
		message1.setContent(content1);
		final String principal1 = "principal1";
		message1.setPrincipal(principal1);

		Message message2 = new Message();
		final int id2 = 2;
		message2.setEntityId(id2);
		final String title2 = "title2";
		message2.setTitle(title2);
		final String url2 = "url2";
		message2.setUrl(url2);
		final String content2 = "content2";
		message2.setContent(content2);
		final String principal2 = "principal2";
		message2.setPrincipal(principal2);

		List<Message> messages = new ArrayList<Message>();
		messages.add(message1);
		messages.add(message2);

		when(this.messageService.getAllMessages()).thenReturn(messages);

		MessagesListFull listMessagesFull = this.restMessageController.listMessagesFullBrowser();

		List<MessageResponseFull> list = listMessagesFull.getMessages();

		assertEquals(String.valueOf(id1), list.get(0).getId());
		assertEquals(title1, list.get(0).getTitle());
		assertEquals(content1, list.get(0).getContent());
		assertEquals(url1, list.get(0).getUrl());
		assertEquals(principal1, list.get(0).getPrincipal());

		assertEquals(String.valueOf(id2), list.get(1).getId());
		assertEquals(title2, list.get(1).getTitle());
		assertEquals(content2, list.get(1).getContent());
		assertEquals(url2, list.get(1).getUrl());
		assertEquals(principal2, list.get(1).getPrincipal());

		verify(this.messageService).getAllMessages();
	}

	/**
	 * Test for {@link RestMessageController#listMessagesShortBrowser()}.
	 */
	@Test
	public void testListMessagesShortBrowser() {
		Message message1 = new Message();
		final int id1 = 1;
		message1.setEntityId(id1);
		final String title1 = "title1";
		message1.setTitle(title1);
		final String url1 = "url1";
		message1.setUrl(url1);
		final String content1 = "content1";
		message1.setContent(content1);
		final String principal1 = "principal1";
		message1.setPrincipal(principal1);

		Message message2 = new Message();
		final int id2 = 2;
		message2.setEntityId(id2);
		final String title2 = "title2";
		message2.setTitle(title2);
		final String url2 = "url2";
		message2.setUrl(url2);
		final String content2 = "content2";
		message2.setContent(content2);
		final String principal2 = "principal2";
		message2.setPrincipal(principal2);

		List<Message> messages = new ArrayList<Message>();
		messages.add(message1);
		messages.add(message2);

		when(this.messageService.getAllMessages()).thenReturn(messages);

		MessagesListShort listMessagesShort = this.restMessageController.listMessagesShortBrowser();

		List<MessageResponseShort> list = listMessagesShort.getMessages();

		assertEquals(title1, list.get(0).getTitle());
		assertEquals(content1, list.get(0).getContent());
		assertEquals(principal1, list.get(0).getPrincipal());

		assertEquals(title2, list.get(1).getTitle());
		assertEquals(content2, list.get(1).getContent());
		assertEquals(principal2, list.get(1).getPrincipal());

		verify(this.messageService).getAllMessages();
	}

	/**
	 * Test for {@link RestMessageController#getMessageFullApp(int)}.
	 */
	@Test
	public void testGetMessageFullApp() {
		Message message = new Message();
		final int id = 1;
		message.setEntityId(id);
		final String title = "title";
		message.setTitle(title);
		final String url = "url";
		message.setUrl(url);
		final String content = "content";
		message.setContent(content);
		final String principal = "principal";
		message.setPrincipal(principal);

		when(this.messageService.getMessage(id)).thenReturn(message);

		MessageResponseFull messageResponseFull = this.restMessageController.getMessageFullApp(id);

		assertEquals(String.valueOf(id), messageResponseFull.getId());
		assertEquals(title, messageResponseFull.getTitle());
		assertEquals(content, messageResponseFull.getContent());
		assertEquals(url, messageResponseFull.getUrl());
		assertEquals(principal, messageResponseFull.getPrincipal());

		verify(this.messageService).getMessage(id);
	}

	/**
	 * Test for {@link RestMessageController#getMessageShortApp(int)}.
	 */
	@Test
	public void testGetMessageShortApp() {
		Message message = new Message();
		final int id = 1;
		message.setEntityId(id);
		final String title = "title";
		message.setTitle(title);
		final String url = "url";
		message.setUrl(url);
		final String content = "content";
		message.setContent(content);
		final String principal = "principal";
		message.setPrincipal(principal);

		when(this.messageService.getMessage(id)).thenReturn(message);

		MessageResponseShort messageResponseShort = this.restMessageController.getMessageShortApp(id);

		assertEquals(title, messageResponseShort.getTitle());
		assertEquals(content, messageResponseShort.getContent());
		assertEquals(principal, messageResponseShort.getPrincipal());

		verify(this.messageService).getMessage(id);
	}

	/**
	 * Test for {@link RestMessageController#getMessageFullBrowser(int)}.
	 */
	@Test
	public void testGetMessageFullBrowser() {
		Message message = new Message();
		final int id = 1;
		message.setEntityId(id);
		final String title = "title";
		message.setTitle(title);
		final String url = "url";
		message.setUrl(url);
		final String content = "content";
		message.setContent(content);
		final String principal = "principal";
		message.setPrincipal(principal);

		when(this.messageService.getMessage(id)).thenReturn(message);

		MessageResponseFull messageResponseFull = this.restMessageController.getMessageFullBrowser(id);

		assertEquals(String.valueOf(id), messageResponseFull.getId());
		assertEquals(title, messageResponseFull.getTitle());
		assertEquals(content, messageResponseFull.getContent());
		assertEquals(url, messageResponseFull.getUrl());
		assertEquals(principal, messageResponseFull.getPrincipal());

		verify(this.messageService).getMessage(id);
	}

	/**
	 * Test for {@link RestMessageController#getMessageShortBrowser(int)}.
	 */
	@Test
	public void testGetMessageShortBrowser() {
		Message message = new Message();
		final int id = 1;
		message.setEntityId(id);
		final String title = "title";
		message.setTitle(title);
		final String url = "url";
		message.setUrl(url);
		final String content = "content";
		message.setContent(content);
		final String principal = "principal";
		message.setPrincipal(principal);

		when(this.messageService.getMessage(id)).thenReturn(message);

		MessageResponseShort messageResponseShort = this.restMessageController.getMessageShortBrowser(id);

		assertEquals(title, messageResponseShort.getTitle());
		assertEquals(content, messageResponseShort.getContent());
		assertEquals(principal, messageResponseShort.getPrincipal());

		verify(this.messageService).getMessage(id);
	}

	/**
	 * Test for
	 * {@link RestMessageController#createMessage(Message, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * )}.
	 */
	@Test
	public void testCreateMessage() {
		Message message = new Message();
		final int id = 1;
		message.setEntityId(id);
		final String title = "title";
		message.setTitle(title);
		final String url = "url";
		message.setUrl(url);
		final String content = "content";
		message.setContent(content);
		final String principal = "principal";
		message.setPrincipal(principal);

		MockHttpServletRequest servletRequest = new MockHttpServletRequest();
		final String requestUrl = "/url";
		servletRequest.setRequestURI(requestUrl);
		MockHttpServletResponse servletResponse = new MockHttpServletResponse();

		this.restMessageController.createMessage(message, servletRequest, servletResponse);

		assertEquals(servletRequest.getRequestURL() + "/" + id, servletResponse.getHeader("Location"));
		verify(this.messageService).create(message);
	}

	/**
	 * Test for {@link RestMessageController#removeMessage(int) )}.
	 */
	@Test
	public void testRemoveMessage() {
		Message message = new Message();
		final int id = 1;
		message.setEntityId(id);
		final String title = "title";
		message.setTitle(title);
		final String url = "url";
		message.setUrl(url);
		final String content = "content";
		message.setContent(content);
		final String principal = "principal";
		message.setPrincipal(principal);
		
		when(this.messageService.getMessage(id)).thenReturn(message);
		
		this.restMessageController.removeMessage(id);

		verify(this.messageService).getMessage(id);
		verify(this.messageService).remove(message);
	}
	
	/**
	 * Test for {@link RestMessageController#updateMessage(int, Message)}.
	 */
	@Test
	public void testUpdateMessage() {
		Message message = new Message();
		final int id = 1;
		message.setEntityId(id);
		this.restMessageController.updateMessage(id, message);
		verify(this.messageService).update(message);
	}
}
