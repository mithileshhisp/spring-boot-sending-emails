package org.hispindia.emailservice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.hispindia.emailservice.model.Mail;
import org.hispindia.emailservice.service.MailService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hispindia.emailservice.pojo.ProgramOrganisationUnitBundle;
import org.hispindia.emailservice.pojo.ProgramOrganisationUnit;
import org.hispindia.emailservice.pojo.TrackedEntityInstanceBundle;
import org.hispindia.emailservice.util.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@SpringBootApplication
@Component
public class EmailServiceApplication
{
        final Logger logger = LoggerFactory.getLogger(EmailServiceApplication.class);

        @Autowired
	private MailService mailService;

        @Autowired
        private JdbcTemplate jdbcTemplate;

	@Value("${app.dhis2.api.ProgramOrganisationUnits}")
        String dhis2ProgramOrganisationUnitsURL;

	@Value("${app.dhis2.api.TrackedEntityInstances}")
        String dhis2TrackedEntityInstancesURL;

	@Value("${app.dhis2.api.SqlVIew}")
        String dhis2SqlViewURL;

	public static void main(String[] args) {
            SpringApplication.run(EmailServiceApplication.class, args);
	}

        public void run() throws Exception 
	{
            logger.info("sending email start: "  );

            LocalDate start = LocalDate.parse("2021-12-01");
            //LocalDate end   = LocalDate.parse("2023-06-15");
            LocalDate end   = LocalDate.parse(LocalDate.now().toString());

            long diffInDays = ChronoUnit.DAYS.between(start, end);
            System.out.println(" diffInDays -- " + diffInDays);

            String newline = System.lineSeparator();  
            // optionSet option with meta atribute
            String dhis2ProgramURL = dhis2ProgramOrganisationUnitsURL + ".json?" + ParamsUtil.PROGRAM_ORGUNIT_PARAM;
            System.out.println( "dhis2ProgramURL -- " + dhis2ProgramURL );
            
            //ProgramOrganisationUnitBundle programOrganisationUnitBundle = mailService.getProgramOrganisationUnitEmailFromApi(dhis2ProgramURL);
            
            Set<ProgramOrganisationUnit> programOrganisationUnitList = new HashSet<>( mailService.getProgramOrganisationUnitEmailFromApi(dhis2ProgramURL) );
            System.out.println(" email size -- " + programOrganisationUnitList.size());            
            
            for(ProgramOrganisationUnit programOrganisationUnit : programOrganisationUnitList){

                String emailSubject = "PrEP_Miss-Appointment Client list of " + programOrganisationUnit.getDisplayName();
                    
                String emailText= "<html><body><P>" + "Dear PrEP provider, " + "<br>" + 
                    "    DHIS2 Tracker system is sending you a list of clients who missed the appointment>=7 days after their last scheduled date for your necessary action. " + "<br></p>" +

                    "<table width='100%' border='1' align='left'>"
                           + "<tr align='left' >"
                           /*+ "<td style='background-color:blue;color:white' ><b>TEI-UID-- count<b></td>"*/
                           + "<td style='background-color:blue;color:white' ><b>Client's ID<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Client's PrEP ID<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Client's Sex<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>State/Region<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Township<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Facility <b></td>"
                           + "<td style='background-color:blue;color:white' ><b>PrEP Initiation Date<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Last Visit Date at PrEP clinic<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Last PrEP ARV taken<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Last Next schedule Date<b></td>"
                           + "<td style='background-color:blue;color:white' ><b>Days of missed appointment since last schedule date<b></td>"
                           + "</tr>";

                String dhis2TEIURL = dhis2TrackedEntityInstancesURL + "&ou=" + programOrganisationUnit.getId() + ParamsUtil.TEI_PARAM;
                System.out.println( "dhis2TEIURL -- " + dhis2TEIURL );

                List<Map<String, String>> trackedEntityInstanceDetailList = new ArrayList<>( mailService.getTrackedEntityInstancesDetails(dhis2TEIURL) );

                Map<String, String> teiAttributeValuesMap =  new HashMap<>();
                Map<String, String> teiEventDataValuesMap =  new HashMap<>();

                teiAttributeValuesMap =  new HashMap<>( trackedEntityInstanceDetailList.get(0));
                teiEventDataValuesMap =  new HashMap<>( trackedEntityInstanceDetailList.get(1));

                System.out.println(" TEI size -- " + teiAttributeValuesMap.size());
                for(String teiUIDKey : teiAttributeValuesMap.keySet()) {
                    //System.out.println( " orgUnitName -- " + programOrganisationUnit.getDisplayName() +  " teiUID -- " + teiUIDKey.split(":")[0] + " keyValue 1 -- " + teiAttributeValuesMap.get(teiUIDKey.split(":")[0] + ":" + "n2gG7cdigPc") + " keyValue 2 -- " + teiAttributeValuesMap.get(teiUIDKey.split(":")[0] + ":" + "P3Spi0kT92n"));
                    String traceOutcomeStageEventURL = "var=prgStageUID:uMYfp6QX2d0&var=teiUID:"+teiUIDKey.split(":")[0];
                    String tempDHIS2SqlViewURLTraceOutcome = dhis2SqlViewURL + "uxSLQ0uffI4/data.json?paging=false&" + traceOutcomeStageEventURL;
                    //System.out.println( "tempDHIS2SqlViewURLTraceOutcome -- " + tempDHIS2SqlViewURLTraceOutcome );                    
                    List<String> traceOutcomeEventUid = new ArrayList<>( mailService.getSqlViewQueryResponse(tempDHIS2SqlViewURLTraceOutcome) );

                    if( !traceOutcomeEventUid.isEmpty() && teiEventDataValuesMap.get(teiUIDKey.split(":")[0] + ":" + traceOutcomeEventUid.get(1) + ":" + "zkFIdGgOThz") != null ){
                        logger.info("email not send to : " + programOrganisationUnit.getEmail()  );
                        System.out.println( "tempDHIS2SqlViewURLTraceOutcome -- " + tempDHIS2SqlViewURLTraceOutcome ); 
                    }
                    else{
                        String clientPrEPID = "";
                        if( teiAttributeValuesMap.get(teiUIDKey.split(":")[0] + ":" + "n2gG7cdigPc") != null )
                        {
                            clientPrEPID = teiAttributeValuesMap.get(teiUIDKey.split(":")[0] + ":" + "n2gG7cdigPc");
                        }
                        String client_ID = "";
                        if( teiAttributeValuesMap.get(teiUIDKey.split(":")[0] + ":" + "P3Spi0kT92n") != null )
                        {
                            client_ID = teiAttributeValuesMap.get(teiUIDKey.split(":")[0] + ":" + "P3Spi0kT92n");
                        }

                        String sqlViewParam =  "var=deUID:QB79pRV2LqV&var=orgUID:"+ programOrganisationUnit.getId()+"&var=teiUID:"+teiUIDKey.split(":")[0];
                        String tempDHIS2SqlViewURL = dhis2SqlViewURL + "Q9LGwm4cqeG/data.json?paging=false&" + sqlViewParam;
                        //System.out.println( "tempDHIS2SqlViewURL -- " + tempDHIS2SqlViewURL );
                        List<String> sqlViewData = new ArrayList<>( mailService.getSqlViewQueryResponse(tempDHIS2SqlViewURL) );

                        String eventUID = "", event_date = "", last_next_schedule_date = "",dayDiffrence="";
                        String prepEventUID = "", prepTEIUID = "";
                        if(!sqlViewData.isEmpty()){
                            eventUID = sqlViewData.get(1);
                            event_date = sqlViewData.get(4);
                            last_next_schedule_date = sqlViewData.get(5);
                            dayDiffrence = sqlViewData.get(6);

                            String prEPScreeningStageEventURL = "var=prgStageUID:BrZ8MF97cDH&var=teiUID:"+teiUIDKey.split(":")[0];
                            String tempDHIS2SqlViewURLPrep = dhis2SqlViewURL + "uxSLQ0uffI4/data.json?paging=false&" + prEPScreeningStageEventURL;
                            List<String> prEPScreeningStageEventUid = new ArrayList<>( mailService.getSqlViewQueryResponse(tempDHIS2SqlViewURLPrep) );

                            if(!prEPScreeningStageEventUid.isEmpty()){
                                prepTEIUID = prEPScreeningStageEventUid.get(0);
                                prepEventUID = prEPScreeningStageEventUid.get(1);
                            }

                            String client_sex = "",client_state= "",client_township = "";
                            if( teiEventDataValuesMap.get(prepTEIUID + ":" + prepEventUID + ":" + "kQzpqh4JL7l") != null )
                            {
                                client_sex = teiEventDataValuesMap.get(prepTEIUID + ":" + prepEventUID + ":" + "kQzpqh4JL7l");
                            }

                            if( teiEventDataValuesMap.get(prepTEIUID + ":" + prepEventUID + ":" + "nVU4BU2jHc3") != null )
                            {
                                client_state = teiEventDataValuesMap.get(prepTEIUID + ":" + prepEventUID + ":" + "nVU4BU2jHc3");
                            }
                            if( teiEventDataValuesMap.get(prepTEIUID + ":" + prepEventUID + ":" + "x3s6CXPdNjd") != null )
                            {
                                client_township = teiEventDataValuesMap.get(prepTEIUID + ":" + prepEventUID + ":" + "x3s6CXPdNjd");
                            }

                            emailText = emailText 
                               +"<tr align='left'>"
                               /*+ "<td align='left' ><b>" + i + " -- "+ trackedEntityInstance.getUid() + "<b></td>"*/
                               + "<td align='left' ><b>" + client_ID + "<b></td>"
                               + "<td align='left' ><b>" + clientPrEPID + "<b></td>"
                               + "<td align='left' ><b>" + client_sex + "<b></td>"
                               + "<td align='left' ><b>" + client_state + "<b></td>"
                               + "<td align='left' ><b>" + client_township + "<b></td>"
                               + "<td align='left' ><b>" + programOrganisationUnit.getDisplayName() + "<b></td>"
                               + "<td align='left' ><b>" + teiEventDataValuesMap.get(prepTEIUID + ":" + prepEventUID + ":" + "ts9LEoIEJWC") + "<b></td>"
                               + "<td align='left' ><b>" + event_date + "<b></td>"
                               + "<td align='left' ><b>" + teiEventDataValuesMap.get(teiUIDKey.split(":")[0] + ":" + eventUID + ":" + "icJeQiH7vf3") + "<b></td>"
                               + "<td align='left' ><b>" + last_next_schedule_date + "<b></td>"
                               + "<td align='left' ><b>" + dayDiffrence + "<b></td>"
                               +"</tr>";
                        }

                    }


                }

                //System.out.println(" size -- " + teiAttributeValuesMap.size()); 

/*
                TrackedEntityInstanceBundle trackedEntityInstanceBundle = mailService.getTrackedEntityInstancesFromApi(dhis2TEIURL);
                
                System.out.println(" Attributes Value -- " + trackedEntityInstanceBundle.getTrackedEntityInstances().get(0).getAttributes().get(0).getValue() );
                System.out.println(" data Value -- " + trackedEntityInstanceBundle.getTrackedEntityInstances().get(0).getEnrollments().get(0).getEvents().get(0).getDataValues().get(0).getValue() );
                System.out.println(" size -- " + trackedEntityInstanceBundle.getTrackedEntityInstances().size());

                //if( programOrganisationUnit.getEmail() != null){
                    System.out.println(" -- " + programOrganisationUnit.getId() + " -- " + programOrganisationUnit.getDisplayName() + " -- " + programOrganisationUnit.getEmail()  );
                //}
*/
/*
                Mail mail = new Mail();
                mail.setMailFrom("ipamis@hispindia.org");
                mail.setMailTo( programOrganisationUnit.getEmail());
                mail.setMailSubject(emailSubject);
                mail.setMailContent(emailText);
                mailService.sendEmail(mail);
*/
                //Context context = new Context();
                //context.setVariable("message", emailText);

                //emailService.sendEmailWithHtmlTemplate(emailRequest.getTo(), emailRequest.getSubject(), "email-template", context);

                //mailService.sendEmailWithHtmlTemplate(programOrganisationUnit.getEmail(), emailSubject, emailText, context);
                mailService.sendEmailWithHTMLContent(emailSubject, emailText, programOrganisationUnit.getEmail());
                logger.info("email send to : " + programOrganisationUnit.getEmail()  );
            }

            logger.info("sending email end: "  );
	}
//
//https://train-dhis-mm.icap.baosystems.com/api/trackedEntityInstances.json?ouMode=DESCENDANTS&program=bASezt1TUKD&ou=ikjUpqWKy7H&order=lastUpdated:desc&skipPaging=true
//https://links.hispindia.org/prep_tracker/api/trackedEntityInstances.json?skipPaging=true&program=bASezt1TUKD&ou=Cc2ntGA27wX&fields=orgUnit,trackedEntityInstance,attributes[attribute,value],enrollments[orgUnitName,events[programStage,eventDate,dataValues[dataElement,value]]]
//https://train-dhis-mm.icap.baosystems.com/api/trackedEntityInstances/NhQtcUiPOXi.json?skipPaging=true&program=bASezt1TUKD&ou=RZjIN6Adcdr&ouMode=DESCENDANTS&fields=orgUnit,trackedEntityInstance,attributes[attribute,value],enrollments[orgUnitName,events[programStage,eventDate,dataValues[dataElement,value]]]
//https://train-dhis-mm.icap.baosystems.com/api/trackedEntityInstances.json?skipPaging=true&program=bASezt1TUKD&ou=RZjIN6Adcdr&ouMode=DESCENDANTS&fields=orgUnit,trackedEntityInstance,attributes[attribute,value],enrollments[orgUnitName,events[programStage,eventDate,dataValues[dataElement,value]]]
//https://train-dhis-mm.icap.baosystems.com/api/trackedEntityInstances/NhQtcUiPOXi.json?skipPaging=true&program=bASezt1TUKD&ou=RZjIN6Adcdr&fields=orgUnit,trackedEntityInstance,attributes[attribute,value],enrollments[orgUnitName,events[event,programStage,eventDate,dataValues[dataElement,value]]]
//https://train-dhis-mm.icap.baosystems.com/api/trackedEntityInstances.json?skipPaging=true&program=bASezt1TUKD&ou=ikjUpqWKy7H&fields=orgUnit,trackedEntityInstance,attributes[attribute,value],enrollments[orgUnitName,events[event,programStage,eventDate,dataValues[dataElement,value]]]
        public void getProgramOrganisationUnitList()
        {
            //Set<OrganisationUnit> programOrgList = new HashSet<OrganisationUnit>();
            try
            {

                String query = "SELECT organisationunitid,uid,name,email from organisationunit where email is not null "
                    + "and organisationunitid in ( select organisationunitid from program_organisationunits) ";

                //SqlRowSet rs = jdbcTemplate.queryForRowSet( query );
                SqlRowSet rs = jdbcTemplate.queryForRowSet( query );

                while ( rs.next() )
                {
                    Long orgUnitId = rs.getLong( 1 );
                    String uid = rs.getString( 2 );
                    String name = rs.getString( 3 );
                    String email = rs.getString( 4 );

                    if ( email != null )
                    {
                        System.out.println("name: " + name + " email : " + email );
                    }
                }
            }
            catch ( Exception e )
            {
                throw new RuntimeException( "Illegal programOrgList ", e );
            }
        }

        public void getTrackedEntityInstanceList( String orgUnitUid )
        {

            //orgUnitUid = "Cc2ntGA27wX";
            //Set<TrackedEntityInstance> trackedEntityInstanceList = new HashSet<TrackedEntityInstance>();
            try
            {

                String query = "SELECT tei.uid AS teiUID FROM programinstance pi "
                               + "INNER JOIN trackedentityinstance tei ON tei.trackedentityinstanceid = pi.trackedentityinstanceid " 
                               + "INNER JOIN organisationunit org ON pi.organisationunitid = org.organisationunitid " 
                               + "WHERE org.uid = '" + orgUnitUid + "' ";

                SqlRowSet rs = jdbcTemplate.queryForRowSet( query );

                while ( rs.next() )
                {
                    String teiUId = rs.getString( 1 );

                    if ( teiUId != null  )
                    {
                        //TrackedEntityInstance tei = trackedEntityInstanceService.getTrackedEntityInstance( teiUId );
                        //trackedEntityInstanceList.add( tei );
                        System.out.println("teiUId: " + teiUId  );
                    }
                }

                //return trackedEntityInstanceList;
            }
            catch ( Exception e )
            {
                throw new RuntimeException( "Illegal orgUnitUid ", e );
            }
        }

}
