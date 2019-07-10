package org.jenkinsci.plugins.curfew;

import hudson.Extension;

import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.GlobalVariable;

import groovy.lang.Binding;

@Extension
public class CurfewGlobalVariable extends GlobalVariable{
	
	private String before = "16";
	
	public void setBefore(String before) {
		this.before = before;
	}

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
        			.getConstructor(CpsScript.class, String.class).newInstance(script, before);
            binding.setVariable(getName(), curfew);
        }
        
	    return curfew;
	}
	
}