package messages.jms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import messages.orm.Message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisherImpl implements MessagePublisher {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void publishMessage(String queue, Message message) {
		this.amqpTemplate.convertAndSend(queue, message);
	}
	
	public List<Message> getMessagesFromQueue(String queue) {
		List<Message> messages = new ArrayList<Message>();
		Message message = null;
		do {
			message = (Message) this.amqpTemplate.receiveAndConvert(queue);
			if (message != null) {
				messages.add(message);
			}
		} while (message != null);
		return messages;
	}
	
	public void publishScheduledMessage() {
		Message message = new Message();
		message.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.publishMessage("messages", message);
	}

}
