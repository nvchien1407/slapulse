//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek;

/**
 * Constant values used throughout the application.
 * <p/>
 * <p>
 * <a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class Constants {

   //~ Static fields/initializers =============================================

   /**
    * The name of the ResourceBundle used in this application
    */
   public static final String BUNDLE_KEY = "ApplicationResources";
   public static final String POWER_GROUP = "PowerUserGroup";
   /**
    * The application scoped attribute for persistence engine used
    */
   public static final String CALENDARDATE_KEY = "calendarDateForm";

   /**
    * The request scope attribute that holds the calendarDate list
    */
   public static final String CALENDARDATE_LIST = "calendarDateList";
   public static final String DAO_TYPE = "daoType";
   public static final String DAO_TYPE_HIBERNATE = "hibernate";

   /**
    * Application scoped attribute for authentication url
    */
   public static final String AUTH_URL = "authURL";

   /**
    * Application scoped attributes for SSL Switching
    */
   public static final String HTTP_PORT = "httpPort";
   public static final String HTTPS_PORT = "httpsPort";

   /**
    * The application scoped attribute for indicating a secure login
    */
   public static final String SECURE_LOGIN = "secureLogin";

   /**
    * The encryption algorithm key to be used for passwords
    */
   public static final String ENC_ALGORITHM = "algorithm";

   /**
    * A flag to indicate if passwords should be encrypted
    */
   public static final String ENCRYPT_PASSWORD = "encryptPassword";

   /**
    * File separator from System properties
    */
   public static final String FILE_SEP = System.getProperty("file.separator");

   /**
    * User home from System properties
    */
   public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

   /**
    * The session scope attribute under which the breadcrumb ArrayStack is
    * stored
    */
   public static final String BREADCRUMB = "breadcrumbs";

   /**
    * The session scope attribute under which the User object for the
    * currently logged in user is stored.
    */
   public static final String USER_KEY = "currentUserForm";

   /**
    * The request scope attribute under which an editable user form is stored
    */
   public static final String USER_EDIT_KEY = "userForm";

   /**
    * The request scope attribute that holds the user list
    */
   public static final String USER_LIST = "userList";

   /**
    * The request scope attribute for indicating a newly-registered user
    */
   public static final String REGISTERED = "registered";

   /**
    * The name of the Administrator role, as specified in web.xml
    */
   public static final String ADMIN_ROLE = "admin";

   /**
    * The name of the User role, as specified in web.xml
    */
   public static final String USER_ROLE = "tomcat";

   /**
    * The name of the user's role list, a request-scoped attribute
    * when adding/editing a user.
    */
   public static final String USER_ROLES = "userRoles";

   /**
    * The name of the available roles list, a request-scoped attribute
    * when adding/editing a user.
    */
   public static final String AVAILABLE_ROLES = "availableRoles";

   /**
    * Name of cookie for "Remember Me" functionality.
    */
   public static final String LOGIN_COOKIE = "sessionId";

   /**
    * The name of the configuration hashmap stored in application scope.
    */
   public static final String CONFIG = "appConfig";

   //ApprovalsScope-START
   /**
    * The request scope attribute that holds the approvalsScope form.
    */
   public static final String APPROVALSSCOPE_KEY = "approvalsScopeForm";

   /**
    * The request scope attribute that holds the approvalsScope list
    */
   public static final String APPROVALSSCOPE_LIST = "approvalsScopeList";
   //ApprovalsScope-END

   //AuditDoc-START
   /**
    * The request scope attribute that holds the auditDoc form.
    */
   public static final String AUDITDOC_KEY = "auditDocForm";

   /**
    * The request scope attribute that holds the auditDoc list
    */
   public static final String AUDITDOC_LIST = "auditDocList";
   //AuditDoc-END

   //Auditor-START
   /**
    * The request scope attribute that holds the auditor form.
    */
   public static final String AUDITOR_KEY = "auditorForm";

   /**
    * The request scope attribute that holds the auditor list
    */
   public static final String AUDITOR_LIST = "auditorList";
   //Auditor-END

   //ChangeLog-START
   /**
    * The request scope attribute that holds the changeLog form.
    */
   public static final String CHANGELOG_KEY = "changeLogForm";

   /**
    * The request scope attribute that holds the changeLog list
    */
   public static final String CHANGELOG_LIST = "changeLogList";
   //ChangeLog-END

   //Dia-START
   /**
    * The request scope attribute that holds the dia form.
    */
   public static final String DIA_KEY = "diaForm";

   /**
    * The request scope attribute that holds the dia list
    */
   public static final String DIA_LIST = "diaList";
   //Dia-END

   //Reference-START
   /**
    * The request scope attribute that holds the reference form.
    */
   public static final String REFERENCE_KEY = "referenceForm";

   /**
    * The request scope attribute that holds the reference list
    */
   public static final String REFERENCE_LIST = "referenceList";
   //Reference-END

   //Supplier-START
   /**
    * The request scope attribute that holds the supplier form.
    */
   public static final String SUPPLIER_KEY = "supplierForm";

   /**
    * The request scope attribute that holds the supplier list
    */
   public static final String SUPPLIER_LIST = "supplierList";
   //Supplier-END

   //SupplierApprovalsScope-START
   /**
    * The request scope attribute that holds the supplierApprovalsScope form.
    */
   public static final String SUPPLIERAPPROVALSSCOPE_KEY = "supplierApprovalsScopeForm";

   /**
    * The request scope attribute that holds the supplierApprovalsScope list
    */
   public static final String SUPPLIERAPPROVALSSCOPE_LIST = "supplierApprovalsScopeList";
   //SupplierApprovalsScope-END

   //SupplierAudit-START
   /**
    * The request scope attribute that holds the supplierAudit form.
    */
   public static final String SUPPLIERAUDIT_KEY = "supplierAuditForm";

   /**
    * The request scope attribute that holds the supplierAudit list
    */
   public static final String SUPPLIERAUDIT_LIST = "supplierAuditList";
   //SupplierAudit-END

   //SupplierUpdatesNotify-START
   /**
    * The request scope attribute that holds the supplierUpdatesNotify form.
    */
   public static final String SUPPLIERUPDATESNOTIFY_KEY = "supplierUpdatesNotifyForm";

   /**
    * The request scope attribute that holds the supplierUpdatesNotify list
    */
   public static final String SUPPLIERUPDATESNOTIFY_LIST = "supplierUpdatesNotifyList";
   //SupplierUpdatesNotify-END

   public static final String HISTOGRAMREPORT = "histogramReport";
   public static final String SUPPLIER_SEARCH_RESULTS = "SUPPLIER_SEARCH_RESULTS";

   public static final String WITHDRAWN_NAME = "Withdrawn";
   public static final String STATUS_GROUPNAME = "Status";
   public static final String REFERENCE_QUALITY_RATING = "Quality Rating";
   public static final String REFERENCE_QUALITY_SYSTEM = "Quality System";
   public static final String REFERENCE_ACCREDITATION = "Accreditation";
   public static final String REFERENCE_DELIVERY_RATING = "Delivery Rating";
   public static final String REFERENCE_PERFORMANCE_RATING = "C/A Performance Rating";
   public static final String REFERENCE_STATUS = "Status";
   public static final String REFERENCE_SHARED_2ND_PARTY = "Shared 2nd Party";
   public static final String REFERENCE_STATE_COUNTRTY = "Country/State";

   public static final String APPROVALS_SCOPE_TEXT_TYPE = "TEXT";
   public static final String APPROVALS_SCOPE_BOOL_TYPE = "BOOLEAN";

   //DefaultWeek-START
   /**
    * The request scope attribute that holds the defaultWeek form.
    */
   public static final String DEFAULTWEEK_KEY = "defaultWeekForm";

   /**
    * The request scope attribute that holds the defaultWeek list
    */
   public static final String DEFAULTWEEK_LIST = "defaultWeekList";

   public static final String[] weekdays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
         "Sunday" };
   //DefaultWeek-END

   //WorkHourRange-START
   /**
    * The request scope attribute that holds the workHourRange form.
    */
   public static final String WORKHOURRANGE_KEY = "workHourRangeForm";

   /**
    * The request scope attribute that holds the workHourRange list
    */
   public static final String WORKHOURRANGE_LIST = "workHourRangeList";
   //WorkHourRange-END

   //  DayModel-START
   /**
    * The request scope attribute that holds the dayModel form.
    */
   public static final String DAYMODEL_KEY = "dayModelForm";

   /**
    * The request scope attribute that holds the dayModel list
    */
   public static final String DAYMODEL_LIST = "dayModelList";
   //  DayModel-END
   public static final String REFERENCE_GROUPNAME_CALENDAR = "Calendar";

   //ServiceLevelAgreement-START
   /**
    * The request scope attribute that holds the serviceLevelAgreement form.
    */
   public static final String SERVICELEVELAGREEMENT_KEY = "serviceLevelAgreementForm";

   /**
    * The request scope attribute that holds the serviceLevelAgreement list
    */
   public static final String SERVICELEVELAGREEMENT_LIST = "serviceLevelAgreementList";
   //ServiceLevelAgreement-END

   //BusinessProcess-START
   /**
    * The request scope attribute that holds the businessProcess form.
    */
   public static final String BUSINESSPROCESS_KEY = "businessProcessForm";

   /**
    * The request scope attribute that holds the businessProcess list
    */
   public static final String BUSINESSPROCESS_LIST = "businessProcessList";
   //BusinessProcess-END
   public static final String REFERENCE_BUSINESSPROCESS_NAME = "BusinessProcessName";

   public static final String REFERENCE_BUSINESSPROCESS_TYPE = "BusinessProcessType";

   public static final String REFERENCE_BUSINESSPROCESS_TXN = "BusinessProcessTxn";
   
   public static final String REFERENCE_BUSINESSPROCESS_STEP = "BusinessProcessStep";
   public static final String EMAIL_TEMPLATE="EmailTemplate";
   public static final String FROM_EMAIL = "FromEmail";
   public static final String TO_EMAIL = "ToEmail";
   
   public static final String T_FORMULA_TYPE = "T-Formulae";
   public static final String DURATION_TYPE = "Duration";
   public static final String DEADLINE_TYPE = "Fixed Deadline";
   public static final String END_OF_BUSINESS_TYPE = "End Of Business";
   public static final String END_OF_DAY_TYPE = "End Of Day";
   public static final String SAME_TIME_TYPE = "Same Time";
   public static final String ACTUAL_TIME_TYPE = "Actual Time";
   public static final String FIXED_TIME_TYPE = "Fixed Time";
   public static final String WORKING_HOURS_TYPE = "Working Hours";
   public static final String TWENTY_FOUR_HOUR_CLOCK_TYPE = "24 Hour Clock";
   public static final String YES_TYPE = "Yes";
   public static final String NO_TYPE = "No";
   public static final String DEFAULT_BUSINESS_PROCESS_TYPE = "DEFAULT";
   public static final String DEFAULT_FILENET_YEAR = "1906";

   //  SLA Status-START
   public static final String REFERENCE_SLASTATUS_OK = "SLA-Status OK";
   public static final String REFERENCE_SLASTATUS_APPROACHING = "SLA-Status Approaching";
   public static final String REFERENCE_SLASTATUS_EXCEEDED = "SLA-Status Exceeded";
   public static final String DEFAULT_SLASTATUS_OK = "OK";
   public static final String DEFAULT_SLASTATUS_APPROACHING = "Approaching SLA";
   public static final String DEFAULT_SLASTATUS_EXCEEDED = "Exceeded SLA";
   //  SLA Status-END

   //  SLA Date Time - START
   public static final String DEFAULT_TIME_FORMAT = "HH:mm";
   public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
   //  SLA Date Time - END

}