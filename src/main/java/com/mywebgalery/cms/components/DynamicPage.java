package com.mywebgalery.cms.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.util.AvailableValues;
import org.apache.tapestry5.ioc.util.UnknownValueException;
import org.apache.tapestry5.runtime.RenderCommand;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.dynamic.DynamicDelegate;
import org.apache.tapestry5.services.dynamic.DynamicTemplate;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BaseComponent;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.model.Page;

/**
 * A copy of <code>org.apache.tapestry5.corelib.components.Dynamic</code> that rewrites <code>getBlock</code>
 * to get the block overrides from the modules instead of the informal parameters.
 * @author ican
 *
 */
@SupportsInformalParameters
public class DynamicPage extends BaseComponent {

    /** The dynamic template containing the content to be rendered by the component. */
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.ASSET)
    private DynamicTemplate _template;

	@Inject private BeanBlockSource _blockSource;
    @Inject private Environment _environment;

    @Inject private ComponentResources _resources;

    private final DynamicDelegate _delegate = new DynamicDelegate() {

        public ComponentResources getComponentResources() {
            return _resources;
        }

        public Block getBlock(String name) {
        	Block result = null;
        	if(name.startsWith("module_")){
        		String moduleName = name.substring(7);
        		Session s = getTransactionManager().getSession();
        		s.beginTransaction();
        		try {
        			Page p = _environment.peek(Page.class);
					Module m = Module.getInstance().getByAppAndName(s, p.getAppId(), moduleName);
					_environment.push(Module.class, m);
					result = _blockSource.getDisplayBlock(m.getType());
					System.out.println(result);
				} catch (Exception e) {
					getLog().error(e.getMessage(), e);
				}
        	} else {
        		result = _resources.getBlockParameter(name);
        	}

        	System.out.println(result);

            if (result != null)
                return result;

            throw new UnknownValueException(String.format(
                    "Component %s does not have an informal Block parameter with id '%s'.", _resources.getCompleteId(),
                    name), null, null, new AvailableValues("Available Blocks", _resources.getInformalParameterNames()));
        }
    };

    RenderCommand beginRender() {
        // Probably some room for caching here as well. It shouldn't be necessary to re-create the outermost
        // RenderCommand every time, unless the template has changed from the previous render.
        return _template.createRenderCommand(_delegate);
    }


}
