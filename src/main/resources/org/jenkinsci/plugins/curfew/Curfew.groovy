package org.jenkinsci.plugins.curfew;

import java.io.Serializable;

public class Curfew implements Serializable {
	
	private String mondayBefore;
	private String mondayAfter;
	
    private org.jenkinsci.plugins.workflow.cps.CpsScript script
	
    public Curfew (org.jenkinsci.plugins.workflow.cps.CpsScript script, String mondayBefore, String mondayAfter) {
        this.script = script
		this.mondayBefore = mondayBefore
		this.mondayAfter = mondayAfter
    }
	
	public void setBefore(int before) {
		this.before = before;
	}
	
	def checkPoint () {
		def date = new Date()
		int dayOfTheWeek = date[Calendar.DAY_OF_WEEK]
		def hour = date.format('HH', TimeZone.getTimeZone('Europe/Berlin'))

		if (monday(dayOfTheWeek)) {
			return true
		}
		
		return false
	}
	
	def monday(int dayOfTheWeek) {
		return dayOfTheWeek == Calendar.MONDAY
		and (hour.toInteger() >= mondayAfter.toInteger()
			|| hour.toInteger() <= mondayBefore.toInteger() )
	}
	
	def withTimeout (Closure timeout, Closure body) {
		if (checkPoint()) {
			timeout (time: 15, unit: 'SECONDS') {
				body ()
			}
		}
	}
	
}
