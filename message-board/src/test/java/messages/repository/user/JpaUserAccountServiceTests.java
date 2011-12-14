package messages.repository.user;

import org.springframework.test.context.ActiveProfiles;

/**
 * An integration test for {@link JpaUserAccountService}. Test uses in-memory
 * database.
 */
@ActiveProfiles(profiles = { "dev", "jpa" })
public class JpaUserAccountServiceTests extends UserAccountServiceTest {

}
