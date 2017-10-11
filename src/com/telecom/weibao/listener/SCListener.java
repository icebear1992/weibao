package com.telecom.weibao.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telecom.weibao.dac.Dac;

@WebListener
public class SCListener implements ServletContextListener, ServletContextAttributeListener {

    public void attributeAdded(ServletContextAttributeEvent scae)  {
    	ServletContext sc = scae.getServletContext();
		if(scae.getName().equals(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)){
			ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
			Dac.setJdbcTemplate((JdbcTemplate) ac.getBean("jdbcTemplate")); 
			Dac.setTransactionTemplate((TransactionTemplate)ac.getBean("transactionTemplate"));
		}
    }
	
    public void attributeRemoved(ServletContextAttributeEvent scae)  { 
    }

    public void attributeReplaced(ServletContextAttributeEvent scae)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    }
	
    public void contextDestroyed(ServletContextEvent sce)  { 
    }
}
