/**
 * 
 */
package j.jave.framework.me;

import j.jave.framework.components.views.JDefaultServlet;

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
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author Administrator
 *
 */
public class JettyServerHelper {

	
	public static void main(String[] args) throws Exception {
		
		String webApp="D:/java_/JFramework/trunk/jframework-me/src/main/webapp";
		String serverDirectory="D:/java_/server-directory";
		
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
        http_config.setRequestHeaderSize(8192);
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
        requestLog.setFilename(serverDirectory + "/logs/yyyy_mm_dd.request.log");
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

	private static void demo() throws Exception, InterruptedException {
		// Create a basic jetty server object that will listen on port 8080.
        // Note that if you set this to port 0 then a randomly available port
        // will be assigned that you can either look in the logs for the port,
        // or programmatically obtain it for use in test cases.
        Server server = new Server(8686);
 
        // The ServletHandler is a dead simple way to create a context handler
        // that is backed by an instance of a Servlet.
        // This handler then needs to be registered with the Server object.
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
 
        // Passing in the class for the Servlet allows jetty to instantiate an
        // instance of that Servlet and mount it on a given context path.
 
        // IMPORTANT:
        // This is a raw Servlet, not a Servlet that has been configured
        // through a web.xml @WebServlet annotation, or anything similar.
        handler.addServletWithMapping(JDefaultServlet.class, "/*");
 
        // Start things up!
        server.start();
 
        // The use of server.join() the will make the current thread join and
        // wait until the server is done executing.
        // See
        // http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
        server.join();
	}
}
