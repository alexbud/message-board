package messages.jms;

import java.util.List;

import messages.orm.Message;

public interface MessagePublisher {

	void publishMessage(String queue, Message message);

	List<Message> getMessagesFromQueue(String queue);

}
