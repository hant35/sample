package com.panpages.bow.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
public class MailConfig {
	
	@Autowired
    private Environment environment;
	
	@Bean
	JavaMailSender mailSender() {
		JavaMailSenderImpl mailSenderInstance = new JavaMailSenderImpl();
		mailSenderInstance.setHost(getEnvironment().getRequiredProperty(ConfigConstant.MAIL_HOST.getName()));
		mailSenderInstance.setPort(Integer.parseInt(getEnvironment().getRequiredProperty(ConfigConstant.MAIL_PORT.getName())));
		mailSenderInstance.setUsername(getEnvironment().getRequiredProperty(ConfigConstant.MAIL_USERNAME.getName()));
		mailSenderInstance.setPassword(getEnvironment().getRequiredProperty(ConfigConstant.MAIL_PASSWORD.getName()));
		
		Properties mailProps = new Properties();
		mailProps.put("mail.debug", "true"); 
	    mailProps.put("mail.smtp.auth", "true");
	    mailProps.put("mail.smtp.starttls.enable", "true");
	    mailProps.put("mail.mime.charset", "UTF-8");
	    mailProps.put("mail.transport.protocol", "smtp");
	    mailProps.put("mail.smtp.ssl.trust", getEnvironment().getRequiredProperty(ConfigConstant.MAIL_HOST.getName()));
		mailSenderInstance.setJavaMailProperties(mailProps);
		
		return mailSenderInstance;
	}
	
	@Bean
	VelocityEngine velocityEngine() throws VelocityException, IOException {
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		
		Properties velocityProps = new Properties();
		velocityProps.put("resource.loader", "file");
		velocityProps.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		velocityProps.put("file.resource.loader.path", getEnvironment().getRequiredProperty(ConfigConstant.MAIL_TEMPLATE_PATH.getName()));
		
		factory.setVelocityProperties(velocityProps);
        
		return factory.createVelocityEngine();
	}
	
	public Environment getEnvironment() {
		return environment;
	}
		
}
