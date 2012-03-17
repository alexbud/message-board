package messages.web.rest;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import messages.orm.Message;
import messages.repository.message.MessageService;
import messages.util.MessageRequestConverterUtils;
import messages.ws.types.MessageResponseFull;
import messages.ws.types.MessageResponseShort;
import messages.ws.types.MessagesListFull;
import messages.ws.types.MessagesListShort;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RESTful Spring MVC @Controller controller, intended for programmatic and
 * browser clients.
 */
@Controller
public class RestMessageController {

	private MessageService messageService;

	/**
	 * Creates a new MessageController with a given message service.
	 */
	@Autowired
	public RestMessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * Provides a REST list of all messages (FULL VERSION) for programmatic
	 * access. Representation is selected depending on Accept header of the
	 * request.
	 */
	@RequestMapping(value = "/app/full/messages", method = RequestMethod.GET)
	public @ResponseBody
	MessagesListFull listMessagesFullApp() {
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
	 * Provides a REST list of all messages (FULL VERSION) for browser access.
	 * Representation is selected depending on media type extension (.json,
	 * .xml).
	 */
	@RequestMapping(value = "/browser/full/messages", method = RequestMethod.GET)
	public MessagesListFull listMessagesFullBrowser() {
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
	 * Provides a REST list of all messages (SHORT VERSION) for programmatic
	 * access. Representation is selected depending on Accept header of the
	 * request.
	 */
	@RequestMapping(value = "/app/short/messages", method = RequestMethod.GET)
	public @ResponseBody
	MessagesListShort listMessagesShortApp() {
		List<Message> allMessages = this.messageService.getAllMessages();
		MessagesListShort messages = new MessagesListShort();
		for (Message message : allMessages) {
			MessageResponseShort messageResponseShort = MessageRequestConverterUtils
					.convertMessageToResponseShort(message);
			messages.add(messageResponseShort);
		}
		return messages;
	}

	/**
	 * Provides a REST list of all messages (SHORT VERSION) for browser access.
	 * Representation is selected depending on media type extension (.json,
	 * .xml).
	 */
	@RequestMapping(value = "/browser/short/messages", method = RequestMethod.GET)
	public MessagesListShort listMessagesShortBrowser() {
		List<Message> allMessages = this.messageService.getAllMessages();
		MessagesListShort messages = new MessagesListShort();
		for (Message message : allMessages) {
			MessageResponseShort messageResponseShort = MessageRequestConverterUtils
					.convertMessageToResponseShort(message);
			messages.add(messageResponseShort);
		}
		return messages;
	}

	/**
	 * Provides the details of a message (FULL VERSION) with the given id for
	 * programmatic access. Representation is selected depending on Accept
	 * header of the request.
	 */
	@RequestMapping(value = "/app/full/messages/{id}", method = RequestMethod.GET)
	public @ResponseBody
	MessageResponseFull getMessageFullApp(@PathVariable("id") int id) {
		Message message = this.messageService.getMessage(id);
		if (message == null) {
			throw new EntityNotFoundException();
		}
		MessageResponseFull messageResponseFull = MessageRequestConverterUtils
				.convertMessageToResponseFull(message);
		return messageResponseFull;
	}

	/**
	 * Provides the details of a message (FULL VERSION) with the given id for
	 * browser access. Representation is selected depending on media type
	 * extension (.json, .xml).
	 */
	@RequestMapping(value = "/browser/full/messages/{id}", method = RequestMethod.GET)
	public MessageResponseFull getMessageFullBrowser(@PathVariable("id") int id) {
		Message message = this.messageService.getMessage(id);
		if (message == null) {
			throw new EntityNotFoundException();
		}
		MessageResponseFull messageResponseFull = MessageRequestConverterUtils
				.convertMessageToResponseFull(message);
		return messageResponseFull;
	}

	/**
	 * Provides the details of a message (SHORT VERSION) with the given id for
	 * programmatic access. Representation is selected depending on Accept
	 * header of the request.
	 */
	@RequestMapping(value = "/app/short/messages/{id}", method = RequestMethod.GET)
	public @ResponseBody
	MessageResponseShort getMessageShortApp(@PathVariable("id") int id) {
		Message message = this.messageService.getMessage(id);
		if (message == null) {
			throw new EntityNotFoundException();
		}
		MessageResponseShort messageResponseShort = MessageRequestConverterUtils
				.convertMessageToResponseShort(message);
		return messageResponseShort;
	}

	/**
	 * Provides the details of a message (SHORT VERSION) with the given id for
	 * browser access. Representation is selected depending on media type
	 * extension (.json, .xml).
	 */
	@RequestMapping(value = "/browser/short/messages/{id}", method = RequestMethod.GET)
	public MessageResponseShort getMessageShortBrowser(
			@PathVariable("id") int id) {
		Message message = this.messageService.getMessage(id);
		if (message == null) {
			throw new EntityNotFoundException();
		}
		MessageResponseShort messageResponseShort = MessageRequestConverterUtils
				.convertMessageToResponseShort(message);
		return messageResponseShort;
	}

	/**
	 * Creates a new Message, setting its URL as the Location header on the
	 * response.
	 */
	@RequestMapping(value = "/app/full/messages", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createMessage(@Valid @RequestBody Message newMessage,
			HttpServletRequest request, HttpServletResponse response) {
		this.messageService.create(newMessage);
		StringBuffer url = request.getRequestURL();
		url.append("/");
		url.append(newMessage.getEntityId());
		response.setHeader("Location", url.toString());
	}

	/**
	 * Removes a Message with the given id.
	 */
	@RequestMapping(value = "/app/full/messages/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeMessage(@PathVariable("id") int id) {
		Message message = this.messageService.getMessage(id);
		this.messageService.remove(message);
	}

	/**
	 * Updates a Message with the given id.
	 */
	@RequestMapping(value = "/app/full/messages/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateMessage(@PathVariable("id") int id,
			@Valid @RequestBody Message message) {
		this.messageService.update(message);
	}

	/**
	 * Maps ObjectNotFoundExceptions to a 404 Not Found HTTP status code.
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ ObjectNotFoundException.class })
	public void handleObjectNotFound() {
		// just return empty 404
	}

	/**
	 * Maps EntityNotFoundExceptions to a 404 Not Found HTTP status code.
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ EntityNotFoundException.class })
	public void handleEntityNotFound() {
		// just return empty 404
	}
}
