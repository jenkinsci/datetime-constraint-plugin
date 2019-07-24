package org.jenkinsci.plugins.curfew;

import java.io.IOException;
import java.time.ZoneId;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.XmlFile;
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

		@Inject
		private transient CurfewGlobalVariable curfewVar;

		private String waitTime = "30"; // in seconds

		private String timeZone = "UTC";

		private String mondayBefore = "8";
		private String mondayAfter = "16";
		private String tuesdayBefore = "8";
		private String tuesdayAfter = "16";
		private String wednesdayBefore = "8";
		private String wednesdayAfter = "16";
		private String thursdayBefore = "8";
		private String thursdayAfter = "16";
		private String fridayBefore = "8";
		private String fridayAfter = "16";
		private String saturdayBefore = "8";
		private String saturdayAfter = "16";
		private String sundayAfter = "16";
		private String sundayBefore = "8";

		public CurfewConfigDesc() {
			load();
		}

		@PostConstruct 
		public void init() {

			curfewVar.setTime("waitTime", waitTime);
			curfewVar.setTime("timeZone", timeZone);

			curfewVar.setTime("mondayBefore", mondayBefore);
			curfewVar.setTime("mondayAfter", mondayAfter);
			curfewVar.setTime("tuesdayBefore", tuesdayBefore);
			curfewVar.setTime("tuesdayAfter", tuesdayAfter);
			curfewVar.setTime("wednesdayBefore",wednesdayBefore);
			curfewVar.setTime("wednesdayAfter", wednesdayAfter);
			curfewVar.setTime("thursdayBefore", thursdayBefore);
			curfewVar.setTime("thursdayAfter",thursdayAfter);
			curfewVar.setTime("fridayBefore", fridayBefore);
			curfewVar.setTime("fridayAfter", fridayAfter);
			curfewVar.setTime("saturdayBefore", saturdayBefore);
			curfewVar.setTime("saturdayAfter", saturdayAfter);
			curfewVar.setTime("sundayBefore", sundayBefore);
			curfewVar.setTime("sundayAfter", sundayAfter);
		}

		@Override
		public String getDisplayName() {
			return "Curfew timeframes";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {

			setWaitTime(formData.getString("waitTime"));
			setTimeZone(formData.getString("timeZone"));

			setMonday(formData);
			setTuesday(formData);
			setWednesday(formData);
			setThursday(formData);
			setFriday(formData);
			setSaturday(formData);
			setSunday(formData);

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
			curfewVar.setTime("waitTime", waitTime);
			this.waitTime = waitTime;
		}

		public void setTimeZone(String timeZone) {
			curfewVar.setTime("timeZone", timeZone);
			this.timeZone = timeZone;
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
			if (formData.has("monday")) {
				JSONObject dayField = formData.getJSONObject("monday");
				setMondayBefore(dayField.get("mondayBefore").toString());
				setMondayAfter(dayField.get("mondayAfter").toString());

			} else {
				setMondayBefore(null);
				setMondayAfter(null);
			}
		}
		
		private void setTuesday(JSONObject formData) {
			if (formData.has("tuesday")) {
				JSONObject dayField = formData.getJSONObject("tuesday");
				setTuesdayBefore(dayField.get("tuesdayBefore").toString());
				setTuesdayAfter(dayField.get("tuesdayAfter").toString());

			} else {
				setTuesdayBefore(null);
				setTuesdayAfter(null);
			}
		}
		
		private void setWednesday(JSONObject formData) {
			if (formData.has("wednesday")) {
				JSONObject dayField = formData.getJSONObject("wednesday");
				setWednesdayBefore(dayField.get("wednesdayBefore").toString());
				setWednesdayAfter(dayField.get("wednesdayAfter").toString());

			} else {
				setWednesdayBefore(null);
				setWednesdayAfter(null);
			}
		}
		
		private void setThursday(JSONObject formData) {
			if (formData.has("thursday")) {
				JSONObject dayField = formData.getJSONObject("thursday");
				setThursdayBefore(dayField.get("thursdayBefore").toString());
				setThursdayAfter(dayField.get("thursdayAfter").toString());

			} else {
				setThursdayBefore(null);
				setThursdayAfter(null);
			}
		}
		
		private void setFriday(JSONObject formData) {
			if (formData.has("friday")) {
				JSONObject dayField = formData.getJSONObject("friday");
				setFridayBefore(dayField.get("fridayBefore").toString());
				setFridayAfter(dayField.get("fridayAfter").toString());

			} else {
				setFridayBefore(null);
				setFridayAfter(null);
			}
		}
		
		private void setSaturday(JSONObject formData) {
			if (formData.has("saturday")) {
				JSONObject dayField = formData.getJSONObject("saturday");
				setSaturdayBefore(dayField.get("saturdayBefore").toString());
				setSaturdayAfter(dayField.get("saturdayAfter").toString());

			} else {
				setSaturdayBefore(null);
				setSaturdayAfter(null);
			}
		}
		
		private void setSunday(JSONObject formData) {
			if (formData.has("sunday")) {
				JSONObject dayField = formData.getJSONObject("sunday");
				setSundayBefore(dayField.get("sundayBefore").toString());
				setSundayAfter(dayField.get("sundayAfter").toString());

			} else {
				setSundayBefore(null);
				setSundayAfter(null);
			}
		}

		public void setMondayBefore(String mondayBefore) {
			curfewVar.setTime("mondayBefore", mondayBefore);
			this.mondayBefore = mondayBefore;
		}

		public void setMondayAfter(String mondayAfter) {
			curfewVar.setTime("mondayAfter", mondayAfter);
			this.mondayAfter = mondayAfter;
		}

		public void setTuesdayBefore(String tuesdayBefore) {
			curfewVar.setTime("tuesdayBefore", tuesdayBefore);
			this.tuesdayBefore = tuesdayBefore;
		}

		public void setTuesdayAfter(String tuesdayAfter) {
			curfewVar.setTime("tuesdayAfter", tuesdayAfter);
			this.tuesdayAfter = tuesdayAfter;
		}

		public void setWednesdayBefore(String wednesdayBefore) {
			curfewVar.setTime("wednesdayBefore", wednesdayBefore);
			this.wednesdayBefore = wednesdayBefore;
		}

		public void setWednesdayAfter(String wednesdayAfter) {
			curfewVar.setTime("wednesdayAfter", wednesdayAfter);
			this.wednesdayAfter = wednesdayAfter;
		}

		public void setThursdayBefore(String thursdayBefore) {
			curfewVar.setTime("thursdayBefore", thursdayBefore);
			this.thursdayBefore = thursdayBefore;
		}

		public void setThursdayAfter(String thursdayAfter) {
			curfewVar.setTime("thursdayAfter", thursdayAfter);
			this.thursdayAfter = thursdayAfter;
		}

		public void setFridayBefore(String fridayBefore) {
			curfewVar.setTime("fridayBefore", fridayBefore);
			this.fridayBefore = fridayBefore;
		}

		public void setFridayAfter(String fridayAfter) {
			curfewVar.setTime("fridayAfter", fridayAfter);
			this.fridayAfter = fridayAfter;
		}

		public void setSaturdayBefore(String saturdayBefore) {
			curfewVar.setTime("saturdayBefore", saturdayBefore);
			this.saturdayBefore = saturdayBefore;
		}

		public void setSaturdayAfter(String saturdayAfter) {
			curfewVar.setTime("saturdayAfter", saturdayAfter);
			this.saturdayAfter = saturdayAfter;
		}

		public void setSundayAfter(String sundayAfter) {
			curfewVar.setTime("sundayAfter", sundayAfter);
			this.sundayAfter = sundayAfter;
		}

		public void setSundayBefore(String sundayBefore) {
			curfewVar.setTime("sundayBefore", sundayBefore);
			this.sundayBefore = sundayBefore;
		}

	}

}
