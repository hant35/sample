package com.panpages.bow.service.mail;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.panpages.bow.model.Mail;
import com.panpages.bow.model.Survey;

public interface MailService {	

	void sendMail(Mail mail) throws MailException;

	void sendMail(Survey survey, Map<String, String> params, String filePath) throws MailException;
	
	void sendMailAttach(Mail mail, String filePath)  throws MailException, MessagingException;
	
}
