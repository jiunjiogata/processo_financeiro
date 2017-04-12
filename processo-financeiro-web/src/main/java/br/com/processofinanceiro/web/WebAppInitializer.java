package br.com.processofinanceiro.web;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Java-based Servlet initializer configuration class.
 */
public class WebAppInitializer implements WebApplicationInitializer {

	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

	public void onStartup(ServletContext servletContext) throws ServletException {
		registerSpringWebMvcServlet(servletContext, registerContextLoaderListener(servletContext));
	}

	/**
	 * Create an WebApp Spring context with the servletContext as its parent.
	 * 
	 * @param servletContext
	 *            - the parent servlet context.
	 * @return an spring context derived {@link WebApplicationContext}.
	 */
	private WebApplicationContext registerContextLoaderListener(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(AppConfig.class);
		dispatcherContext.setServletContext(servletContext);
		servletContext.addListener(new ContextLoaderListener(dispatcherContext));
		return dispatcherContext;
	}

	/**
	 * Register the Spring WebMVC servlet request forwarding.
	 * 
	 * @param servletContext
	 *            - the current servlet context.
	 */
	private void registerSpringWebMvcServlet(ServletContext servletContext, WebApplicationContext rootContext) {
		javax.servlet.ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
				new DispatcherServlet(rootContext));
		dispatcher.addMapping("/services/*");
		dispatcher.setMultipartConfig(
				new MultipartConfigElement("/tmp", 1024 * 1024 * 5, 1024 * 1024 * 5 * 5, 1024 * 1024));
	}

}