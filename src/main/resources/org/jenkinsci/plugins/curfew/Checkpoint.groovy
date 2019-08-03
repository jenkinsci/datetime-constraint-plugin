package org.jenkinsci.plugins.curfew;

public class Checkpoint implements Serializable {

	def call (Map<String, String> times) {
		Calendar calendar =  Calendar.getInstance();
		def dayOfWeek  = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toLowerCase();

		def date = new Date()
		def tz = TimeZone.getTimeZone(times.get("timeZone"))
		def hour = date.format('HH', tz).toInteger()

		return checkByDay(times.get(dayOfWeek+"Before"), times.get(dayOfWeek+"After"), hour)
	}

	private def checkByDay(String minHour, String maxHour, int hour) {

		if (minHour == null || maxHour == null) {
			return false
		}

		boolean tooEarly = hour < minHour.toInteger()
		boolean tooLate = hour >= maxHour.toInteger()

		return tooEarly || tooLate
	}
}