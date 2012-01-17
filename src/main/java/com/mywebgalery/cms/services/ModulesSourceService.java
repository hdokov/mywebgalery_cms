package com.mywebgalery.cms.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModulesSourceService {

	private Map<String, ModuleDescriptor> _modules = new HashMap<String, ModuleDescriptor>();

	public ModulesSourceService() {	}

	public ModulesSourceService(Map<String, ModuleDescriptor> modules){
		_modules.putAll(modules);
	}

	public void add(String type, ModuleDescriptor module){
		_modules.put(type, module);
	}

	public ModuleDescriptor get(String type){
		return _modules.get(type);
	}

	public List<ModuleDescriptor> getAllModules(){
		List<ModuleDescriptor> list = new ArrayList<ModuleDescriptor>(_modules.values());
		Collections.sort(list);
		return list;
	}

}
