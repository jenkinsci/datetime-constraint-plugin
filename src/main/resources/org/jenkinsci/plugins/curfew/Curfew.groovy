package org.jenkinsci.plugins.curfew;

import java.io.Serializable;

public class Curfew implements Serializable {
	
	private Map<String, String> times = new HashMap<String, String>()
	
    private org.jenkinsci.plugins.workflow.cps.CpsScript script
	
    public Curfew (org.jenkinsci.plugins.workflow.cps.CpsScript script, Map times) {
        this.script = script
		this.times = times
    }
	
	public void setBefore(int before) {
		this.before = before;
	}
	
	def checkPoint () {
		def date = new Date()
		int dayOfTheWeek = date[Calendar.DAY_OF_WEEK]
		def hour = date.format('HH', TimeZone.getTimeZone('Europe/Berlin'))

		if (monday(dayOfTheWeek, hour)) {
			return true
		}
		
		return false
	}
	
	def monday(int dayOfTheWeek, def hour) {
		return dayOfTheWeek == Calendar.MONDAY
		and (hour.toInteger() >= times.get("mondayAfter").toInteger()
			|| hour.toInteger() <= times.get("mondayBefore").toInteger() )
	}
	
	def withTimeout (Closure timeout, Closure body) {
		if (checkPoint()) {
			timeout (time: 15, unit: 'SECONDS') {
				body ()
			}
		}
	}
	
}
