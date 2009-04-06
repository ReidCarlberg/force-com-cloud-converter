package com.modelmetrics.common.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationStartupListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		//do nothing

	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		//headless
		
		System.setProperty("java.awt.headless", "true");
	}

}
