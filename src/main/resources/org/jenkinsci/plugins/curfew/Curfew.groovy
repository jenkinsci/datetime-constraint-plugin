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
			def t = times.get("waitTime").toInteger()
			script.timeout (time: t, unit: 'SECONDS') {
				script.input ("Approval is required ...")
			}
		}
	}

	private def checkPoint () {

		if (checkByDay(times.get("mondayBefore"), times.get("mondayAfter"), Calendar.MONDAY)
		|| checkByDay(times.get("tuesdayBefore"), times.get("tuesdayAfter"), Calendar.TUESDAY)
		|| checkByDay(times.get("wednesdayBefore"), times.get("wednesdayAfter"), Calendar.WEDNESDAY)
		|| checkByDay(times.get("thursdayBefore"), times.get("thursdayAfter"), Calendar.THURSDAY)
		|| checkByDay(times.get("fridayBefore"), times.get("fridayAfter"), Calendar.FRIDAY)
		|| checkByDay(times.get("saturdayBefore"), times.get("saturdayAfter"), Calendar.SATURDAY)
		|| checkByDay(times.get("sundayBefore"), times.get("sundayAfter"), Calendar.SUNDAY)) {
			return true
		}

		script.echo "No approval is required ..."

		return false
	}

	private def checkByDay(String minHour, String maxHour, int theDay) {

		if (minHour == null || maxHour == null) {
			return false
		}

		def date = new Date()
		int today = date[Calendar.DAY_OF_WEEK]
		def tz = TimeZone.getTimeZone(times.get("timeZone"))
		def hour = date.format('HH', tz).toInteger()

		boolean tooEarly = hour < minHour.toInteger()
		boolean tooLate = hour >= maxHour.toInteger()

		boolean tooEarlyOrTooLate = tooEarly || tooLate
		boolean todayIsTheDay = today == theDay

		return ( todayIsTheDay && tooEarlyOrTooLate )
	}
}
