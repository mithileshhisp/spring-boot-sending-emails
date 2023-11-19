package org.hispindia.emailservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hispindia.emailservice.model.Mail;
import org.hispindia.emailservice.pojo.ProgramOrganisationUnitBundle;
import org.hispindia.emailservice.pojo.ProgramOrganisationUnit;
import org.hispindia.emailservice.pojo.SqlViewQueryResponse;
import org.hispindia.emailservice.pojo.TrackedEntityInstance;
import org.hispindia.emailservice.pojo.TrackedEntityInstanceBundle;

import org.thymeleaf.context.Context;

public interface MailService 
{
    public void sendEmail(Mail mail);
    public void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context);
    public void sendEmailWithHTMLContent(String subject, String htmlMsg, String sendTo);
    public ProgramOrganisationUnitBundle getProgramOrganisationUnitFromApi(String url);
    public Set<ProgramOrganisationUnit> getProgramOrganisationUnitEmailFromApi(String url);

    public TrackedEntityInstanceBundle getTrackedEntityInstancesFromApi(String url);
    public Set<TrackedEntityInstance>  getTrackedEntityInstancesList(String url);
    public Map<String, String> getTrackedEntityInstancesAttributeValues(String url);
    public Map<String, String> getTrackedEntityInstancesEventDataValues(String url);

    public List<Map<String, String>> getTrackedEntityInstancesDetails(String url);

    public SqlViewQueryResponse getSqlViewResponse(String url);
    //public List<List<String>> getSqlViewQueryResponse(String url);
    public List<String> getSqlViewQueryResponse(String url);


}
