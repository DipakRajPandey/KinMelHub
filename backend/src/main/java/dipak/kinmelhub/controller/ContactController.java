package dipak.kinmelhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dipak.kinmelhub.model.EmailEntity;
import dipak.kinmelhub.utils.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ContactController {
@Autowired
	private MailsUtils mu;
	
	@PostMapping("/sendmail/{toEmail}/{subject}")
	public ResponseEntity<String>sendMail(@PathVariable("toEmail") String toEMail,@PathVariable("subject") String subject
			,@RequestBody EmailEntity message) {
		;
		return new ResponseEntity<>(mu.sendEmail(toEMail, subject, message),HttpStatus.OK);
	}
}
