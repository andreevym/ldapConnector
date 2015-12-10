package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LdapConnector {

    private static final String LDAP_HOST = "ldap://localhost:10389";
    private static final String LOGIN = "uid=admin,ou=system";
    private static final String PASSWORD = "secret";
    private static final String LIST_PATH = "ou=system";

    /**
     * http://docs.oracle.com/javase/tutorial/jndi/ldap/auth_mechs.html
     * <p>
     * simple	Use weak authentication (clear-text password)
     */
    private static final String AUTHENTICATION_TYPE = "simple";

    public static void main(String[] args) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, AUTHENTICATION_TYPE);
        env.put(Context.PROVIDER_URL, LDAP_HOST);
        env.put(Context.SECURITY_PRINCIPAL, LOGIN);
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);

        LdapContext ldapContext;
        try {
            ldapContext = new InitialLdapContext(env, null);
            NamingEnumeration<NameClassPair> list = ldapContext.list(LIST_PATH);

            while (list.hasMore()) {
                NameClassPair nc = list.next();
                System.out.println(nc);
            }

            ldapContext.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
