package messages.repository.message;

import org.springframework.test.context.ActiveProfiles;

/**
 * An integration test for {@link JpaMessageService}. Test uses in-memory
 * database.
 * test test
 */
@ActiveProfiles(profiles = { "dev", "jpa" })
public class JpaMessageServiceTests extends MessageServiceTests {

}
