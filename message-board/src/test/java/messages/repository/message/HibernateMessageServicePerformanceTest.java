package messages.repository.message;

import static org.junit.Assert.assertEquals;

import java.util.Hashtable;

import messages.orm.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

//@ActiveProfiles(profiles = { "production", "hibernate" })
@ActiveProfiles(profiles = { "dev", "hibernate" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-config.xml" })
public class HibernateMessageServicePerformanceTest {
	
	Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private MessageService messageService;
	
	private Hashtable<String, String> table = new Hashtable<String, String>();
	
	private static final int N = 5000;

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		// populate hashtable
		for (int i = 0; i < 1000; i++) {
			this.table.put(String.valueOf(i), String.valueOf(i));
		}
	}

	@Test
	@Transactional
	public void testMysql() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "admin"));
		final int id = 10;
		Message message = this.messageService.getMessage(Integer.valueOf(id));
		long before = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			assertEquals(message, this.messageService.getMessage(Integer.valueOf(id)));
			this.messageService.create(new Message("e"));
		}
		long after = System.currentTimeMillis();
		this.logger.info("MYSQL test took " + (after - before) + " ms");
	}
	
	@Test
	@Transactional
	public void testHsqldb() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "password"));
		long before = System.currentTimeMillis();
		final int id = 10;
		Message message = this.messageService.getMessage(Integer.valueOf(id));
		for (int i = 0; i < N; i++) {
			assertEquals(message, this.messageService.getMessage(Integer.valueOf(id)));
			this.messageService.create(new Message("e"));
		}
		long after = System.currentTimeMillis();
		this.logger.info("HSQLDB test took " + (after - before) + " ms");
	}
	
	@Test
	@Transactional
	public void testHashtable() {
		long before = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			this.table.get(Integer.valueOf(10));
			this.table.put(String.valueOf(i), String.valueOf(i));
		}
		long after = System.currentTimeMillis();
		this.logger.info("HASHTABLE test took " + (after - before) + " ms");
	}

}
