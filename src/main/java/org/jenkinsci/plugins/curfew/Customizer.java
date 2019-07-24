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

public class Customizer extends jenkins.model.GlobalPluginConfiguration {

	@DataBoundConstructor
	public Customizer() {
		super();
	}

	@Extension
	public static final class CurfewConfigDesc extends Descriptor<GlobalConfiguration> {

		private static final String WAIT_TIME = "waitTime";
		private static final String TIME_ZONE = "timeZone";
		private static final String MONDAY = "monday";
		private static final String TUESDAY = "tuesday";
		private static final String WEDNESDAY = "wednesday";
		private static final String THURSDAY = "thursday";
		private static final String FRIDAY = "friday";
		private static final String SUNDAY = "sunday";
		private static final String SATURDAY = "saturday";
		private static final String BEFORE = "Before";
		private static final String AFTER = "After";
		
		
		@Inject
		private transient ScriptWrapper wrapper;

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

			wrapper.setTime(WAIT_TIME, waitTime);
			wrapper.setTime(TIME_ZONE, timeZone);

			wrapper.setTime(MONDAY+BEFORE, mondayBefore);
			wrapper.setTime(MONDAY+AFTER, mondayAfter);
			wrapper.setTime(TUESDAY+BEFORE, tuesdayBefore);
			wrapper.setTime(TUESDAY+AFTER, tuesdayAfter);
			wrapper.setTime(WEDNESDAY+BEFORE,wednesdayBefore);
			wrapper.setTime(WEDNESDAY+AFTER, wednesdayAfter);
			wrapper.setTime(THURSDAY+BEFORE, thursdayBefore);
			wrapper.setTime(THURSDAY+AFTER,thursdayAfter);
			wrapper.setTime(FRIDAY+BEFORE, fridayBefore);
			wrapper.setTime(FRIDAY+AFTER, fridayAfter);
			wrapper.setTime(SATURDAY+BEFORE, saturdayBefore);
			wrapper.setTime(SATURDAY+AFTER, saturdayAfter);
			wrapper.setTime(SUNDAY+BEFORE, sundayBefore);
			wrapper.setTime(SUNDAY+AFTER, sundayAfter);
		}

		@Override
		public String getDisplayName() {
			return "Curfew";
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
				this.mondayBefore = dayField.get(MONDAY+BEFORE).toString();
				this.mondayAfter = dayField.get(MONDAY+AFTER).toString();

			} else {
				this.mondayBefore = null;
				this.mondayAfter = null;
			}
		}
		
		private void setTuesday(JSONObject formData) {
			if (formData.has(TUESDAY)) {
				JSONObject dayField = formData.getJSONObject(TUESDAY);
				this.tuesdayBefore = dayField.get(TUESDAY+BEFORE).toString();
				this.tuesdayAfter = dayField.get(TUESDAY+AFTER).toString();

			} else {
				this.tuesdayBefore = null;
				this.tuesdayAfter = null;
			}
		}
		
		private void setWednesday(JSONObject formData) {
			if (formData.has(WEDNESDAY)) {
				JSONObject dayField = formData.getJSONObject(WEDNESDAY);
				this.wednesdayBefore = dayField.get(WEDNESDAY+BEFORE).toString();
				this.wednesdayAfter = dayField.get(WEDNESDAY+AFTER).toString();

			} else {
				this.wednesdayBefore = null;
				this.wednesdayAfter = null;
			}
		}
		
		private void setThursday(JSONObject formData) {
			if (formData.has(THURSDAY)) {
				JSONObject dayField = formData.getJSONObject(THURSDAY);
				this.thursdayBefore = dayField.get(THURSDAY+BEFORE).toString();
				this.thursdayAfter = dayField.get(THURSDAY+AFTER).toString();

			} else {
				this.thursdayBefore = null;
				this.thursdayAfter = null;
			}
		}
		
		private void setFriday(JSONObject formData) {
			if (formData.has(FRIDAY)) {
				JSONObject dayField = formData.getJSONObject(FRIDAY);
				this.fridayBefore = dayField.get(FRIDAY+BEFORE).toString();
				this.fridayAfter = dayField.get(FRIDAY+AFTER).toString();

			} else {
				this.fridayBefore = null;
				this.fridayAfter = null;
			}
		}
		
		private void setSaturday(JSONObject formData) {
			if (formData.has(SATURDAY)) {
				JSONObject dayField = formData.getJSONObject(SATURDAY);
				this.saturdayBefore = dayField.get(SATURDAY+BEFORE).toString();
				this.saturdayAfter = dayField.get(SATURDAY+AFTER).toString();

			} else {
				this.saturdayBefore = null;
				this.saturdayAfter = null;
			}
		}
		
		private void setSunday(JSONObject formData) {
			if (formData.has(SUNDAY)) {
				JSONObject dayField = formData.getJSONObject(SUNDAY);
				this.sundayBefore = dayField.get(SUNDAY+BEFORE).toString();
				this.sundayAfter = dayField.get(SUNDAY+AFTER).toString();

			} else {
				this.sundayBefore = null;
				this.sundayAfter = null;
			}
		}

	}

}
