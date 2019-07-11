package org.jenkinsci.plugins.curfew;

import java.io.Serializable;

public class Curfew implements Serializable {
	
	private String before;
	private String after;
	
    private org.jenkinsci.plugins.workflow.cps.CpsScript script
	
    public Curfew (org.jenkinsci.plugins.workflow.cps.CpsScript script, String before, String after) {
        this.script = script
		this.before = before
		this.after = after
    }
	
	public void setBefore(int before) {
		this.before = before;
	}
	
	def checkPoint () {
		def date = new Date()
		int dayOfTheWeek = date[Calendar.DAY_OF_WEEK]
		def hour = date.format('HH', TimeZone.getTimeZone('Europe/Berlin'))

		if (dayOfTheWeek == Calendar.FRIDAY 
			|| hour.toInteger() >= after.toInteger()
			|| hour.toInteger() <= before.toInteger() ) {
			return true
		}
		
		return false
	}
	
	def withTimeout (Closure timeout, Closure body) {
		if (checkPoint()) {
			timeout (time: 15, unit: 'SECONDS') {
				body ()
			}
		}
	}
	
}
