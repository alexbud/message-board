package messages.ws.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import messages.ws.MessageBoardEndpoint;
import messages.ws.types.CreateMessageRequest;
import messages.ws.types.ListMessagesFullRequest;
import messages.ws.types.ListMessagesShortRequest;
import messages.ws.types.MessageResponseFull;
import messages.ws.types.MessageResponseShort;
import messages.ws.types.MessagesListFull;
import messages.ws.types.MessagesListShort;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A programmatic SOAP client test case for {@link MessageBoardEndpoint}. NOTE:
 * requires an application to be running on localhost application server.
 */
@ContextConfiguration(locations = { "classpath:/soap-client-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SoapMessageBoardTests {

	private static final String NAMESPACE_URI = "http://www.abudko.com/message-board";

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	/**
	 * Client test for
	 * {@link MessageBoardEndpoint#listMessagesFull(messages.ws.types.ListMessagesFullRequest)
	 * )} using jaxb 'object - xml' conversion.
	 */
	@Test
	public void testListMessagesFullWithJAXB() {
		ListMessagesFullRequest request = new ListMessagesFullRequest();
		MessagesListFull list = (MessagesListFull) this.webServiceTemplate
				.marshalSendAndReceive(request);
		List<MessageResponseFull> messages = list.getMessages();
		final int id = 0;
		MessageResponseFull message = messages.get(id);
		// assert the expected message results
		assertNotNull(message);
		// the message id must exist
		assertNotNull(Integer.valueOf(message.getId()).intValue());
		// the message title must exist
		assertNotNull(message.getTitle());
		// the message content must exist
		assertNotNull(message.getContent());
		// the message sender must exist
		assertNotNull(message.getSender());
		// the message url must exist
		assertNotNull(message.getUrl());
	}

	/**
	 * Client test for
	 * {@link MessageBoardEndpoint#listMessagesShort(messages.ws.types.ListMessagesShortRequest)
	 * )} using jaxb 'object - xml' conversion.
	 */
	@Test
	public void testListMessagesShortWithJAXB() {
		ListMessagesShortRequest request = new ListMessagesShortRequest();
		MessagesListShort list = (MessagesListShort) this.webServiceTemplate
				.marshalSendAndReceive(request);
		List<MessageResponseShort> messages = list.getMessages();
		MessageResponseShort message = messages.get(0);
		// assert the expected message results
		assertNotNull(message);
		// the message title must exists
		assertNotNull(message.getTitle());
		// the message content must exists
		assertNotNull(message.getContent());
		// the message sender must exists
		assertNotNull(message.getSender());
	}

	/**
	 * Client test for
	 * {@link MessageBoardEndpoint#createMessage(CreateMessageRequest)} using
	 * plain xml without conversion.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateMessageWithJAXB() throws Exception {
		MessagesListShort list = (MessagesListShort) this.webServiceTemplate
				.marshalSendAndReceive(new ListMessagesShortRequest());
		final int size = list.getMessages().size();
		CreateMessageRequest request = new CreateMessageRequest();
		final String title = "new title";
		request.setTitle(title);
		final String content = "new content";
		request.setContent(content);
		final String sender = "new sender";
		request.setSender(sender);
		final String url = "http://www";
		request.setUrl(url);

		this.webServiceTemplate.marshalSendAndReceive(request);

		// test newly created
		MessagesListShort newlist = (MessagesListShort) this.webServiceTemplate
				.marshalSendAndReceive(new ListMessagesShortRequest());
		assertEquals(size + 1, newlist.getMessages().size());
	}

	/**
	 * Client test for
	 * {@link MessageBoardEndpoint#createMessage(CreateMessageRequest)} using
	 * plain xml without conversion.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateMessageWithXml() throws Exception {
		MessagesListShort list = (MessagesListShort) this.webServiceTemplate
				.marshalSendAndReceive(new ListMessagesShortRequest());
		final int size = list.getMessages().size();
		Document document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();
		Element messageElement = document.createElementNS(NAMESPACE_URI,
				"createMessageRequest");
		final String title = "new title";
		messageElement.setAttribute("title", title);
		final String content = "new content";
		messageElement.setAttribute("content", content);
		final String sender = "new sender";
		messageElement.setAttribute("sender", sender);
		final String url = "http://www";
		messageElement.setAttribute("url", url);
		DOMSource source = new DOMSource(messageElement);
		this.webServiceTemplate.sendSourceAndReceiveToResult(source, null);

		// test newly created
		MessagesListShort newlist = (MessagesListShort) this.webServiceTemplate
				.marshalSendAndReceive(new ListMessagesShortRequest());
		assertEquals(size + 1, newlist.getMessages().size());
	}

	/**
	 * Client test for
	 * {@link MessageBoardEndpoint#createMessage(CreateMessageRequest)} using
	 * real sample xml file request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateMessageWithRealXmlFile() throws Exception {
		MessagesListShort list = (MessagesListShort) this.webServiceTemplate
				.marshalSendAndReceive(new ListMessagesShortRequest());
		final int size = list.getMessages().size();
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		ClassPathResource resource = new ClassPathResource(
				"/soap-sample-request.xml");
		Document document = documentBuilder.parse(resource.getFile());
		Element requestElement = document.getDocumentElement();
		DOMSource source = new DOMSource(requestElement);
		this.webServiceTemplate.sendSourceAndReceiveToResult(source, null);
		// test newly created
		MessagesListShort newlist = (MessagesListShort) this.webServiceTemplate
				.marshalSendAndReceive(new ListMessagesShortRequest());
		assertEquals(size + 1, newlist.getMessages().size());
	}
}
