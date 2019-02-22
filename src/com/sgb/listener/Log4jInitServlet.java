package com.sgb.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Log4jInitServlet
 */
@WebServlet("/Log4jInitServlet.init")
public class Log4jInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Log log = LogFactory.getLog(getClass());   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log4jInitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() {
        String prefix = getServletContext().getRealPath("/");
        String file = getInitParameter("log4j");

        if (file != null) {
           //System.out.println("log4j 初始化成功");
        log.info("log4j初始化成功");
        PropertyConfigurator.configure(prefix+ file);
        } else {
        log.info("log4j初始化失败");
        //System.out.println("log4j 初始化失败");
        }

        }

}
