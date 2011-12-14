package messages.repository.message;

import org.springframework.test.context.ActiveProfiles;

/**
 * An integration test for {@link HibernateMessageService}. Test uses in-memory
 * database.
 */
@ActiveProfiles(profiles = { "dev", "hibernate" })
public class HibernateMessageServiceTests extends MessageServiceTests {

}
