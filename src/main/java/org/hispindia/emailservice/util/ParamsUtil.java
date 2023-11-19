package org.hispindia.emailservice.util;

/**
 * @author Mithilesh Thakur
 */
public class ParamsUtil {

    public static String PAGER_PARAM = "paging=false";
    public static String PROGRAM_FIELDS_PARAM = "fields=id,displayName,programStages";
    public static String REF_DATE_PARAM = "refDate=2019-01-01";
    public static String PATIENT_PAGE_OFFSET_TEMP = "page-offset=1";
    public static String CLAIM_PAGE_OFFSET_TEMP = "page-offset=100";
    public static String CLAIM_PAGE_SIZE = "_count=1";
    public static String TRACKED_ENTITY_ATTRIBUTES_PARAM="paging=false&fields=displayName,shortName,id,code,href,optionSet";
    public static String DATA_ELEMENTS_PARAM="paging=false&fields=displayName,shortName,id,code,href,optionSet";
    public static String ORG_UNITS_PARAM="paging=false&fields=id,displayName,code";
    public static String ORG_UNITS_SYNC_PARAM="fields=id,displayName,shortName,code,level,attributeValues[attribute[id,displayName,code],value]&sortOrder=ASC&paging=false";
    public static String OPTION_PARAM="fields=options[id,name,code,attributeValues[attribute[id,displayName,code],value]]";
    public static String ENROLLMENT_PARAM="fields=trackedEntityInstance,orgUnit,enrollment,enrollmentDate,incidentDate,status";

    public static String PROGRAM_ORGUNIT_PARAM="fields=organisationUnits[id,displayName,email]";
    //public static String TEI_PARAM="&fields=orgUnit,trackedEntityInstance,attributes[attribute,value],enrollments[orgUnitName,events[event,programStage,eventDate,dataValues[dataElement,value]]]";
    public static String TEI_PARAM="&fields=orgUnit,trackedEntityInstance,attributes[attribute,value],enrollments[orgUnitName,events[event,programStage,eventDate,dataValues[dataElement,value]]]";
}

