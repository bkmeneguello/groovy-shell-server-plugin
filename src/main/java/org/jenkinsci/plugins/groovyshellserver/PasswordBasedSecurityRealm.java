package org.jenkinsci.plugins.groovyshellserver;

import static java.util.Collections.emptyMap;
import hudson.model.User;
import hudson.security.AbstractPasswordBasedSecurityRealm;
import hudson.security.GroupDetails;
import hudson.security.HudsonPrivateSecurityRealm.Details;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

public class PasswordBasedSecurityRealm extends AbstractPasswordBasedSecurityRealm {

	@Override
	public UserDetails authenticate(String username, String password) throws AuthenticationException {
		Details u = loadUserByUsername(username);
        if (!u.isPasswordCorrect(password)) {
            String message;
            try {
                message = ResourceBundle.getBundle("org.acegisecurity.messages").getString("AbstractUserDetailsAuthenticationProvider.badCredentials");
            } catch (MissingResourceException x) {
                message = "Bad credentials";
            }
            throw new BadCredentialsException(message);
        }
        return u;
	}

	@Override
    public Details loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User u = User.get(username, false, emptyMap());
        Details p = (u != null ? u.getProperty(Details.class) : null);
        if (p == null) {
            throw new UsernameNotFoundException("Password is not set: " + username);
        }
        return p;
    }

	@Override
    public GroupDetails loadGroupByGroupname(String groupname) throws UsernameNotFoundException, DataAccessException {
        throw new UsernameNotFoundException(groupname);
    }

}
