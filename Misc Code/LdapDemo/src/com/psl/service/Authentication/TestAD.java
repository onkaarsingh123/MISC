package com.psl.service.Authentication;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


public class TestAD {
	

	public static void main(String[] args) throws NamingException {
	
	Hashtable<String, String> env = new Hashtable<String, String>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.SECURITY_AUTHENTICATION, "simple");
	
	env.put(Context.SECURITY_PRINCIPAL, "onkaar@marines.local");
	
	env.put(Context.SECURITY_CREDENTIALS, "root@123");
	env.put(Context.PROVIDER_URL, "ldap://52.16.19.143:389/DC=marines,DC=local");
	DirContext ctx;
	try {
	    //Authenticate the logon user
	    ctx = new InitialDirContext(env);
	    //Start checking if the user is within the organization unit(s)
	    String searchBase = "ou=marineCrop,DC=marines,DC=local";
		StringBuilder searchFilter = new StringBuilder("(&");
		searchFilter.append("(objectClass=person)");
	    searchFilter.append("(userPrincipalName=onkaar@marines.local)");
	    searchFilter.append("(memberOf=CN=marineAdmin,OU=marineCrop,DC=marines,DC=local)");
	    searchFilter.append(")");
	  
		//String searchFilter = "(&(objectClass=person)(sAMAccountName=jamesS)(userPrincipalName=jamesS@marines.local))";
	    SearchControls sCtrl = new SearchControls();
	    sCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    NamingEnumeration answer = ctx.search(searchBase, searchFilter.toString(), sCtrl);
	    boolean pass = false;
		String role="";
	    System.out.println("REssult ");
	    while (answer.hasMoreElements()) {
	           SearchResult sr = (SearchResult)answer.next();
				 System.out.println("REssult "+sr);
	         String str=(String)sr.getAttributes().get("memberOf").get(0);
	         String arr[]=str.split(",");
			for(int i=0;i<arr.length;i++){
						System.out.println(arr[i]);
						if(arr[i].equals("CN=Domain Admins")){
							System.out.println("Admin User Authenticated successfully !!"+ arr[i]);	
			role="admin";
			}
			}
		 pass = true;
	    }	    
	    if (pass) {
	    	System.out.println("User Authenticated successfully!!");
	        // The user belongs to the specified OU(s), do something...
	    } else {
	        // The user doesn't belong to the specified OU(s)
	    	System.out.println("Authentication failed : " );
	    }

	} catch (Exception e) {
		System.out.println("Authentication failed : " + e);
		e.printStackTrace();
		System.exit(-1);
	}

}
}