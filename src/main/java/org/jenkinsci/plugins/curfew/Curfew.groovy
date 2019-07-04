package org.jenkinsci.plugins.curfew;

import java.io.Serializable;

public class Curfew implements Serializable {
    private org.jenkinsci.plugins.workflow.cps.CpsScript script

    public Curfew (org.jenkinsci.plugins.workflow.cps.CpsScript script) {
        this.script = script
    }
	
	// todo check why check(this) doest work
	// todo check if possible to call check() without params
	def check (Object o) {
		check(o.&timeout, o.&input)
	}
	
	def check (Closure timeout, Closure input) {
		def date = new Date()
		int dayOfTheWeek = date[Calendar.DAY_OF_WEEK]
		def hour = date.format('HH', TimeZone.getTimeZone('Europe/Berlin'))

		if (dayOfTheWeek == Calendar.FRIDAY || hour.toInteger() >= 16 ) {
			timeout (time: 15, unit: 'SECONDS') {
				input ('Validation is required')
			}
		}
	}
}
