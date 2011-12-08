package messages.repository;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = { "dev", "jpa" })
public class JpaMessageServiceTests extends MessageServiceTests {

}
