package org.hispindia.emailservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.hispindia.emailservice.EmailServiceApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.hispindia.emailservice.model.Mail;
import org.hispindia.emailservice.pojo.Attributes;
import org.hispindia.emailservice.pojo.DataValues;
import org.hispindia.emailservice.pojo.Enrollments;
import org.hispindia.emailservice.pojo.Events;
import org.hispindia.emailservice.pojo.ListGrid;
import org.hispindia.emailservice.pojo.ProgramOrganisationUnitBundle;
import org.hispindia.emailservice.pojo.ProgramOrganisationUnit;

import org.hispindia.emailservice.pojo.SqlViewQueryResponse;

import org.hispindia.emailservice.pojo.TrackedEntityInstance;
import org.hispindia.emailservice.pojo.TrackedEntityInstanceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService
{
        final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	//@Autowired
	private JavaMailSender javaMailSender;

        private final TemplateEngine templateEngine;

	private HttpEntity<Void> request;
        private RestTemplate restTemplate;  
        private HttpHeaders authHeaders;

        @Autowired
        public MailServiceImpl(RestTemplate restTemplate, @Qualifier("Dhis2")HttpHeaders authHeaders, JavaMailSender javaMailSender,
                                TemplateEngine templateEngine) {
                request = new HttpEntity<Void>(authHeaders);
                //restTemplate = new RestTemplate();
                this.restTemplate = restTemplate;
                this.authHeaders = authHeaders;
                this.javaMailSender = javaMailSender;
                this.templateEngine = templateEngine;
        }

	@Override
	public void sendEmail(Mail mail) 
	{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setSubject(mail.getMailSubject());
                    mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
                    mimeMessageHelper.setTo(mail.getMailTo());
                    mimeMessageHelper.setText(mail.getMailContent());
                    javaMailSender.send(mimeMessageHelper.getMimeMessage());
            } 
            catch (MessagingException e) {
                    e.printStackTrace();
            }
	}
   
        public void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context) {
            //MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            try {
                helper.setTo(to);
                helper.setSubject(subject);
                String htmlContent = templateEngine.process(templateName, context);
                helper.setText(htmlContent, true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                // Handle exception
            }
        }
        public void sendEmailWithHTMLContent(String subject, String htmlMsg, String sendTo)  {
            //MimeMessage message = mailSender.createMimeMessage();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
                mimeMessage.setContent(htmlMsg, "text/html");
                helper.setTo(sendTo);
                helper.setSubject(subject);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                //log.error(e.toString());
                logger.error(e.toString());
            }
        }
        @Override
	public ProgramOrganisationUnitBundle getProgramOrganisationUnitFromApi(String url) {

            ResponseEntity<ProgramOrganisationUnitBundle> programOrganisationUnit = restTemplate.exchange(url, HttpMethod.GET, request, ProgramOrganisationUnitBundle.class);
            //System.out.println(" url -- " + url );
            //System.out.println(" programOrganisationUnit -- " + programOrganisationUnit.getBody() );            
            return programOrganisationUnit.getBody();
	}

        @Override
	public Set<ProgramOrganisationUnit> getProgramOrganisationUnitEmailFromApi(String url) {

            Set<ProgramOrganisationUnit> programOrganisationUnitList = new HashSet<>();
            
            ResponseEntity<ProgramOrganisationUnitBundle> programOrganisationUnitBundle = restTemplate.exchange(url, HttpMethod.GET, request, ProgramOrganisationUnitBundle.class);
            
            //return programOrganisationUnit.getBody();
            if( programOrganisationUnitBundle.getBody().getOrganisationUnits() != null ){
                for(ProgramOrganisationUnit programOrganisationUnit : programOrganisationUnitBundle.getBody().getOrganisationUnits() ) {
                    if( programOrganisationUnit.getEmail() != null){
                        programOrganisationUnitList.add(programOrganisationUnit);
                    }
                }
            }
            return programOrganisationUnitList;
	}

        @Override
	public TrackedEntityInstanceBundle getTrackedEntityInstancesFromApi(String url) {

            ResponseEntity<TrackedEntityInstanceBundle> trackedEntityInstanceBundle = restTemplate.exchange(url, HttpMethod.GET, request, TrackedEntityInstanceBundle.class);
            //System.out.println(" url -- " + url );
            //System.out.println(" trackedEntityInstanceBundle -- " + trackedEntityInstanceBundle.getBody() );            
            return trackedEntityInstanceBundle.getBody();
	}

        @Override
	public Set<TrackedEntityInstance> getTrackedEntityInstancesList(String url) {

            Set<TrackedEntityInstance> trackedEntityInstanceList = new HashSet<>();
            
            ResponseEntity<TrackedEntityInstanceBundle> trackedEntityInstanceBundle = restTemplate.exchange(url, HttpMethod.GET, request, TrackedEntityInstanceBundle.class);
            
            //return programOrganisationUnit.getBody();
            if( trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() != null ){
                for(TrackedEntityInstance trackedEntityInstance : trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() ) {
                    if( trackedEntityInstance.getTrackedEntityInstance() != null){
                        trackedEntityInstanceList.add(trackedEntityInstance);
                    }
                }
            }
            return trackedEntityInstanceList;
	}

        @Override
	public Map<String, String> getTrackedEntityInstancesAttributeValues(String url) {

            Map<String, String> teiAttributeValuesMap =  new HashMap<>();

            ResponseEntity<TrackedEntityInstanceBundle> trackedEntityInstanceBundle = restTemplate.exchange(url, HttpMethod.GET, request, TrackedEntityInstanceBundle.class);

            if( trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() != null ){
                for(TrackedEntityInstance trackedEntityInstance : trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() ) {
                    for(Attributes attribute : trackedEntityInstance.getAttributes() ){
                        if( attribute.getValue() != null ){
                            String teiAttributeKey = trackedEntityInstance.getTrackedEntityInstance() + ":" + attribute.getAttribute();
                            teiAttributeValuesMap.put(teiAttributeKey, attribute.getValue());
                        }
                    }
                }
            }
            return teiAttributeValuesMap;
	}

        @Override
	public Map<String, String> getTrackedEntityInstancesEventDataValues(String url) {

            Map<String, String> teiEventDataValuesMap =  new HashMap<>();
            
            ResponseEntity<TrackedEntityInstanceBundle> trackedEntityInstanceBundle = restTemplate.exchange(url, HttpMethod.GET, request, TrackedEntityInstanceBundle.class);
            
            if( trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() != null ){
                for(TrackedEntityInstance trackedEntityInstance : trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() ) {
                    for(Enrollments enrollment : trackedEntityInstance.getEnrollments() ){
                        for(Events event : enrollment.getEvents() ){
                            for(DataValues dataValue : event.getDataValues() ){
                                if( dataValue.getValue() != null ){
                                    String teiEventDataValueKey = trackedEntityInstance.getTrackedEntityInstance() + ":" + event.getEvent() + ":" + dataValue.getDataElement();
                                    teiEventDataValuesMap.put(teiEventDataValueKey, dataValue.getValue());
                                }
                            }
                        }

                    }
                }
            }
            return teiEventDataValuesMap;
	}

        @Override
	public List<Map<String, String>>  getTrackedEntityInstancesDetails(String url) {

            List<Map<String, String>> trackedEntityInstanceDetailList = new ArrayList<>();
            Map<String, String> teiAttributeValuesMap =  new HashMap<>();
            //Map<String, String> teiOrgUnitNameMap =  new HashMap<>();
            Map<String, String> teiEventDataValuesMap =  new HashMap<>();
            
            ResponseEntity<TrackedEntityInstanceBundle> trackedEntityInstanceBundle = restTemplate.exchange(url, HttpMethod.GET, request, TrackedEntityInstanceBundle.class);
            
            if( trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() != null ){
                for(TrackedEntityInstance trackedEntityInstance : trackedEntityInstanceBundle.getBody().getTrackedEntityInstances() ) {
                    for(Attributes attribute : trackedEntityInstance.getAttributes() ){
                        if( attribute.getAttribute() != null && attribute.getValue() != null ){
                            String teiAttributeKey = trackedEntityInstance.getTrackedEntityInstance() + ":" + attribute.getAttribute();
                            teiAttributeValuesMap.put(teiAttributeKey, attribute.getValue());
                        }
                    }
                    for(Enrollments enrollment : trackedEntityInstance.getEnrollments() ){
                        //teiOrgUnitNameMap.put(trackedEntityInstance.getTrackedEntityInstance(), enrollment.getOrgUnitName());

                        for(Events event : enrollment.getEvents() ){
                            for(DataValues dataValue : event.getDataValues() ){
                                if( dataValue.getValue() != null ){
                                    String teiEventDataValueKey = trackedEntityInstance.getTrackedEntityInstance() + ":" + event.getEvent() + ":" + dataValue.getDataElement();
                                    teiEventDataValuesMap.put(teiEventDataValueKey, dataValue.getValue());
                                }
                            }
                        }
                    }
                }
            }

            trackedEntityInstanceDetailList.add(0, teiAttributeValuesMap);
            trackedEntityInstanceDetailList.add(1, teiEventDataValuesMap);
            //trackedEntityInstanceDetailList.add(1, teiOrgUnitNameMap);
            
            //trackedEntityInstanceDetailList.add(3, teiEventDataValuesMap);
 
            return trackedEntityInstanceDetailList;
	}

        @Override
	public SqlViewQueryResponse getSqlViewResponse(String url) {

            ResponseEntity<SqlViewQueryResponse> sqlViewResponse = restTemplate.exchange(url, HttpMethod.GET, request, SqlViewQueryResponse.class);    
            return sqlViewResponse.getBody();
	}

        @Override
	public List<String> getSqlViewQueryResponse(String url) {

            List<String> sqlViewRow = new ArrayList<String>();

            //QueryResponse responseMapped = null;
            //ResponseEntity<ListGrid> listGrid = restTemplate.exchange(url, HttpMethod.GET, request, ListGrid.class);
            ResponseEntity<SqlViewQueryResponse> sqlViewQueryResponse = restTemplate.exchange(url, HttpMethod.GET, request, SqlViewQueryResponse.class);

            //System.out.println(" sqlViewResponse -- " + sqlViewQueryResponse.getBody() ); 
            //return sqlViewResponse.getBody();
            List<List<String>> rows = sqlViewQueryResponse.getBody().getListGrid().getRows();
            if(!rows.isEmpty()){
                sqlViewRow = rows.get(0);
            }
            //return rows.get(0);
            return sqlViewRow;
	}

}
