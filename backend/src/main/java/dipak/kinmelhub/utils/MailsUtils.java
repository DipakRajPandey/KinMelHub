package dipak.kinmelhub.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import dipak.kinmelhub.model.EmailEntity;

@Component
public class MailsUtils {
	@Autowired
	private JavaMailSender sendmail;
public String sendEmail(String toEmail,String subject,EmailEntity message) {
	SimpleMailMessage  mail=new SimpleMailMessage();
	mail.setTo(toEmail);
	mail.setSubject(subject);
	mail.setText(message.toString());
	sendmail.send(mail);
	return "Mail sended";
}
}
