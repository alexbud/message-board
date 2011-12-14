package messages.ws;

import java.util.List;

import javax.validation.Valid;

import messages.orm.Message;
import messages.repository.message.MessageService;
import messages.util.MessageRequestConverterUtils;
import messages.ws.types.CreateMessageRequest;
import messages.ws.types.ListMessagesFullRequest;
import messages.ws.types.ListMessagesShortRequest;
import messages.ws.types.MessageResponseFull;
import messages.ws.types.MessageResponseShort;
import messages.ws.types.MessagesListFull;
import messages.ws.types.MessagesListShort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Endpoint object serving message board requests via SOAP protocol.
 */
@Endpoint
public class MessageBoardEndpoint {

	private static final String NAMESPACE_URI = "http://www.abudko.com/message-board";

	@Autowired
	private MessageService messageService;

	/**
	 * Creates a new message from the request and persists it.
	 * 
	 * @param messageRequest
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createMessageRequest")
	public void createMessage(
			@Valid @RequestPayload CreateMessageRequest messageRequest) {
		String title = messageRequest.getTitle();
		Message message = new Message(title);
		String content = messageRequest.getContent();
		message.setContent(content);
		String url = messageRequest.getUrl();
		message.setUrl(url);
		this.messageService.create(message);
	}

	/**
	 * Returns FULL VERSION message response list.
	 * 
	 * @param req
	 * @return messages the FULL VERSION message list
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "listMessagesFullRequest")
	@ResponsePayload
	public MessagesListFull listMessagesFull(
			@RequestPayload ListMessagesFullRequest req) {
		List<Message> allMessages = this.messageService.getAllMessages();
		MessagesListFull messages = new MessagesListFull();
		for (Message message : allMessages) {
			MessageResponseFull messageResponseFull = MessageRequestConverterUtils
					.convertMessageToResponseFull(message);
			messages.add(messageResponseFull);
		}
		return messages;
	}

	/**
	 * Returns SHORT VERSION message response list.
	 * 
	 * @param req
	 * @return messages the SHORT VERSION message list
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "listMessagesShortRequest")
	@ResponsePayload
	public MessagesListShort listMessagesShort(
			@RequestPayload ListMessagesShortRequest req) {
		List<Message> allMessages = this.messageService.getAllMessages();
		MessagesListShort messages = new MessagesListShort();
		for (Message message : allMessages) {
			MessageResponseShort messageResponseShort = MessageRequestConverterUtils
					.convertMessageToResponseShort(message);
			messages.add(messageResponseShort);
		}
		return messages;
	}
}