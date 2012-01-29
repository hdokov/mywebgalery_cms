package com.mywebgalery.cms.services;

import java.io.IOException;
import java.util.Map;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.services.ApplicationStateContribution;
import org.apache.tapestry5.services.ApplicationStateCreator;
import org.apache.tapestry5.services.BeanBlockContribution;
import org.apache.tapestry5.services.DisplayBlockContribution;
import org.apache.tapestry5.services.EditBlockContribution;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */
public class AppModule {
	public static void bind(ServiceBinder binder) {
		// binder.bind(MyServiceInterface.class, MyServiceImpl.class);

		// Make bind() calls on the binder object to define most IoC services.
		// Use service builder methods (example below) when the implementation
		// is provided inline, or requires more initialization than simply
		// invoking the constructor.
	}

	public static void contributeApplicationDefaults(
			MappedConfiguration<String, String> configuration) {
		// Contributions to ApplicationDefaults will override any contributions
		// to
		// FactoryDefaults (with the same key). Here we're restricting the
		// supported
		// locales to just "en" (English). As you add localised message catalogs
		// and other assets,
		// you can extend this list of locales (it's a comma separated series of
		// locale names;
		// the first locale name is the default when there's no reasonable
		// match).

		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "bg,en");

		// The factory default is true but during the early stages of an
		// application
		// overriding to false is a good idea. In addition, this is often
		// overridden
		// on the command line as -Dtapestry.production-mode=false
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");

		// The application version number is incorprated into URLs for some
		// assets. Web browsers will cache assets because of the far future
		// expires
		// header. If existing assets are changed, the version number should
		// also
		// change, to force the browser to download new versions.
		configuration.add(SymbolConstants.APPLICATION_VERSION, "0.0.1-SNAPSHOT");
	}

	/**
	 * This is a service definition, the service will be named "TimingFilter".
	 * The interface, RequestFilter, is used within the RequestHandler service
	 * pipeline, which is built from the RequestHandler service configuration.
	 * Tapestry IoC is responsible for passing in an appropriate Logger
	 * instance. Requests for static resources are handled at a higher level, so
	 * this filter will only be invoked for Tapestry related requests.
	 *
	 * <p>
	 * Service builder methods are useful when the implementation is inline as
	 * an inner class (as here) or require some other kind of special
	 * initialization. In most cases, use the static bind() method instead.
	 *
	 * <p>
	 * If this method was named "build", then the service id would be taken from
	 * the service interface and would be "RequestFilter". Since Tapestry
	 * already defines a service named "RequestFilter" we use an explicit
	 * service id that we can reference inside the contribution method.
	 */
	public RequestFilter buildTimingFilter(final Logger log) {
		return new RequestFilter() {
			public boolean service(Request request, Response response,
					RequestHandler handler) throws IOException {
				long startTime = System.currentTimeMillis();

				try {
					// The responsibility of a filter is to invoke the
					// corresponding method
					// in the handler. When you chain multiple filters together,
					// each filter
					// received a handler that is a bridge to the next filter.

					return handler.service(request, response);
				} finally {
					long elapsed = System.currentTimeMillis() - startTime;
					if (!request.getPath().startsWith("/assets")
							&& !"/favicon.ico".equals(request.getPath()))
						log.info(String.format("Request time (%s): %d ms", request.getPath(), elapsed));
				}
			}
		};
	}

	/**
	 * This is a contribution to the RequestHandler service configuration. This
	 * is how we extend Tapestry using the timing filter. A common use for this
	 * kind of filter is transaction management or security. The @Local
	 * annotation selects the desired service by type, but only from the same
	 * module. Without @Local, there would be an error due to the other
	 * service(s) that implement RequestFilter (defined in other modules).
	 */
	public void contributeRequestHandler(
			OrderedConfiguration<RequestFilter> configuration,
			@Local RequestFilter filter) {
		// Each contribution to an ordered configuration has a name, When
		// necessary, you may
		// set constraints to precisely control the invocation order of the
		// contributed filter
		// within the pipeline.

		configuration.add("Timing", filter);
	}

	public void contributeApplicationStateManager(
			MappedConfiguration<Class<?>, ApplicationStateContribution> configuration) {
		ApplicationStateCreator<UserSessionData> creator = new ApplicationStateCreator<UserSessionData>() {
			public UserSessionData create() {
				return new UserSessionData();
			}
		};
		configuration.add(UserSessionData.class, new ApplicationStateContribution("session", creator));
		ApplicationStateCreator<AppMessages> creator1 = new ApplicationStateCreator<AppMessages>() {
			public AppMessages create() {
				return new AppMessages();
			}
		};
		configuration.add(AppMessages.class, new ApplicationStateContribution("session", creator1));
	}

	// disable default field decorator
	public static void contributeMarkupRenderer(
			OrderedConfiguration<MarkupRendererFilter> configuration,
			final Environment environment) {

		MarkupRendererFilter validationDecorator = new MarkupRendererFilter() {

			public void renderMarkup(MarkupWriter writer,
					MarkupRenderer renderer) {
				ValidationDecorator decorator = new ValidationDecorator() {

					public void insideLabel(Field field, Element labelElement) {
					}

					public void insideField(Field field) {
					}

					public void beforeLabel(Field field) {
					}

					public void beforeField(Field field) {
					}

					public void afterLabel(Field field) {
					}

					public void afterField(Field field) {
					}
				};

				environment.push(ValidationDecorator.class, decorator);
				renderer.renderMarkup(writer);
				environment.pop(ValidationDecorator.class);

			}
		};

		configuration.override("DefaultValidationDecorator", validationDecorator);

	}

	public static void contributeIgnoredPathsFilter(Configuration<String> configuration) {
		configuration.add("/img/.*");
		configuration.add("/css/.*");
		configuration.add("/templates/.*");
	}


	// MODULES
	public static ModulesSourceService buildFileServicerDispatcher(Map<String, ModuleDescriptor> contributions) {
		return new ModulesSourceService(contributions);
	}

	/**
	 * Add localization files for the modules
	 */
    public void contributeComponentMessagesSource(OrderedConfiguration<Resource> configuration,@Value("conf/modules.properties")Resource catalog) {
    	configuration.add("BaseComponent",catalog);
    }

    /**
     * Include the modules in the list of available modules
     * @param configuration
     */
	public static void contributeFileServicerDispatcher(MappedConfiguration<String, ModuleDescriptor> configuration) {
		configuration.add("module.login", new ModuleDescriptor("Log in/Log out", "module.login"));
		configuration.add("module.html", new ModuleDescriptor("Log in/Log out", "module.html"));
		configuration.add("module.menu", new ModuleDescriptor("Log in/Log out", "module.menu"));
	}

	/**
	 * Add the display blocks for the modules
	 * @param configuration
	 */
	public void contributeBeanBlockSource(Configuration<BeanBlockContribution> configuration) {
		configuration.add(new DisplayBlockContribution("module.login", "modules/ModuleLogin", "view"));
		configuration.add(new EditBlockContribution("module.login", "modules/ModuleLogin", "edit"));
		configuration.add(new DisplayBlockContribution("module.html", "modules/ModuleHtml", "view"));
		configuration.add(new EditBlockContribution("module.html", "modules/ModuleHtml", "edit"));
		configuration.add(new DisplayBlockContribution("module.menu", "modules/ModuleMenu", "view"));
		configuration.add(new EditBlockContribution("module.menu", "modules/ModuleMenu", "edit"));
	}

}
