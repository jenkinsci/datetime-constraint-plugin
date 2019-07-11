package org.jenkinsci.plugins.curfew;

import hudson.Extension;

import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.GlobalVariable;

import groovy.lang.Binding;

@Extension
public class CurfewGlobalVariable extends GlobalVariable{
	
	private String before = "8";
	private String after = "16";

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
        			.getConstructor(CpsScript.class, String.class, String.class).newInstance(script, before, after);
            binding.setVariable(getName(), curfew);
        }
        
	    return curfew;
	}
	
	public void setBefore(String before) {
		this.before = before;
	}

	public void setAfter(String after) {
		this.after = after;
	}
	
}