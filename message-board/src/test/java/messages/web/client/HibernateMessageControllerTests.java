package messages.web.client;

import messages.web.MessageController;

import org.springframework.test.context.ActiveProfiles;

/**
 * An integration test for {@link MessageController}. Test uses in-memory
 * database.
 */
@ActiveProfiles(profiles = { "dev", "hibernate" })
public class HibernateMessageControllerTests extends MessageControllerTests {

}
