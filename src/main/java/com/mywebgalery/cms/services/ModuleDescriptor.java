package com.mywebgalery.cms.services;


public class ModuleDescriptor implements Comparable<ModuleDescriptor> {

	/**
	 * The name of the <code>DisplayBlockContribution</code> this module will render.
	 * See <code>AppModule</code> how the blocks are defined
	 */
	private String _type;

	/**
	 * localization key for the module name
	 */
	private String _nameKey;

	/**
	 * The name of the block presented to the user if no value found for _nameKey
	 */
	private String _displayName;

	public ModuleDescriptor(){}

	public ModuleDescriptor(String displayName, String type){
		this(displayName, type, type+".name");
	}

	public ModuleDescriptor(String displayName, String type, String nameKey){
		_displayName = displayName;
		_type = type;
		_nameKey = nameKey;
	}

	public int compareTo(ModuleDescriptor o) {
		if(_displayName == null)
			return -1;
		return _displayName.compareTo(o._displayName);
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	public String getNameKey() {
		return _nameKey;
	}

	public void setNameKey(String nameKey) {
		_nameKey = nameKey;
	}


}
