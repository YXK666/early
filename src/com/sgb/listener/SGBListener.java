package com.sgb.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

public class SGBListener implements ServletContextListener {
	private Timer timer = null;
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer(true);
		// arg0.getServletContext().log("定时器已启动");//log4j
		// System.out.println("定时器启动");
		log.info("定时器启动");
		// 调用自检
		timer.schedule(new SGBTask(arg0.getServletContext()), 0,6*60*60 * 1000);
		// 调用备份数据库
		timer.schedule(new DBBackUpTask(arg0.getServletContext()), 0, 6*60*60 * 1000);
		// arg0.getServletContext().log("已添加任务");
	}

}
