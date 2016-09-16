
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
	env.put(Context.PROVIDER_URL, "ldap://52.16.189.130:389");
	DirContext ctx;
	try {
	    //Authenticate the logon user
	    ctx = new InitialDirContext(env);
	    //Start checking if the user is within the organization unit(s)
	    String searchBase = "OU=marineCrop,DC=marines,DC=local";
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
	   
	    while (answer.hasMoreElements()) {
	           SearchResult sr = (SearchResult)answer.next();
				 System.out.println("REssult "+sr);
	         String str=(String)sr.getAttributes().get("memberOf").get(0);
	         String arr[]=str.split(",");
			 System.out.println(arr);
			for(int i=0;i<arr.length;i++){
						System.out.println(arr[i]);
						if(arr[i].equals("CN=marineAdmin")){
							System.out.println("Admin User Authenticated successfully !!"+ arr[i]);	
			role="admin";
			}
				
			}
		 pass = true;
	    }	    
	    
	    	
	      
		if (!pass) {

	   System.out.println("The user is not an Admin...!");
		//DirContext ctx1 = new InitialDirContext(env);
		String searchBase1 = "OU=marineCrop,DC=marines,DC=local";
		StringBuilder searchFilter1 = new StringBuilder("(&");
		searchFilter1.append("(objectClass=person)");
	    searchFilter1.append("(userPrincipalName=onkaar@marines.local)");
	    searchFilter1.append("(memberOf=CN=engineer,OU=marineCrop,DC=marines,DC=local)");
	    searchFilter1.append(")");
	  
		//String searchFilter = "(&(objectClass=person)(sAMAccountName=jamesS)(userPrincipalName=jamesS@marines.local))";
	    SearchControls sCtrl1 = new SearchControls();
	    sCtrl1.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    NamingEnumeration answer1 = ctx.search(searchBase1, searchFilter1.toString(), sCtrl1);
	   
	    System.out.println("REssult engineer ");
	    while (answer1.hasMoreElements()) {
	           SearchResult sr = (SearchResult)answer1.next();
				 System.out.println("REssult "+sr);
	         String str=(String)sr.getAttributes().get("memberOf").get(0);
	         String arr[]=str.split(",");
			 System.out.println(arr);
			for(int i=0;i<arr.length;i++){
						System.out.println(arr[i]);
						if(arr[i].equals("CN=engineer")){
							System.out.println("Engineer User Authenticated successfully !!"+ arr[i]);	
			role="engineer";
			}
				
			}
		 pass = true;
			
			
			System.out.println("R O L E :  "+role);	
	    }

	} 
	}
	catch (Exception e) {
		
		System.out.println("Authentication failed : " + e);
		e.printStackTrace();
		System.exit(-1);
	}

}
}