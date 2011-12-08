package messages.repository;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = { "dev", "hibernate" })
public class HibernateMessageServiceTests extends MessageServiceTests {

}
