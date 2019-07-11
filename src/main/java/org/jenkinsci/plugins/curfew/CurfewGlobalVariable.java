package org.jenkinsci.plugins.curfew;

import hudson.Extension;

import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.GlobalVariable;

import groovy.lang.Binding;

@Extension
public class CurfewGlobalVariable extends GlobalVariable{
	
	private String mondayBefore = "8";
	private String mondayAfter = "16"; // todo make it here a hashmap

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
        			.getConstructor(CpsScript.class, String.class, String.class).newInstance(script, mondayBefore, mondayAfter);
            binding.setVariable(getName(), curfew);
        }
        
	    return curfew;
	}
	
	public void setMondayBefore(String mondayBefore) {
		this.mondayBefore = mondayBefore;
	}

	public void setMondayAfter(String mondayAfter) {
		this.mondayAfter = mondayAfter;
	}
	
}