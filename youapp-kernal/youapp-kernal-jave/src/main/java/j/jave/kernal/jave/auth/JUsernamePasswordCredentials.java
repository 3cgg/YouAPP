package j.jave.kernal.jave.auth;

import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JLangUtils;

import java.io.Serializable;
import java.security.Principal;

public class JUsernamePasswordCredentials implements JCredentials, Serializable {

    private static final long serialVersionUID = 243343858802739403L;

    private final JBasicUserPrincipal principal;
    private final String password;

    /**
     * The constructor with the username and password combined string argument.
     *
     * @param usernamePassword the username:password formed string
     * @see #toString
     */
    public JUsernamePasswordCredentials(final String usernamePassword) {
        super();
        JAssert.notNull(usernamePassword, "Username:password string");
        final int atColon = usernamePassword.indexOf(':');
        if (atColon >= 0) {
            this.principal = new JBasicUserPrincipal(usernamePassword.substring(0, atColon));
            this.password = usernamePassword.substring(atColon + 1);
        } else {
            this.principal = new JBasicUserPrincipal(usernamePassword);
            this.password = null;
        }
    }


    /**
     * The constructor with the username and password arguments.
     *
     * @param userName the user name
     * @param password the password
     */
    public JUsernamePasswordCredentials(final String userName, final String password) {
        super();
        JAssert.notNull(userName, "Username");
        this.principal = new JBasicUserPrincipal(userName);
        this.password = password;
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserName() {
        return this.principal.getName();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return this.principal.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof JUsernamePasswordCredentials) {
            final JUsernamePasswordCredentials that = (JUsernamePasswordCredentials) o;
            if (JLangUtils.equals(this.principal, that.principal)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.principal.toString();
    }

}
