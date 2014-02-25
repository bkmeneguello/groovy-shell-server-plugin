import hudson.security.SecurityRealm
import jenkins.model.Jenkins

import org.jenkinsci.plugins.groovyshellserver.PasswordBasedSecurityRealm

jenkins = Jenkins.getInstance()
securityRealm = jenkins.getSecurityRealm()
print securityRealm
if (securityRealm != SecurityRealm.NO_AUTHENTICATION) {
//	securityRealm = new PasswordBasedSecurityRealm();
//	print "username: "
//	username = System.console().readLine()
//	print "password: ";
//	password = System.console().readPassword()
//	securityRealm.authenticate(username, new String(password))
}