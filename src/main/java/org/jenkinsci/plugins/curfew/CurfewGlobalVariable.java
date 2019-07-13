package org.jenkinsci.plugins.curfew;

import hudson.Extension;

import java.util.HashMap;
import java.util.Map;

import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.GlobalVariable;

import groovy.lang.Binding;

@Extension
public class CurfewGlobalVariable extends GlobalVariable{
	
	private Map<String, String> times = new HashMap<String, String>(); 

	@Override
	public String getName() {
		return "curfew";
	}

	@Override
	public Object getValue(CpsScript script) throws Exception {
		Binding binding = script.getBinding();
        Object curfew;
        if (binding.hasVariable(getName())) {
        	curfew = binding.getVariable(getName());
        } else {
        	curfew = script.getClass().getClassLoader().loadClass("org.jenkinsci.plugins.curfew.Curfew")
        			.getConstructor(CpsScript.class, Map.class).newInstance(script, times);
            binding.setVariable(getName(), curfew);
        }
        
	    return curfew;
	}
	
	public void setTime(String key, String value) {
		times.put(key, value); 
	}
	
}