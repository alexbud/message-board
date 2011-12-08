package messages.web.rest.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.List;

import messages.orm.Message;
import messages.web.rest.RestMessageController;
import messages.ws.types.MessageResponseFull;
import messages.ws.types.MessageResponseShort;
import messages.ws.types.MessagesListFull;
import messages.ws.types.MessagesListShort;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * A programmatic REST client test case for {@link RestMessageController}. NOTE:
 * requires an application to be running on localhost application server.
 */
@ContextConfiguration(locations = { "/rest-client-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RestMessageControllerTests {

	/**
	 * Server URL ending with the servlet mapping on which the application can
	 * be reached.
	 */
	private static final String BASE_URL = "http://localhost:8080/message-board/board/app";

	@Autowired
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		// authenticated user
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(null, -1),
				new UsernamePasswordCredentials("admin", "admin"));
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);
		this.restTemplate.setRequestFactory(requestFactory);
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#listMessagesFullApp()}
	 */
	@Test
	public void messageSummaryFullRequest() {
		final String url = BASE_URL + "/full/messages";
		MessagesListFull messages = this.restTemplate.getForObject(url,
				MessagesListFull.class);
		List<MessageResponseFull> list = messages.getMessages();
		final int id = 0;
		assertNotNull(list.get(id).getId());
		assertNotNull(list.get(id).getTitle());
		assertNotNull(list.get(id).getContent());
		assertNotNull(list.get(id).getSender());
		assertNotNull(list.get(id).getUrl());
		// test application/json Accept header
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessagesListFull>(headers),
				MessagesListFull.class);
		// test application/xml Accept header
		headers = new HttpHeaders();
		headers.set("Accept", "application/xml");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessagesListFull>(headers),
				MessagesListFull.class);
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#listMessagesShortApp()}
	 */
	@Test
	public void messageSummaryShortRequest() {
		final String url = BASE_URL + "/short/messages";
		MessagesListShort messages = this.restTemplate.getForObject(url,
				MessagesListShort.class);
		List<MessageResponseShort> list = messages.getMessages();
		final int id = 0;
		assertNotNull(list.get(id).getTitle());
		assertNotNull(list.get(id).getContent());
		assertNotNull(list.get(id).getSender());
		// test application/json Accept header
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessagesListShort>(headers),
				MessagesListShort.class);
		// test application/xml Accept header
		headers = new HttpHeaders();
		headers.set("Accept", "application/xml");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessagesListShort>(headers),
				MessagesListShort.class);
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#getMessageFullApp(int)}
	 */
	@Test
	public void messageDetailsFullRequest() {
		final String id = "1";
		final String url = BASE_URL + "/full/messages/{id}";
		MessageResponseFull message = this.restTemplate.getForObject(url,
				MessageResponseFull.class, id);
		assertNotNull(message.getId());
		assertNotNull(message.getTitle());
		assertNotNull(message.getContent());
		assertNotNull(message.getSender());
		assertNotNull(message.getUrl());
		// test application/json Accept header
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessageResponseFull>(headers),
				MessageResponseFull.class, id);
		// test application/xml Accept header
		headers = new HttpHeaders();
		headers.set("Accept", "application/xml");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessageResponseFull>(headers),
				MessageResponseFull.class, id);
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#getMessageShortApp(int)}
	 */
	@Test
	public void messageDetailsShortRequest() {
		final String id = "1";
		final String url = BASE_URL + "/short/messages/{id}";
		MessageResponseShort message = this.restTemplate.getForObject(url,
				MessageResponseShort.class, id);
		assertNotNull(message.getTitle());
		assertNotNull(message.getContent());
		assertNotNull(message.getSender());
		// test application/json Accept header
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessageResponseShort>(headers),
				MessageResponseShort.class, id);
		// test application/xml Accept header
		headers = new HttpHeaders();
		headers.set("Accept", "application/xml");
		this.restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<MessageResponseShort>(headers),
				MessageResponseShort.class, id);
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#createMessage(Message, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 */
	@Test
	public void createMessageRequest() {
		String url = BASE_URL + "/full/messages";
		Message message = new Message("title");
		message.setContent("content");
		message.setSender("sender");
		message.setUrl("http://url");
		URI newMessageLocation = this.restTemplate
				.postForLocation(url, message);
		MessageResponseFull retrievedMessage = this.restTemplate.getForObject(
				newMessageLocation, MessageResponseFull.class);
		assertNotNull(retrievedMessage.getId());
		assertEquals(message.getTitle(), retrievedMessage.getTitle());
		assertEquals(message.getContent(), retrievedMessage.getContent());
		assertEquals(message.getSender(), retrievedMessage.getSender());
		assertEquals(message.getUrl(), retrievedMessage.getUrl());
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#createMessage(Message, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * Should throw Unauthorized exception
	 */
	@Test
	public void createMessageRequestNotAuthorized() {
		// setup unauthorized principal
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(null, -1),
				new UsernamePasswordCredentials("viewer", "viewer"));
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);
		this.restTemplate.setRequestFactory(requestFactory);
		String url = BASE_URL + "/full/messages";
		Message message = new Message("title");
		message.setContent("content");
		message.setSender("sender");
		message.setUrl("http://url");
		try {
			this.restTemplate.postForLocation(url, message);
			fail("Should have received 403 Forbidden after posting message at "
					+ url);
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
		}
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#createMessage(Message, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * Testing validation by posting invalid data.
	 */
	@Test
	public void createMessageBadRequest() {
		String url = BASE_URL + "/full/messages";
		Message message = new Message();
		try {
			this.restTemplate.postForLocation(url, message);
			fail("Should have received 400 Bad request after posting invalid message at "
					+ url);
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
		}
		// valid data
		message.setTitle("title");
		this.restTemplate.postForLocation(url, message);
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#removeMessage(int)}
	 */
	@Test
	public void removeMessageRequest() {
		String url = BASE_URL + "/full/messages";
		// first create message
		Message message = new Message("title");
		message.setContent("content");
		message.setSender("sender");
		message.setUrl("http://url");
		URI newMessageLocation = this.restTemplate
				.postForLocation(url, message);
		MessageResponseFull retrievedMessage = this.restTemplate.getForObject(
				newMessageLocation, MessageResponseFull.class);
		assertNotNull(retrievedMessage.getId());
		// remove message
		this.restTemplate.delete(newMessageLocation);
		try {
			this.restTemplate.getForObject(newMessageLocation,
					MessageResponseFull.class);
			fail("Should have received 404 Not Found after deleting message at "
					+ newMessageLocation);
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#removeMessage(int)} Should throw
	 * Unauthorized exception
	 */
	@Test
	public void removeMessageRequestNotAuthorized() {
		String url = BASE_URL + "/full/messages";
		// first create message by admin
		Message message = new Message("title");
		message.setContent("content");
		message.setSender("sender");
		message.setUrl("http://url");
		URI newMessageLocation = this.restTemplate
				.postForLocation(url, message);
		MessageResponseFull retrievedMessage = this.restTemplate.getForObject(
				newMessageLocation, MessageResponseFull.class);
		assertNotNull(retrievedMessage.getId());
		// setup unauthorized principal
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(null, -1),
				new UsernamePasswordCredentials("member", "member"));
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);
		this.restTemplate.setRequestFactory(requestFactory);
		// remove message
		try {
			this.restTemplate.delete(newMessageLocation);
			fail("Should have received 403 Forbidden after deleting message at "
					+ newMessageLocation);
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
		}
	}

	/**
	 * Programmatic client test for
	 * {@link RestMessageController#updateMessage(int, Message)}
	 */
	@Test
	public void updateMessageRequest() {
		String url = BASE_URL + "/full/messages";
		// first create message
		final String title = "title";
		Message message = new Message(title);
		message.setContent("content");
		message.setSender("sender");
		message.setUrl("http://url");
		URI newMessageLocation = this.restTemplate
				.postForLocation(url, message);
		MessageResponseFull retrievedMessage = this.restTemplate.getForObject(
				newMessageLocation, MessageResponseFull.class);
		assertNotNull(retrievedMessage.getId());
		assertEquals(title, retrievedMessage.getTitle());
		// update message
		Message newMessage = new Message();
		newMessage.setEntityId(Integer.valueOf(retrievedMessage.getId()));
		final String newTitle = "newTitle";
		newMessage.setTitle(newTitle);
		// other values are not changes, only title
		newMessage.setContent(retrievedMessage.getContent());
		newMessage.setSender(retrievedMessage.getSender());
		newMessage.setUrl(retrievedMessage.getUrl());
		this.restTemplate.put(newMessageLocation, newMessage);
		MessageResponseFull newRetrievedMessage = this.restTemplate
				.getForObject(newMessageLocation, MessageResponseFull.class);
		assertEquals(newTitle, newRetrievedMessage.getTitle());
	}
}
