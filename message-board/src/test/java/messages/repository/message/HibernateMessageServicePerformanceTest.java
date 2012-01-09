package messages.repository.message;

import messages.orm.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles(profiles = { "production", "hibernate" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-config.xml" })
public class HibernateMessageServicePerformanceTest {

	@Autowired
	private MessageService messageService;

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "admin"));
	}

	@Test
	@Transactional
	public void test() {
		final int n = 1000;
		Message message = null;
		long before = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			message = this.messageService.getMessage(Integer.valueOf(10));
			this.messageService.getAllMessages();
			this.messageService.create(new Message("e"));
		}
		long after = System.currentTimeMillis();
		System.out.println(after - before);
	}

}
