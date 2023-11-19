package org.hispindia.emailservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduler {
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
        @Autowired private EmailServiceApplication emailServiceApplication;
	
       @Scheduled(cron = "${app.schedule.cronExpression}")
	public void sendEmail() throws Exception {
            logger.info("LocalDate : " + LocalDate.now().toString());
            logger.info("sending email started at: " + LocalDateTime.now().toString());
            //emailServiceApplication.run();

           try {
                emailServiceApplication.run();
            } catch (MessagingException e) {
                //log.error(e.toString());
                logger.error(e.toString());
            }

            logger.info("ending email end at: " + LocalDateTime.now().toString());
		
	}
}
