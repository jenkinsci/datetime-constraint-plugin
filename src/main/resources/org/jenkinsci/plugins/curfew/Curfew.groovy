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

		if (saturday(dayOfTheWeek, hour)) {
			return true
		}
		
		return false
	}
	
	/*
	def monday(int dayOfTheWeek, def hour) {
		return dayOfTheWeek == Calendar.MONDAY
		and (hour.toInteger() >= times.get("mondayAfter").toInteger()
			|| hour.toInteger() < times.get("mondayBefore").toInteger() )
	}

	def thursday(int dayOfTheWeek, def hour) {
		boolean x1 = false;
		boolean x2 = false;
		if(times != null) {
			String after = times.get("thursdayAfter")
			if ( after != null )
			{
				x1 = hour.toInteger() >= after.toInteger()
			}
			String before = times.get("thursdayBefore")
			if ( before != null ) {
				x2 = hour.toInteger() < before.toInteger()
			}
		}
	*/
		
	def saturday(int dayOfTheWeek, def hour) {
		boolean x1 = false;
		boolean x2 = false;
		if(times != null) {
			String after = times.get("saturdayAfter")
			if ( after != null ) {
				x1 = hour.toInteger() >= after.toInteger()
			}
			String before = times.get("saturdayBefore")
			if ( before != null ) {
				x2 = hour.toInteger() < before.toInteger()
			}
		}

		boolean x = x1 || x2
		boolean y = dayOfTheWeek == Calendar.SATURDAY
		return ( y && x )
	}
	
	def withTimeout (Closure timeout, Closure body) {
		if (checkPoint()) {
			timeout (time: 15, unit: 'SECONDS') {
				body ()
			}
		}
	}
	
}
