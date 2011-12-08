package messages.util;

import messages.orm.Message;
import messages.ws.types.MessageResponseFull;
import messages.ws.types.MessageResponseShort;

/**
 * Utility class that provides convenient static methods for converting from
 * entity POJO to response object.
 */
public class MessageRequestConverterUtils {

	/**
	 * Convert message to message response (SHORT VERSION).
	 * 
	 * @param message
	 * @return messageResponseShort the message response short object
	 */
	public static MessageResponseShort convertMessageToResponseShort(
			Message message) {
		MessageResponseShort messageResponseShort = new MessageResponseShort();
		messageResponseShort.setTitle(message.getTitle());
		messageResponseShort.setContent(message.getContent());
		messageResponseShort.setSender(message.getSender());
		return messageResponseShort;
	}

	/**
	 * Convert message to message response (FULL VERSION).
	 * 
	 * @param message
	 * @return messageResponseShort the message response full object
	 */
	public static MessageResponseFull convertMessageToResponseFull(
			Message message) {
		MessageResponseFull messageResponseFull = new MessageResponseFull();
		messageResponseFull.setId(message.getEntityId().toString());
		messageResponseFull.setTitle(message.getTitle());
		messageResponseFull.setContent(message.getContent());
		messageResponseFull.setSender(message.getSender());
		messageResponseFull.setUrl(message.getUrl());
		return messageResponseFull;
	}

}
