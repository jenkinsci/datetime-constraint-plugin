package org.jenkinsci.plugins.curfew;

import hudson.Extension;

import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.GlobalVariable;

import groovy.lang.Binding;

@Extension
public class CurfewGlobalVariable extends GlobalVariable{

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
        	curfew = script.getClass().getClassLoader().loadClass("org.jenkinsci.plugins.curfew.Curfew").getConstructor(CpsScript.class).newInstance(script);
            binding.setVariable(getName(), curfew);
        }
	    return curfew;
	}
}