package org.jenkinsci.plugins.curfew;

import java.io.Serializable;
import org.jenkinsci.plugins.workflow.support.steps.input.InputStepExecution;
import org.jenkinsci.plugins.workflow.support.steps.input.InputStep;

public class Curfew implements Serializable {

	private Map<String, String> times = new HashMap<String, String>()

	private org.jenkinsci.plugins.workflow.cps.CpsScript script

	public Curfew (org.jenkinsci.plugins.workflow.cps.CpsScript script, Map times) {
		this.script = script
		this.times = times
	}

	def call () {
		Checkpoint checkpoint = new Checkpoint()
		if (checkpoint(times)) {
			def t = times.get("waitTime").toInteger()
			script.timeout (time: t, unit: 'SECONDS') {
				script.input ("Approval is required ...")
			}
		} else {
			script.echo "No approval is required ..."
		}
	}
}
