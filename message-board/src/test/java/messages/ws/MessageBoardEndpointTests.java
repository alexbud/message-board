package messages.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import messages.orm.Message;
import messages.repository.message.MessageService;
import messages.ws.types.CreateMessageRequest;
import messages.ws.types.ListMessagesFullRequest;
import messages.ws.types.ListMessagesShortRequest;
import messages.ws.types.MessageResponseFull;
import messages.ws.types.MessageResponseShort;
import messages.ws.types.MessagesListFull;
import messages.ws.types.MessagesListShort;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link MessageBoardEndpoint}.
 * 
 * @author alexei
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageBoardEndpointTests {

	@Mock
	private MessageService messageService;

	@InjectMocks
	private MessageBoardEndpoint messageBoardEndpoint = new MessageBoardEndpoint();

	/**
	 * Test for {@link MessageBoardEndpoint#createMessage(messages.ws.types.CreateMessageRequest)}.
	 */
	@Test
	public void testCreateMessage() {
		Message message = new Message();
		final String title = "title";
		message.setTitle(title);
		final String url = "url";
		message.setUrl(url);
		final String content = "content";
		message.setContent(content);

		CreateMessageRequest createMessageRequest = new CreateMessageRequest();
		createMessageRequest.setTitle(message.getTitle());
		createMessageRequest.setUrl(message.getUrl());
		createMessageRequest.setContent(message.getContent());

		this.messageBoardEndpoint.createMessage(createMessageRequest);
		verify(this.messageService).create(refEq(message));
	}

	/**
	 * Test for {@link MessageBoardEndpoint#listMessagesFull(messages.ws.types.ListMessagesFullRequest)}.
	 */
	@Test
	public void testListMessagesFull() {
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

		ListMessagesFullRequest listMessagesFullRequest = new ListMessagesFullRequest();
		MessagesListFull listMessagesFull = this.messageBoardEndpoint.listMessagesFull(listMessagesFullRequest);

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
	 * Test for {@link MessageBoardEndpoint#listMessagesShort(messages.ws.types.ListMessagesShortRequest)}.
	 */
	@Test
	public void testListMessagesShort() {
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

		ListMessagesShortRequest listMessagesShortRequest = new ListMessagesShortRequest();
		MessagesListShort listMessagesShort = this.messageBoardEndpoint.listMessagesShort(listMessagesShortRequest);

		List<MessageResponseShort> list = listMessagesShort.getMessages();

		assertEquals(title1, list.get(0).getTitle());
		assertEquals(content1, list.get(0).getContent());
		assertEquals(principal1, list.get(0).getPrincipal());

		assertEquals(title2, list.get(1).getTitle());
		assertEquals(content2, list.get(1).getContent());
		assertEquals(principal2, list.get(1).getPrincipal());

		verify(this.messageService).getAllMessages();
	}
}
