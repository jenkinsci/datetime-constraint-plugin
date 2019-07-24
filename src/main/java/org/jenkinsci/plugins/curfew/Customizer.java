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
		private String sundayBefore;
		private String sundayAfter;

		public CurfewConfigDesc() {
			load();
		}

		@PostConstruct
		public void setUp() {

			wrapper.setTime(WAIT_TIME, waitTime);
			wrapper.setTime(TIME_ZONE, timeZone);

			wrapper.setTime(MONDAY + BEFORE, mondayBefore);
			wrapper.setTime(MONDAY + AFTER, mondayAfter);
			wrapper.setTime(TUESDAY + BEFORE, tuesdayBefore);
			wrapper.setTime(TUESDAY + AFTER, tuesdayAfter);
			wrapper.setTime(WEDNESDAY + BEFORE, wednesdayBefore);
			wrapper.setTime(WEDNESDAY + AFTER, wednesdayAfter);
			wrapper.setTime(THURSDAY + BEFORE, thursdayBefore);
			wrapper.setTime(THURSDAY + AFTER, thursdayAfter);
			wrapper.setTime(FRIDAY + BEFORE, fridayBefore);
			wrapper.setTime(FRIDAY + AFTER, fridayAfter);
			wrapper.setTime(SATURDAY + BEFORE, saturdayBefore);
			wrapper.setTime(SATURDAY + AFTER, saturdayAfter);
			wrapper.setTime(SUNDAY + BEFORE, sundayBefore);
			wrapper.setTime(SUNDAY + AFTER, sundayAfter);
		}

		@Override
		public String getDisplayName() {
			return "Curfew";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {

			setWaitTime(formData.getString(WAIT_TIME));
			this.timeZone = formData.getString(TIME_ZONE);

			this.mondayBefore = get(formData, MONDAY, BEFORE);
			this.mondayAfter = get(formData, MONDAY, AFTER);
			this.tuesdayBefore = get(formData, TUESDAY, BEFORE);
			this.tuesdayAfter = get(formData, TUESDAY, AFTER);
			this.wednesdayBefore = get(formData, WEDNESDAY, BEFORE);
			this.wednesdayAfter = get(formData, WEDNESDAY, AFTER);
			this.thursdayBefore = get(formData, THURSDAY, BEFORE);
			this.thursdayAfter = get(formData, THURSDAY, AFTER);
			this.fridayBefore = get(formData, FRIDAY, BEFORE);
			this.fridayAfter = get(formData, FRIDAY, AFTER);
			this.saturdayBefore = get(formData, SATURDAY, BEFORE);
			this.saturdayAfter = get(formData, SATURDAY, AFTER);
			this.sundayBefore = get(formData, SUNDAY, BEFORE);
			this.sundayAfter = get(formData, SUNDAY, AFTER);

			setUp();

			save();

			return false;
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

		private String get(JSONObject formData, String day, String limit) {
			if (formData.has(day)) {
				return formData.getJSONObject(day).get(day + limit).toString();
			}
			return null;
		}

		// Methods called from jelly -->> 

		public String getWaitTime() {
			return waitTime;
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
		
		// fill <f:select /> fields

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

	}

}
