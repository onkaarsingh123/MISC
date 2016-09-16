package com.urbanlife.constants;

public interface UrbanLifeConstants {

	public static final String EMAIL_ID_FOUND = "2001";
	public static final String EMAIL_ID_NOT_FOUND = "2002";
	public static final String ID_NOT_PRESENT = "Id is not present in the request !!";
	public static final String ID_NOT_PRESENT_CODE = "10001";
	public static final String ID_NAN = "Id is not a number !!";
	public static final String ID_NAN_CODE = "10002";
	public static final String ID_NOT_PRESENT_IN_DB = "No resourse found for the Id !!";
	public static final String ID_NOT_PRESENT_IN_DB_CODE = "10003";
	public static final String PATH_NOT_FOUND = "No such path present !!";
	public static final String PATH_NOT_FOUND_CODE = "10004";
	public static final String UNKNOWN_ERROR = "unknown error !!";
	public static final String UNKNOWN_ERROR_CODE = "10005";
	public static final String SERVER_DOWN_ERROR = "Server is down";
	public static final String SERVER_DOWN_ERROR_CODE = "10006";
	
	
	public static final String PASSWORD_ENCRYPTION_FAIL = "encryption not working";
	public static final String PASSWORD_ENCRYPTION_FAIL_CODE = "30017";

	
	public static final String CARD_AUTH_FAIlED = "!!";
	
	
	public static final String CARD_INCORRECT_NUMBER = "incorrect_number"; 
	public static final String CARD_INCORRECT_NUMBER_STRIPE = "The card number is incorrect"; 
	public static final String CARD_INCORRECT_NUMBER_CODE = "30001";
	
	public static final String CARD_INVALID_NUMBER = "invalid_number"; 
	public static final String CARD_INVALID_NUMBER_STRIPE = "The card number is not a valid credit card number"; 
	public static final String CARD_INVALID_NUMBER_CODE = "30002";
	
	public static final String CARD_INVALID_EXPIRYMONTH = "invalid_expiry_month"; 
	public static final String CARD_INVALID_EXPIRYMONTH_STRIPE = "The card's expiration month is invalid"; 
	public static final String CARD_INVALID_EXPIRYMONTH_CODE = "30003";
	
	public static final String CARD_INVALID_EXPIRYYEAR = "invalid_expiry_year"; 
	public static final String CARD_INVALID_EXPIRYYEAR_STRIPE = "The card's expiration year is invalid"; 
	public static final String CARD_INVALID_EXPIRYYEAR_CODE = "30004";
	
	public static final String CARD_INVALID_CVC = "invalid_cvc"; 
	public static final String CARD_INVALID_CVC_STRIPE = "The card's security code is invalid"; 
	public static final String CARD_INVALID_CVC_CODE = "30005";
	
	public static final String CARD_EXPIRED = "expired_card"; 
	public static final String CARD_EXPIRED_STRIPE = "The card has expired"; 
	public static final String CARD_EXPIRED_CODE = "30006";
	
	public static final String CARD_INCORRECT_CVC = "incorrect_cvc"; 
	public static final String CARD_INCORRECT_CVC_STRIPE = "The card's expiration year is invalid"; 
	public static final String CARD_INCORRECT_CVC_CODE = "30007";
	
	public static final String CARD_DECLINED = "card_declined"; 
	public static final String CARD_DECLINED_STRIPE = "The card was declined"; 
	public static final String CARD_DECLINED_CODE = "30008";
	
	public static final String CARD_AUTH_FAIlED_CODE = "30009";
	public static final String CARD_INVALID_REQUEST_CODE = "30010";
	public static final String CARD_APICONNECTION_CODE = "30011";
	public static final String CARD_API_CODE = "30012";
	
	public static final String INVALID_EMAIL = "email id is not valid";
	public static final String INVALID_EMAIL_CODE = "30013";
	
	public static final String INVALID_PASSWORD = "password is not valid";
	public static final String INVALID_PASSWORD_CODE = "30014";
	
	public static final String INVALID_CONTACTNUMBER = "contact number is not valid";
	public static final String INVALID_CONTACTNUMBER_CODE = "30015";
	
	public static final String INVALID_ZIP = "zip is not valid";
	public static final String INVALID_ZIP_CODE = "30016";
	
	public static final String CARD_PROCESSING_ERROR = "processing_error"; 
	public static final String CARD_PROCESSING_ERROR_STRIPE = "An error occurred while processing the card"; 
	public static final String CARD_PROCESSING_ERROR_CODE = "30017";
	
	public static final String CARD_MISSING = "missing"; 
	public static final String CARD_MISSING_STRIPE = "There is no card on a customer that is being charged"; 
	public static final String CARD_MISSING_CODE = "30018";
	
	public static final String CARD_INCORRECTZIP = "incorrect_zip"; 
	public static final String CARD_INCORRECTZIP_STRIPE = "The card's zip code failed validation"; 
	public static final String CARD_INCORRECTZIP_CODE = "30019";
	
	public static final String TOKEN = "token";
	
	public static final String CREDENTIAL_INCORRECT = "incorrect credentials"; 
	public static final String CREDENTIAL_INCORRECT_CODE = "30020"; 
	
	
	public static final String TOKEN_INCORRECT = "Token Not Valid"; 
	public static final String TOKEN_INCORRECT_CODE = "30021"; 
	
	public static final String MAIL_SENT_UNSECESSFULL = "mail sent unsucessful"; 
	public static final String MAIL_SENT_UNSECESSFULL_CODE = "30022"; 
	
	public static final String CARD_EXCEPTION_CODE = "30023";
	
	
	public static final String MAIL_ID_NOT_FOUND = "Mail id not found"; 
	public static final String MAIL_ID_NOT_FOUND_CODE = "30024"; 
	
    public static final String FAIL_TO_SET_PASSORD = "Fail to reset password"; 
    public static final String PASSWORD_SET_SUCESSFULLY = "Password set successfully"; 
    public static final String FAIL_TO_SET_PASSORD_CODE = "30025"; 
    
	public static final String TRIPADVISOR_ID_INCORRECT = "invalid trip advisor id"; 
	public static final String TRIPADVISOR_ID_INCORRECT_CODE = "30026"; 
	
    public static final String MAIL_SENT = "mail sent to user";
    public static final String CUSTOM_PREFIX_BO = "BO";
    public static final String EMAILID = "emailId";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";
    public static final String DATASERVICE = "dataServices";
    public static final String OLDUSERDETAILSMAP = "oldUserDetailsMap";
    public static final String PREFIX = "prefix";
    public static final String USERTOKEN = "usertoken";
    public static final String FLAG = "flag";
    public static final String REMEMBERME = "rememberme";
    public static final String NULL = "null";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
	
    public static final String SESSIONNULL = "session data is null"; 
    public static final String SESSIONNULL_CODE = "30050";
	
	
	/*
public static final int 10001 = "Account is not active";
public static final int 10002=" Trial account does not support this feature";
public static final int 10003=" Incoming call rejected due to inactive account";
public static final int 11100=" Invalid URL format";
public static final int 11200=" HTTP retrieval failure";
public static final int 11205=" HTTP connection failure";
public static final int 11206=" HTTP protocol violation";
public static final int 11210=" HTTP bad host name";
public static final int 11215=" HTTP too many redirects";
public static final int 11300=" Invalid template URL";
public static final int 	11310 =" Invalid template token";
public static final int 	11320=" Invalid template unclosed brackets";
public static final int 	11750=" TwiML response body too large";
public static final int 	11751=" MMS -> Media exceeds mobile operator size limit";
public static final int 	11770=" Empty response body";
public static final int 	12100=" Document parse failure";
public static final int 	12101=" Invalid Twilio Markup XML version";
public static final int 	12102=" The root element must be Response";
public static final int	12200=" Schema validation warning";
public static final int	12300=" Invalid Content-Type";
public static final int	12400=" Internal Failure";
public static final int	13201=" Dial=" Cannot Dial out from a Dial Call Segment";
public static final int	13210=" Dial=" Invalid method value";
public static final int	13211=" Dial=" Invalid ifMachine value";
public static final int	13212=" Dial=" Invalid timeout value";
public static final int	13213=" Dial=" Invalid hangupOnStar value";
public static final int	13214=" Dial=" Invalid callerId value";
public static final int	13215=" Dial=" Invalid nested element";
public static final int	13216=" Dial=" Invalid timeLimit value";
public static final int	13217=" Dial=" Invalid record value";
public static final int	13221=" Dial->Number=" Invalid method value";
public static final int	13222=" Dial->Number=" Invalid sendDigits value";
	13223=" Dial=" Invalid phone number format";
	13224=" Dial=" Invalid phone number";
	13225=" Dial=" Forbidden phone number";
	13226=" Dial=" Invalid country code";
	13227=" Dial=" No International Authorization";
	13230=" Dial->Conference=" Invalid muted value";
	13231=" Dial->Conference=" Invalid endConferenceOnExit value";
	13232=" Dial->Conference=" Invalid startConferenceOnEnter value";
	13233=" Dial->Conference=" Invalid waitUrl value";
	13234=" Dial->Conference=" Invalid waitMethod value";
	13235=" Dial->Conference=" Invalid beep value";
	13236=" Dial->Conference=" Invalid Conference Sid value";
	13237=" Dial->Conference=" Invalid conference name";
	13238=" Dial->Conference=" Invalid verb for waitUrl TwiML";
	13241=" Dial->SIP=" Invalid method value";
	13242=" Dial->SIP=" Invalid sendDigits value";
	13243=" Dial->SIP=" Invalid SIP URI";
	13245=" Dial->SIP=" Wrong API Version";
	13247=" Dial->SIP=" Invalid callerID, invalid characters";
	13248=" Dial->SIP=" Invalid callerID, too many characters";
	13249=" Dial->SIP=" Invalid username or password attribute";
	13252=" Dial->SIP=" Invalid header name";
	13253=" Dial->SIP=" Header is too long";
	13254=" Dial->Sip=" SIP URI DNS does not resolve or resolves to an non-public IP address";
	13255=" Dial->Sip=" Dialing .sip.twilio.com addresses is not currently allowed";
	13310=" Gather=" Invalid finishOnKey value";
	13311=" Gather=" Invalid finishOnKey value";
	13312=" Gather=" Invalid method value";
	13313=" Gather=" Invalid timeout value";
	13314=" Gather=" Invalid numDigits value";
	13320=" Gather=" Invalid nested verb";
	13321=" Gather->Say=" Invalid voice value";
	13322=" Gather->Say=" Invalid loop value";
	13325=" Gather->Play=" Invalid Content-Type";
	13410=" Play=" Invalid loop value";
	13420=" Play=" Invalid Content-Type";
	13510=" Say=" Invalid loop value";
	13511=" Say=" Invalid voice value";
	13520=" Say=" Invalid text";
	13610=" Record=" Invalid method value";
	13611=" Record=" Invalid timeout value";
	13612=" Record=" Invalid maxLength value";
	13613=" Record=" Invalid finishOnKey value";
	13614=" Record=" Invalid transcribe value";
	13615=" Record=" maxLength too high for transcription";
	13616=" Record=" playBeep must be true or false";
	13710=" Redirect=" Invalid method value";
	13910=" Pause=" Invalid length value";
	14101=" "To" Attribute is Invalid";
	14102=" Message "From" Attribute is Invalid";
	14103=" Message Invalid Body";
	14104=" SMS method attribute invalid";
	14105=" Sms Verb statusCallback attribute Invalid";
	14106=" Message Reply TwiML document retrieval limit reached";
	14107=" Message rate limit exceeded";
	14108=" Message "From" Phone Number not SMS capable";
	14109=" Message Reply message limit exceeded";
	14110=" Invalid Verb for Message Reply";
	14111=" Invalid To phone number for Trial mode";
	20001=" Unknown parameters";
	20002=" Invalid FriendlyName";
	20003=" Permission Denied";
	20004=" Method not allowed";
	20005=" Account not active";
	20006=" Access Denied";
	20007=" Page size too large";
	20008=" Cannot access this resource with Test Credentials";
	20403=" 403 Forbidden";
	20429=" 429 Too Many Requests";
	20500=" 500 Internal Server Error";
	20503=" 503 Service Unavailable";
	21100=" Accounts Resource";
	21200=" Calls Resource";
	21201=" No 'To' number specified";
	21202=" 'To' number is a premium number";
	21203=" International calling not enabled";
	21204=" Call already initiated";
	21205=" Invalid URL";
	21206=" Invalid SendDigits";
	21207=" Invalid IfMachine";
	21208=" Invalid Timeout";
	21209=" Invalid Method";
	21210=" 'From' phone number not verified";
	21211=" Invalid 'To' Phone Number";
	21212=" Invalid 'From' Phone Number";
	21213=" 'From' phone number is required";
	21214=" 'To' phone number cannot be reached";
	21215=" Account not authorized to call phone number";
	21216=" Account not allowed to call phone number";
	21217=" Phone number does not appear to be valid";
	21218=" Invalid ApplicationSid";
	21219=" 'To' phone number not verified";
	21220=" Invalid call state";
	21221=" Invalid SipAuthUsername. Must be less than 256 chars";
	21222=" Invalid SipAuthUsername. Illegal chars";
	21223=" Invalid SipAuthPassword. Must be less than 256 chars";
	21224=" Invalid SipAuthPassword. Illegal chars";
	21225=" SipAuthPassword is required when providing SipAuthUsername";
	21227=" Headers portion of sip URI must be less than 1024 chars";
	21228=" Invalid SIP Header. Illegal chars in header name";
	21229=" Invalid SIP Header. Illegal chars in header value";
	21230=" Maximum Domains Reached";
	21231=" Domain Validation Error";
	21232=" Invalid Domain";
	21233=" Domain still has subdomains";
	21234=" Maximum IP Access Control Lists reached";
	21235=" IP Access Control List Validation Error";
	21236=" IP Access Control List Dependencies Violation";
	21237=" Maximum IP Addresses Reached for List";
	21238=" Address Validation Error";
	21239=" Maximum Credential Lists Reached";
	21240=" Credential List Validation Error";
	21241=" Credential List Dependencies Violation";
	21242=" Maximum Credentials Reached for List";
	21243=" Credential Validation Error";
	21401=" Invalid Phone Number";
	21402=" Invalid URL";
	21403=" Invalid Method";
	21404=" Inbound phone numbers not available to trial accounts";
	21405=" Cannot set VoiceFallbackUrl without setting Url";
	21406=" Cannot set SmsFallbackUrl without setting SmsUrl";
	21407=" This Phone Number type does not support SMS or MMS";
	21408=" Permission to send an SMS or MMS has not been enabled for the region indicated by the 'To' number";
	21409=" VoiceCallerIdLookup cannot be set for this phone number";
	21420=" ApplicationSid is not accessible";
	21421=" PhoneNumber is invalid";
	21422=" PhoneNumber is not available for purchase";
	21450=" Phone number already verified for your account";
	21451=" Invalid area code";
	21452=" No phone numbers found in area code";
	21453=" Phone number already verified for another account";
	21454=" Invalid CallDelay";
	21455=" Invalid PlayUrl";
	21456=" Invalid CallbackUrl";
	21459=" Phone number is blacklisted for verification";
	21457=" AreaCode Parameter not Supported";
	21458=" PhoneNumber Provisioning Type Mismatch";
	21470=" Invalid AccountSid";
	21471=" Account does not exist";
	21472=" Account is not active";
	21473=" AccountSid you are transferring to is not related to the originating owner of the phone number";
	21474=" API User must be the parent account to transfer phone numbers.";
	21475=" Unable to update Status, invalid Status.";
	21476=" Unable to update Status for subaccount, parent account is suspended.";
	21477=" Unable to update Status for parent accounts";
	21478=" Unable to update Status for subaccount, subaccount has been suspended by Twilio";
	21479=" Unable to update Status for subaccount, subaccount has been closed.";
	21480=" Reached maximum number of subaccounts";
	21501=" Resource not available";
	21502=" Invalid callback url";
	21503=" Invalid transcription type";
	21504=" RecordingSid is required.";
	21601=" Phone number is not a valid SMS-capable/MMS-capable inbound phone number";
	21602=" 'Body' OR MediaURL is required to send a Message";
	21603=" 'From' phone number is required to send a Message";
	21604=" 'To' phone number is required to send a Message";
	21605=" Maximum body length is 1600 characters";
	21606=" The 'From' phone number provided is not a valid, message-capable Twilio phone number.";
	21607=" The 'From' number is not a valid, SMS-capable Twilio number";
	21608=" This number can send messages only to verified numbers";
	21609=" Invalid StatusCallback url";
	21610=" Message cannot be sent to the 'To' number because the customer has replied with STOP";
	21611=" This 'From' number has exceeded the maximum number of queued messages";
	21612=" The 'To' phone number is not currently reachable via SMS";
	21613=" PhoneNumber Requires an Address";
	21614=" 'To' number is not a valid mobile number";
	21615=" PhoneNumber Requires a Local Address";
	21616=" The 'From' number matches multiple numbers for your account";
	21617=" The concatenated message body exceeds the 1600 character limit";
	21618=" The message body cannot be sent";
	21619=" A text message body or media urls must be specified";
	21620=" Invalid media URL(s)";
	21621=" The 'From' number has not been enabled for MMS";
	21622=" MMS has not been enabled for your account";
	21623=" Number of media files exceeds allowed limit";
	21624=" PhoneNumber Requires a Foreign Address";
	21625=" Address Required for Active IncomingPhoneNumber";
	22001=" Call exceeded maximum time in queue";
	22002=" Call could not be dequeued";
	30001=" Message Delivery - Queue overflow";
	30002=" Message Delivery - Account suspended";
	30003=" Message Delivery - Unreachable destination handset";
	30004=" Message Delivery - Message blocked";
	30005=" Message Delivery - Unknown destination handset";
	30006=" Message Delivery - Landline or unreachable carrier";
	30007=" Message Delivery - Carrier violation";
	30008=" Message Delivery - Unknown error";*/
}
