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
		if (checkPoint()) {
			def t = times.get("waitTime") != null ? times.get("waitTime").toInteger() : 15 // todo fct get int from String
			script.timeout (time: t, unit: 'SECONDS') {
				script.input ("Hey proceed plz")
			}
		}
	}
	
	def checkPoint () {
		
		if(times != null) { // throw error instead - implement this in #GlobalVariable
			
			if (checkByDay(times.get("mondayBefore"), times.get("mondayAfter"), Calendar.MONDAY)
				|| checkByDay(times.get("tuesdayBefore"), times.get("tuesdayAfter"), Calendar.TUESDAY)
				|| checkByDay(times.get("wednesdayBefore"), times.get("wednesdayAfter"), Calendar.WEDNESDAY)
				|| checkByDay(times.get("thursdayBefore"), times.get("thursdayAfter"), Calendar.THURSDAY)
				|| checkByDay(times.get("fridayBefore"), times.get("fridayAfter"), Calendar.FRIDAY)
				|| checkByDay(times.get("saturdayBefore"), times.get("saturdayAfter"), Calendar.SATURDAY)
				|| checkByDay(times.get("sundayBefore"), times.get("sundayAfter"), Calendar.SUNDAY)) {
				return true
			}
			
		}
		
		return false
	
	}
		
	def checkByDay(String minHour, String maxHour, int theDay) {
		
		if (minHour == null || maxHour == null) {  // throw error instead - implement this in #GlobalVariable
			return false
		}
		
		def date = new Date()
		int today = date[Calendar.DAY_OF_WEEK] // todo with time zone
		def hour = date.format('HH', TimeZone.getTimeZone(times.get("timeZone"))) // check null
		
		boolean tooEarly = hour.toInteger() < minHour.toInteger()
		boolean tooLate = hour.toInteger() >= maxHour.toInteger()
		
		boolean tooEarlyOrTooLate = tooEarly || tooLate
		boolean todayIsTheDay = today == theDay
		
		return ( todayIsTheDay && tooEarlyOrTooLate )
	}
	
}
