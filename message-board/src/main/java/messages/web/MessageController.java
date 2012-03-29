package messages.web;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import messages.orm.Message;
import messages.repository.message.MessageService;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Action based Spring MVC @Controller controller handling requests for message
 * summary, message details, edit message form, create message form, remove
 * message.
 */
@Controller
@RequestMapping(value = "/messages")
public class MessageController {

	private MessageService messageService;

	private MailSender mailSender;
	
	@Autowired 
	private AmqpTemplate amqpTemplate;

	/**
	 * Creates a new MessageController with a given message service.
	 */
	@Autowired
	public MessageController(MessageService messageService, MailSender mailSender) {
		this.messageService = messageService;
		this.mailSender = mailSender;
	}

	/**
	 * Provides a model with an message for the message detail page.
	 * 
	 * @param id the id of the message
	 * @param model the "implicit" model created by Spring MVC
	 */
	@RequestMapping(value = "/messageDetails", method = RequestMethod.GET)
	public String messageDetails(@RequestParam("entityId") int id, Model model) {
		model.addAttribute("message", this.messageService.getMessage(id));
		return "messageDetails";
	}

	/**
	 * Provides a model with an message for the message form page to edit a
	 * message
	 * 
	 * @param id the id of the message
	 * @param model the "implicit" model created by Spring MVC
	 */
	@RequestMapping(value = "/editMessage", method = RequestMethod.GET)
	public String getEditMessage(@RequestParam("entityId") int id, Model model) {
		Message message = this.messageService.getMessage(id);
		model.addAttribute("message", message);
		return "messageForm";
	}

	/**
	 * Provides a model with a list of all messages for the summary page.
	 * 
	 * @param model the "implicit" model created by Spring MVC
	 */
	@RequestMapping("/messageSummary")
	public String messageSummary(Model model) {
		List<Message> messages = this.messageService.getAllMessages();
		model.addAttribute("messages", messages);
		return "messageSummary";
	}

	/**
	 * Creates a new message object and puts it to the model.
	 * 
	 * @param entityId
	 * @param model the "implicit" model created by Spring MVC
	 */
	@RequestMapping(value = {"/createMessage", "/publishMessage"}, method = RequestMethod.GET)
	public String getCreateMessage(Model model) {
		Message message = new Message();
		model.addAttribute("message", message);
		return "messageForm";
	}

	/**
	 * Removes a message from the database.
	 * 
	 * @param entityId
	 */
	@RequestMapping(value = "/removeMessage")
	public String removeMessage(@RequestParam("entityId") int id) {
		Message message = this.messageService.getMessage(id);
		this.messageService.remove(message);
		return "redirect:/board/messages/messageSummary";
	}

	/**
	 * Posts a message and stores in the database.
	 * 
	 * @param message the message to be stored
	 * @param bindingResult
	 */
	@RequestMapping(value = "/createMessage", method = RequestMethod.POST)
	public String postCreateMessage(@ModelAttribute("message") @Valid Message message, BindingResult result) {
		if (result.hasErrors()) {
			return "messageForm";
		}
		this.messageService.create(message);
		// this.sendEmail(message);
		return "redirect:/board/messages/messageDetails?entityId=" + message.getEntityId();
	}

	/**
	 * Posts an edited existing message and stores in the database.
	 * 
	 * @param message the message to be stored
	 * @param bindingResult
	 */
	@RequestMapping(value = "/editMessage", method = RequestMethod.POST)
	public String postEditMessage(@ModelAttribute("message") @Valid Message message, BindingResult result) {
		if (result.hasErrors()) {
			return "messageForm";
		}
		this.messageService.update(message);
		return "redirect:/board/messages/messageDetails?entityId=" + message.getEntityId();
	}
	
	/**
	 * Publishes a message to a queue.
	 * 
	 * @param message the message to be published
	 * @param bindingResult
	 */
	@RequestMapping(value = "/publishMessage", method = RequestMethod.POST)
	public String postPublishMessage(@ModelAttribute("message") @Valid Message message, BindingResult result) {
		if (result.hasErrors()) {
			return "messageForm";
		}
		String principal = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		Assert.notNull(principal);
		message.setPrincipal(principal);
		message.setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.amqpTemplate.convertAndSend("messages", message);
		return "published";
	}
	
	/**
	 * Retrieves a message from queue.
	 * 
	 * @param model the "implicit" model created by Spring MVC
	 */
	@RequestMapping(value = "/getMessageFromQueue", method = RequestMethod.GET)
	public String getMessageFromQueue(Model model) {
		Message message = (Message) this.amqpTemplate.receiveAndConvert("messages");
		model.addAttribute("message", message);
		return "messageDetailsFromQueue";
	}

	private void sendEmail(Message message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setSubject("New message " + message.getTitle());
		mail.setText("New message " + message.getTitle() + " has been created");
		mail.setFrom("nesi_bud@mail.ru");
		mail.setTo("alexei.budko@netum.fi");
		this.mailSender.send(mail);
	}
}
