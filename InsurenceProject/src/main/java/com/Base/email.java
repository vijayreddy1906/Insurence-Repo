package com.Base;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

public class email extends BaseTest{
	
	/*
	 * Sends test results to the respective stakeholders
	 * Make sure to set the parameter SendExecutionResultsInEmail to Yes in TestRunDetails.properties
	 */
	

	public static void sendEmailWithResults() throws Exception {
		//propertyfile rad
		if(prop.getProperty("SendExecutionResultsInEmail").equalsIgnoreCase("yes")) {

			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(extentReportPath);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Execution Results"); 
			attachment.setName("results.html");

			MultiPartEmail email = new MultiPartEmail();
/*			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);*/
			email.setAuthenticator(new DefaultAuthenticator(prop.getProperty("FromEmail"), prop.getProperty("EmailPassword")));
			email.setSSLOnConnect(true);
			email.setStartTLSEnabled(true);
			email.setFrom(prop.getProperty("FromEmail"));
			email.setSubject("Results");
			email.setMsg("Hi Team,\n\n Please find the attached test Automation Execution Results\n\n");
			try {
				email.addTo(getList("ToEmails"));
				email.addCc(getList("CCEmails"));
				email.addBcc(getList("BCCEmails"));
			}
			catch(Exception e) {

			}
			email.attach(attachment);
			email.send();
			System.out.println("Email sent-->");
		}
	}

	/*
	 * Used to separate email list from the TestRunDetails.properties based on comma and return them as a String array.
	 */
	public static String[] getList(String maillist) {
		String[] toList=null;
		toList=prop.getProperty(maillist).split(",");

		return toList;
	}

}


















/*
 SendExecutionResultsInEmail=No
FromEmail=mech.amuthansakthivel@gmail.com
EmailPassword=ambattur
ToEmails=amuthanvec@gmail.com,mech.amuthansakthivel@gmail.com
CCEmails=
BCCEmails=

*/
