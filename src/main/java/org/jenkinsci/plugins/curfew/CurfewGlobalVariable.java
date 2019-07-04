package org.jenkinsci.plugins.curfew;

import groovy.lang.Binding;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.GlobalVariable;

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
            // Note that if this were a method rather than a constructor, we would need to mark it @NonCPS lest it throw CpsCallableInvocation.
        	curfew = script.getClass().getClassLoader().loadClass("org.jenkinsci.plugins.curfew.Curfew").getConstructor(CpsScript.class).newInstance(script);
            binding.setVariable(getName(), curfew);
        }
        return curfew;
	}
}