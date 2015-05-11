/**
 * 
 */
package cl.uai.webcursos.server.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import cl.uai.webcursos.client.AgendaException;

/**
 * @author Jorge Villalon
 *
 */
public class ActiveDirectoryUAI {

	public static String autenticaUsuario(String user, String password) throws AgendaException {
		Hashtable<String,String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION,"simple");
		env.put(Context.SECURITY_PRINCIPAL,user);
		env.put(Context.SECURITY_CREDENTIALS,password);
		env.put(Context.PROVIDER_URL, "ldap://svwin001.uai.cl/");
		String output = null;
		try {
			DirContext ctx = new InitialDirContext(env);
			SearchControls searchCtls = new SearchControls();
			
			//Specify the attributes to return, null initially return all values	
			//and in subsequent calls, null returns all modified attributes
			String returnedAtts[]={"displayName"};
			searchCtls.setReturningAttributes(returnedAtts);
		
			//Specify the search scope
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
 
			//specify the LDAP search filter
//			String searchFilter = "(&(objectClass=user)(givenName=fernanda))";
			String searchFilter = "(&(objectClass=user)(userPrincipalName=" + user + "))";
 
			//Specify the Base for the search
			String searchBase = "DC=uai,DC=cl";
 
			//Search for objects using the filter
			NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
 
			//Loop through the search results
			while (answer.hasMoreElements()) {
		    		SearchResult sr = answer.next();
		    		output = sr.getAttributes().get("displayName").toString().split(":")[1];
			} 
		} catch (NamingException e) {
			e.printStackTrace();
			throw new AgendaException(e);
		}
		return output;
	}
}
