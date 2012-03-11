package messages.web.client;

import messages.web.UserAccountController;

import org.springframework.test.context.ActiveProfiles;

/**
 * An integration test for {@link UserAccountController}. Test uses in-memory
 * database.
 */
@ActiveProfiles(profiles = { "dev", "jpa" })
public class JpaUserAccountControllerTests extends UserAccountControllerTests {

}
