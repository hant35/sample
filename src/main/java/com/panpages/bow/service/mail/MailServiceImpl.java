package com.panpages.bow.service.mail;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.Field;
import com.panpages.bow.model.Mail;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.utils.SurveyUtils;

@Service("mailService")
public class MailServiceImpl implements MailService {
	@Autowired 
	ApplicationContext ctx;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	private static final Logger logger = Logger.getLogger(MailServiceImpl.class);

	public void sendMail(Mail mail) throws MailException {
		try {
			SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom(mail.getMailFrom());
			message.setTo(mail.getMailTo());
			message.setSubject(mail.getMailSubject());
			
			// String mailTemplatePath = ctx.getEnvironment().getProperty(ConfigConstant.MAIL_TEMPLATE_PATH.getName());
			// String mailTemplateFilePath = String.format("%1$s%2$s%3$s", mailTemplatePath, File.separator, mail.getTemplateName());
			Template template = velocityEngine.getTemplate(mail.getTemplateName());

			VelocityContext velocityContext = new VelocityContext();
			Set<String> fieldKeys = mail.getFields().keySet();
			Iterator<String> keyIterator = fieldKeys.iterator();
			while(keyIterator.hasNext()) {
				String fieldKey = keyIterator.next();
				String fieldValue = mail.getFields().get(fieldKey);
				velocityContext.put(fieldKey, fieldValue);
			}

			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			message.setText(stringWriter.toString());				
			mailSender.send(message);
		} catch (MailException ex) {
			ex.printStackTrace();
			logger.error(ex.getStackTrace());
			throw ex;
		}
	}

	@Override
	public void sendMail(Survey survey, Map<String, String> params, String filePath) throws MailException {
		if (survey == null) {
			return;
		}
		
		try {
			Mail mail = new Mail();
			Field advCompanyNameField = SurveyUtils.findFieldWithFieldTemplateSlugName(ConfigConstant.ADVERTISER_COMPANY_NAME.getName(), survey);			
			Field consultantNameField = SurveyUtils.findFieldWithFieldTemplateSlugName(ConfigConstant.CONSULTANT_NAME.getName(), survey);			
			
			// From mail
			String fromMail = ctx.getEnvironment().getProperty(ConfigConstant.MAIL_USERNAME.getName());
			mail.setMailFrom(fromMail);
			
			// To mail
			Field consultantMailField = SurveyUtils.findFieldWithFieldTemplateSlugName(ConfigConstant.CONSULTANT_EMAIL.getName(), survey);						
			String consultantMail = consultantMailField.getValue();
			mail.setMailTo(consultantMail);
			
			// Subject
			String mailSubjectPattern = ctx.getEnvironment().getProperty(ConfigConstant.NOTIFICATION_MAIL_SUBJECT.getName());
			String mailSubject = String.format(mailSubjectPattern, advCompanyNameField.getValue());
			mail.setMailSubject(mailSubject);
			
			// Template 
			String mailTemplateName = ctx.getEnvironment().getProperty(ConfigConstant.NOTIFICATION_MAIL_TEMPLATE_NAME.getName());
			mail.setTemplateName(mailTemplateName);
			
			/////////////////// Template fields /////////////////////////
			// Consultant name 
			params.put(ConfigConstant.CONSULTANT_NAME.getName(), consultantNameField.getValue());
			// Advertiser company name
			params.put(ConfigConstant.ADVERTISER_COMPANY_NAME.getName(), advCompanyNameField.getValue());
			// Fulfilled date
			String timezone = ctx.getEnvironment().getProperty(ConfigConstant.TIME_ZONE.getName());
			params.put(ConfigConstant.SURVEY_FULFILLED_DATE.getName(), survey.getFulfilledDate(timezone));
			mail.setFields(params);
			if (filePath.trim().length() > 0) {
				sendMailAttach(mail, filePath);
			} else {
				sendMail(mail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		}
	}

	@Override
	public void sendMailAttach(Mail mail, String filePath)
			throws MailException, MessagingException {
		try {
			SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom(mail.getMailFrom());
			message.setTo(mail.getMailTo());
			message.setSubject(mail.getMailSubject());

			// String mailTemplatePath =
			// ctx.getEnvironment().getProperty(ConfigConstant.MAIL_TEMPLATE_PATH.getName());
			// String mailTemplateFilePath = String.format("%1$s%2$s%3$s",
			// mailTemplatePath, File.separator, mail.getTemplateName());
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setFrom(message.getFrom());
			helper.setTo(message.getTo());
			helper.setSubject(message.getSubject());
			helper.setText(message.getSubject());
			FileSystemResource file = new FileSystemResource(filePath);
			helper.addAttachment(file.getFilename(), file);

			Template template = velocityEngine.getTemplate(mail
					.getTemplateName());

			VelocityContext velocityContext = new VelocityContext();
			Set<String> fieldKeys = mail.getFields().keySet();
			Iterator<String> keyIterator = fieldKeys.iterator();
			while (keyIterator.hasNext()) {
				String fieldKey = keyIterator.next();
				String fieldValue = mail.getFields().get(fieldKey);
				velocityContext.put(fieldKey, fieldValue);
			}

			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			helper.setText(stringWriter.toString());

			mailSender.send(mimeMessage);
		} catch (MailException ex) {
			ex.printStackTrace();
			logger.error(ex.getStackTrace());
			throw ex;
		}

	}
}
