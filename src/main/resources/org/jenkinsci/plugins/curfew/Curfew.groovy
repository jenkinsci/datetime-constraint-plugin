package org.jenkinsci.plugins.curfew;

import java.io.Serializable;

public class Curfew implements Serializable {
	
	private int before = 10;
	
    private org.jenkinsci.plugins.workflow.cps.CpsScript script
	
    public Curfew (org.jenkinsci.plugins.workflow.cps.CpsScript script) {
        this.script = script
    }
	
	public void setBefore(int before) {
		this.before = before;
	}
	
	def checkPoint () {
		def date = new Date()
		int dayOfTheWeek = date[Calendar.DAY_OF_WEEK]
		def hour = date.format('HH', TimeZone.getTimeZone('Europe/Berlin'))

		if (dayOfTheWeek == Calendar.FRIDAY || hour.toInteger() >= before ) { //todo change later to after
			return true
		}
		
		return false
	}
//	
//	def check (Closure body) {
//		if (checkPoint()) {
//			body()
//		}
//	}
	
	def withTimeout (Closure timeout, Closure body) {
		if (checkPoint()) {
			timeout (time: 15, unit: 'SECONDS') {
				body ()
			}
		}
	}
	
//	def withTimeout () {
//		println "why are you getting in here? "
//	}
	
//	def withTimeout (Closure body) {
//		if (checkPoint()) {
//			app.timeout (time: 15, unit: 'SECONDS') {
//				body ()
//			}
//		}
//	}
	
	// todo check why check (this.&timeout, this.&input) doesnt work (why input from outside doesnt work)
	// todo check why check(this) doest work
	// todo check if possible to call check() without params
	
//	def check (Closure timeout, Closure input) {
//		def date = new Date()
//		int dayOfTheWeek = date[Calendar.DAY_OF_WEEK]
//		def hour = date.format('HH', TimeZone.getTimeZone('Europe/Berlin'))
//
//		if (dayOfTheWeek == Calendar.FRIDAY || hour.toInteger() >= 16 ) { // if checkPoint
//			timeout (time: 15, unit: 'SECONDS') {
//				input ('Validation is required')
//			}
//		}
//	}
}
