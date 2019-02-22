package com.sgb.listener;

import com.sgb.utils.FormatDate;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimerTask;

public class SGBTask extends TimerTask implements ServletContextListener{
	
	public SGBTask(ServletContext servletContext) {
		
	}

	@Override
	public void run() {
		try {
			Date date = new Date();
			String d = FormatDate.format(date);
			if (MySQLDatabaseBackup.exportDatabaseTool("127.0.0.1", "root", "123456", "E:/360", d+".sql", "db_sgb")) {
				System.out.println(LocalDateTime.now()+"数据库成功备份！！！");
			} else {
				System.out.println(LocalDateTime.now()+"数据库备份失败！！！");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	

}
