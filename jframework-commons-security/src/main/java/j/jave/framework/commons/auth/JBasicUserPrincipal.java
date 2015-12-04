package j.jave.framework.commons.auth;

import j.jave.framework.commons.utils.JAssert;
import j.jave.framework.commons.utils.JLangUtils;

import java.io.Serializable;
import java.security.Principal;

public class JBasicUserPrincipal implements Principal, Serializable {

	private final String username;

    public JBasicUserPrincipal(final String username) {
        super();
        JAssert.notNull(username, "User name");
        this.username = username;
    }

    public String getName() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof JBasicUserPrincipal) {
            final JBasicUserPrincipal that = (JBasicUserPrincipal) o;
            if (JLangUtils.equals(this.username, that.username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("[principal: ");
        buffer.append(this.username);
        buffer.append("]");
        return buffer.toString();
    }

}