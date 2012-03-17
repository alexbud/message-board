package messages.ws;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import messages.orm.Message;
import messages.repository.message.MessageService;
import messages.ws.types.CreateMessageRequest;

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
public class MessageBoardEndpointTest {

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

}
