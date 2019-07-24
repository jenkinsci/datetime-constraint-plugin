package org.jenkinsci.plugins.curfew;

import java.time.ZoneId;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;

public class CurfewConfig extends jenkins.model.GlobalPluginConfiguration {

	@DataBoundConstructor
	public CurfewConfig() {
		super();
	}

	@Extension
	public static final class CurfewConfigDesc extends Descriptor<GlobalConfiguration> {

		private static final String WAIT_TIME = "waitTime";
		private static final String TIME_ZONE = "timeZone";
		private static final String MONDAY = "monday";
		private static final String MONDAY_BEFORE = "mondayBefore";
		private static final String MONDAY_AFTER = "mondayAfter";
		private static final String TUESDAY = "tuesday";
		private static final String TUESDAY_BEFORE = "tuesdayBefore";
		private static final String TUESDAY_AFTER = "tuesdayAfter";
		private static final String WEDNESDAY = "wednesday";
		private static final String WEDNESDAY_BEFORE = "wednesdayBefore";
		private static final String WEDNESDAY_AFTER = "wednesdayAfter";
		private static final String THURSDAY = "thursday";
		private static final String THURSDAY_BEFORE = "thursdayBefore";
		private static final String THURSDAY_AFTER = "thursdayAfter";
		private static final String FRIDAY = "friday";
		private static final String FRIDAY_BEFORE = "fridayBefore";
		private static final String FRIDAY_AFTER = "fridayAfter";
		private static final String SUNDAY = "sunday";
		private static final String SUNDAY_BEFORE = "sundayBefore";
		private static final String SUNDAY_AFTER = "sundayAfter";
		private static final String SATURDAY = "saturday";
		private static final String SATURDAY_BEFORE = "saturdayBefore";
		private static final String SATURDAY_AFTER = "saturdayAfter";

		@Inject
		private transient CurfewGlobalVariable curfewVar;

		private String waitTime = "30"; // in seconds
		private String timeZone = "UTC";
		private String mondayBefore;
		private String mondayAfter;
		private String tuesdayBefore;
		private String tuesdayAfter;
		private String wednesdayBefore;
		private String wednesdayAfter;
		private String thursdayBefore;
		private String thursdayAfter;
		private String fridayBefore;
		private String fridayAfter;
		private String saturdayBefore;
		private String saturdayAfter;
		private String sundayAfter;
		private String sundayBefore;

		public CurfewConfigDesc() {
			load();
		}

		@PostConstruct 
		public void init() {

			curfewVar.setTime(WAIT_TIME, waitTime);
			curfewVar.setTime(TIME_ZONE, timeZone);

			curfewVar.setTime(MONDAY_BEFORE, mondayBefore);
			curfewVar.setTime(MONDAY_AFTER, mondayAfter);
			curfewVar.setTime(TUESDAY_BEFORE, tuesdayBefore);
			curfewVar.setTime(TUESDAY_AFTER, tuesdayAfter);
			curfewVar.setTime(WEDNESDAY_BEFORE,wednesdayBefore);
			curfewVar.setTime(WEDNESDAY_AFTER, wednesdayAfter);
			curfewVar.setTime(THURSDAY_BEFORE, thursdayBefore);
			curfewVar.setTime(THURSDAY_AFTER,thursdayAfter);
			curfewVar.setTime(FRIDAY_BEFORE, fridayBefore);
			curfewVar.setTime(FRIDAY_AFTER, fridayAfter);
			curfewVar.setTime(SATURDAY_BEFORE, saturdayBefore);
			curfewVar.setTime(SATURDAY_AFTER, saturdayAfter);
			curfewVar.setTime(SUNDAY_BEFORE, sundayBefore);
			curfewVar.setTime(SUNDAY_AFTER, sundayAfter);
		}

		@Override
		public String getDisplayName() {
			return "Curfew timeframes";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {

			setWaitTime(formData.getString(WAIT_TIME));
			this.timeZone = formData.getString(TIME_ZONE);
			setMonday(formData);
			setTuesday(formData);
			setWednesday(formData);
			setThursday(formData);
			setFriday(formData);
			setSaturday(formData);
			setSunday(formData);
			
			init();

			save();

			return false;
		}

		public ListBoxModel doFillTimeZoneItems() {
			ListBoxModel list = new ListBoxModel();
			ZoneId.getAvailableZoneIds().stream().sorted().forEach(z -> {
				list.add(new Option(z, z, timeZone.equals(z)));
			});
			return list;
		}

		public ListBoxModel doFillMondayBeforeItems() {
			return listBoxModel(mondayBefore);
		}

		public ListBoxModel doFillMondayAfterItems() {
			return listBoxModel(mondayAfter);
		}

		public ListBoxModel doFillTuesdayBeforeItems() {
			return listBoxModel(tuesdayBefore);
		}

		public ListBoxModel doFillTuesdayAfterItems() {
			return listBoxModel(tuesdayAfter);
		}

		public ListBoxModel doFillWednesdayBeforeItems() {
			return listBoxModel(wednesdayBefore);
		}

		public ListBoxModel doFillWednesdayAfterItems() {
			return listBoxModel(wednesdayAfter);
		}

		public ListBoxModel doFillThursdayBeforeItems() {
			return listBoxModel(thursdayBefore);
		}

		public ListBoxModel doFillThursdayAfterItems() {
			return listBoxModel(thursdayAfter);
		}

		public ListBoxModel doFillFridayBeforeItems() {
			return listBoxModel(fridayBefore);
		}

		public ListBoxModel doFillFridayAfterItems() {
			return listBoxModel(fridayAfter);
		}

		public ListBoxModel doFillSaturdayBeforeItems() {
			return listBoxModel(saturdayBefore);
		}

		public ListBoxModel doFillSaturdayAfterItems() {
			return listBoxModel(saturdayAfter);
		}

		public ListBoxModel doFillSundayBeforeItems() {
			return listBoxModel(sundayBefore);
		}

		public ListBoxModel doFillSundayAfterItems() {
			return listBoxModel(sundayAfter);
		}

		private ListBoxModel listBoxModel(String field) {
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i <= 24; i++) {
				list.add(option(i, field));
			}
			return list;
		}

		private Option option(int i, String field) {
			String name = i + ":00";
			if (i < 10) {
				name = "0" + name;
			}
			return new Option(name, i + "", ("" + i).equals(field)); 
		}

		public String getWaitTime() {
			return waitTime;
		}

		public void setWaitTime(String waitTime) {
			try {
				int t = Integer.parseInt(waitTime);
				if (t < 15) {
					t = 15;
				}
				waitTime = t + "";
			} catch (NumberFormatException e) {
				waitTime = "15";
			}
			this.waitTime = waitTime;
		}

		public boolean monday() {
			return mondayBefore != null && mondayAfter != null;
		}

		public boolean tuesday() {
			return tuesdayBefore != null && tuesdayAfter != null;
		}

		public boolean wednesday() {
			return wednesdayBefore != null && wednesdayAfter != null;
		}

		public boolean thursday() {
			return thursdayBefore != null && thursdayAfter != null;
		}

		public boolean friday() {
			return fridayBefore != null && fridayAfter != null;
		}

		public boolean saturday() {
			return saturdayBefore != null && saturdayAfter != null;
		}

		public boolean sunday() {
			return sundayBefore != null && sundayAfter != null;
		}
		
		private void setMonday(JSONObject formData) {
			if (formData.has(MONDAY)) {
				JSONObject dayField = formData.getJSONObject(MONDAY);
				this.mondayBefore = dayField.get(MONDAY_BEFORE).toString();
				this.mondayAfter = dayField.get(MONDAY_AFTER).toString();

			} else {
				this.mondayBefore = null;
				this.mondayAfter = null;
			}
		}
		
		private void setTuesday(JSONObject formData) {
			if (formData.has(TUESDAY)) {
				JSONObject dayField = formData.getJSONObject(TUESDAY);
				this.tuesdayBefore = dayField.get(TUESDAY_BEFORE).toString();
				this.tuesdayAfter = dayField.get(TUESDAY_AFTER).toString();

			} else {
				this.tuesdayBefore = null;
				this.tuesdayAfter = null;
			}
		}
		
		private void setWednesday(JSONObject formData) {
			if (formData.has(WEDNESDAY)) {
				JSONObject dayField = formData.getJSONObject(WEDNESDAY);
				this.wednesdayBefore = dayField.get(WEDNESDAY_BEFORE).toString();
				this.wednesdayAfter = dayField.get(WEDNESDAY_AFTER).toString();

			} else {
				this.wednesdayBefore = null;
				this.wednesdayAfter = null;
			}
		}
		
		private void setThursday(JSONObject formData) {
			if (formData.has(THURSDAY)) {
				JSONObject dayField = formData.getJSONObject(THURSDAY);
				this.thursdayBefore = dayField.get(THURSDAY_BEFORE).toString();
				this.thursdayAfter = dayField.get(THURSDAY_AFTER).toString();

			} else {
				this.thursdayBefore = null;
				this.thursdayAfter = null;
			}
		}
		
		private void setFriday(JSONObject formData) {
			if (formData.has(FRIDAY)) {
				JSONObject dayField = formData.getJSONObject(FRIDAY);
				this.fridayBefore = dayField.get(FRIDAY_BEFORE).toString();
				this.fridayAfter = dayField.get(FRIDAY_AFTER).toString();

			} else {
				this.fridayBefore = null;
				this.fridayAfter = null;
			}
		}
		
		private void setSaturday(JSONObject formData) {
			if (formData.has(SATURDAY)) {
				JSONObject dayField = formData.getJSONObject(SATURDAY);
				this.saturdayBefore = dayField.get(SATURDAY_BEFORE).toString();
				this.saturdayAfter = dayField.get(SATURDAY_AFTER).toString();

			} else {
				this.saturdayBefore = null;
				this.saturdayAfter = null;
			}
		}
		
		private void setSunday(JSONObject formData) {
			if (formData.has(SUNDAY)) {
				JSONObject dayField = formData.getJSONObject(SUNDAY);
				this.sundayBefore = dayField.get(SUNDAY_BEFORE).toString();
				this.sundayAfter = dayField.get(SUNDAY_AFTER).toString();

			} else {
				this.sundayBefore = null;
				this.sundayAfter = null;
			}
		}

	}

}
