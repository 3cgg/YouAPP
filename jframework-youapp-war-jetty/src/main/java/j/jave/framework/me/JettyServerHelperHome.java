/**
 * 
 */
package j.jave.framework.me;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author Administrator
 *
 */
public class JettyServerHelperHome {

	
	public static void main(String[] args) throws Exception {
		
//		String webApp="D:\\java_\\so\\sources\\trunk\\jframework-me\\target\\jframework-me-1.0.war";
		
//		String webApp="D:/java_/so/sources/trunk/jframework-me/src/main/webapp";
		String webApp="D:/java_/JFramework1.1/trunk/jframework-youapp-war-jetty/target/youapp-jetty";
		String crossdomain="D:/java_/JFramework1.1/trunk/jframework-youapp-war/src/main/crossdomain";
		String serverDirectory="/logs";
		
		 // Setup Threadpool
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(500);
 
        // Server
        Server server = new Server(threadPool);
 
        // Scheduler
        server.addBean(new ScheduledExecutorScheduler());
        
        
     // HTTP Configuration
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(8443);
        http_config.setOutputBufferSize(32768);
        http_config.setRequestHeaderSize(81920000);
        http_config.setResponseHeaderSize(8192);
        http_config.setSendServerVersion(true);
        http_config.setSendDateHeader(false);
		
     // === jetty-http.xml ===
        ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(http_config));
        http.setPort(8686);
        http.setIdleTimeout(30000);
        server.addConnector(http);
        
        
     // Handler Structure
        HandlerCollection handlers = new HandlerCollection();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        handlers.setHandlers(new Handler[] { contexts, new DefaultHandler() });
        
        
        // add servlet handler 
        
        WebAppContext webAppContext=new WebAppContext(webApp, "/youapp");
        contexts.addHandler(webAppContext);
        
        WebAppContext crossdomainApp=new WebAppContext(crossdomain, "/");
        contexts.addHandler(crossdomainApp);
        
//        ServletContextHandler jspHandler = new ServletContextHandler(
//                ServletContextHandler.SESSIONS);
//        jspHandler.addServlet(JspServlet.class, "/jsp/*");
//        jspHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
//        // pur resource path , that can be include JSP . 
//        Resource resource=new PathResource(new File("D:/java_/JProject/JFramework/trunk/jframework-me/jsp"));
//        jspHandler.setBaseResource(resource);
//        contexts.addHandler(jspHandler);
        
     // === jetty-requestlog.xml ===
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename(serverDirectory + "/yyyy_mm_dd.request.log");
        requestLog.setFilenameDateFormat("yyyy_MM_dd");
        requestLog.setRetainDays(90);
        requestLog.setAppend(true);
        requestLog.setExtended(true);
        requestLog.setLogCookies(false);
        requestLog.setLogTimeZone("GMT");
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
        handlers.addHandler(requestLogHandler);
		
        server.setHandler(handlers);
        
        // Start the server
        server.start();
        server.join();
        
	}

}
