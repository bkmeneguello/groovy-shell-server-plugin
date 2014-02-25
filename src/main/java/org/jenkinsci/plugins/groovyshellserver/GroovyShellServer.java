package org.jenkinsci.plugins.groovyshellserver;

import hudson.Plugin;
import hudson.model.Descriptor.FormException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import com.iterative.groovy.service.GroovyShellService;

public class GroovyShellServer extends Plugin {
	
	private static final Logger LOGGER = Logger.getLogger(GroovyShellServer.class.getName());

	private transient GroovyShellService service;
	
	private int port = 6789;
	
	public void setPort(int port) throws IOException {
		this.port = port;
		save();
	}
	
	public int getPort() {
		return port;
	}

	@Override
	public void start() throws Exception {
		try {
			load();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to load", e);
		}
		startService();
	}

	private void startService() throws IOException {
		service = new GroovyShellService(port);
		service.setDefaultScriptNames(getClass().getResource("/authenticate.groovy").toExternalForm());
		service.start();
	}
	
	@Override
	public void stop() throws Exception {
		stopService();
	}

	private void stopService() throws InterruptedException {
		service.destroy();
	}
	
	@Override
	public void configure(StaplerRequest req, JSONObject formData) throws IOException, ServletException, FormException {
		super.configure(req, formData);
		setPort(formData.getInt("port"));
	}
	
}
