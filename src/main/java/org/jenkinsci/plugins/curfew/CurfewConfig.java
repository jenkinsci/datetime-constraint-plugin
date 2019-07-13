package org.jenkinsci.plugins.curfew;

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
	public CurfewConfig () {
		super();
	}

	public String getMondayBefore(){    // todo rm because not called in Jelly	
		CurfewConfigDesc descriptor = (CurfewConfigDesc) super.getDescriptor();    	
		return descriptor.getMondayBefore();
	}
	
	@Extension
    public static final class CurfewConfigDesc extends Descriptor<GlobalConfiguration> {
		
		@Inject
		private transient CurfewGlobalVariable curfewVar;
		
		// todo time_zone, timeout
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
		
        @Override
        public String getDisplayName() {
            return "Curfew timeframes";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
        	setMondayBefore(formData.getString("mondayBefore"));
        	setMondayAfter(formData.getString("mondayAfter"));
        	setTuesdayBefore(formData.getString("tuesdayBefore"));
        	setTuesdayAfter(formData.getString("tuesdayAfter"));
        	setWednesdayBefore(formData.getString("wednesdayBefore"));
        	setWednesdayAfter(formData.getString("wednesdayAfter"));
        	setThursdayBefore(formData.getString("thursdayBefore"));
        	setThursdayAfter(formData.getString("thursdayAfter"));
        	setFridayBefore(formData.getString("fridayBefore"));
        	setFridayAfter(formData.getString("fridayAfter"));
        	setSaturdayBefore(formData.getString("saturdayBefore"));
        	setSaturdayAfter(formData.getString("saturdayAfter"));
        	setSundayBefore(formData.getString("sundayBefore"));
        	setSundayAfter(formData.getString("sundayAfter"));
        	save();
            return false;
        }
         	
    	public ListBoxModel doFillMondayBeforeItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, mondayBefore));
			}
			return list;
    	}
    	
    	public ListBoxModel doFillMondayAfterItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, mondayAfter));
			}
			return list;
    	}

		public ListBoxModel doFillTuesdayBeforeItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, tuesdayBefore));
			}
			return list;
		}
		
		public ListBoxModel doFillTuesdayAfterItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, tuesdayAfter));
			}
			return list;
		}
		
		public ListBoxModel doFillWednesdayBeforeItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, wednesdayBefore));
			}
			return list;
		}
		
		public ListBoxModel doFillWednesdayAfterItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, wednesdayAfter));
			}
			return list;
		}
		
		public ListBoxModel doFillThursdayBeforeItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, thursdayBefore));
			}
			return list;
		}
		
		public ListBoxModel doFillThursdayAfterItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, thursdayAfter));
			}
			return list;
		}
		
		public ListBoxModel doFillFridayBeforeItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, fridayBefore));
			}
			return list;
		}
		
		public ListBoxModel doFillFridayAfterItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, fridayAfter));
			}
			return list;
		}         	
		
		public ListBoxModel doFillSaturdayBeforeItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, saturdayBefore));
			}
			return list;
		}
		
		public ListBoxModel doFillSaturdayAfterItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, saturdayAfter));
			}
			return list;
		}
		
     	
		public ListBoxModel doFillSundayBeforeItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, sundayBefore));
			}
			return list;
		}
		
		public ListBoxModel doFillSundayAfterItems(){
			ListBoxModel list = new ListBoxModel();
			for (int i = 0; i<= 24; i++) {
				list.add(option(i, sundayAfter));
			}
			return list;
		}
    	
		private Option option(int i, String field) {
			String name = i+":00";
			if (i<10) {
				name = "0"+name;
			}
			return new Option(name, i+"", field.equals(i+""));
		}

		public String getMondayBefore() {
			return mondayBefore;
		}

		public void setMondayBefore(String mondayBefore) {
			curfewVar.setTime("mondayBefore", mondayBefore);
			this.mondayBefore = mondayBefore;
		}

		public String getMondayAfter() {
			return mondayAfter;
		}

		public void setMondayAfter(String mondayAfter) {
			curfewVar.setTime("mondayAfter", mondayAfter);
			this.mondayAfter = mondayAfter;
		}

		public String getTuesdayBefore() {
			return tuesdayBefore;
		}

		public void setTuesdayBefore(String tuesdayBefore) {
			curfewVar.setTime("tuesdayBefore", tuesdayBefore);
			this.tuesdayBefore = tuesdayBefore;
		}

		public String getTuesdayAfter() {
			return tuesdayAfter;
		}

		public void setTuesdayAfter(String tuesdayAfter) {
			curfewVar.setTime("tuesdayAfter", tuesdayAfter);
			this.tuesdayAfter = tuesdayAfter;
		}		

		public String getWednesdayBefore() {
			return wednesdayBefore;
		}

		public void setWednesdayBefore(String wednesdayBefore) {
			curfewVar.setTime("wednesdayBefore", wednesdayBefore);
			this.wednesdayBefore = wednesdayBefore;
		}

		public String getWednesdayAfter() {
			return wednesdayAfter;
		}

		public void setWednesdayAfter(String wednesdayAfter) {
			curfewVar.setTime("wednesdayAfter", wednesdayAfter);
			this.wednesdayAfter = wednesdayAfter;
		}		
		
		public String getThursdayBefore() {
			return thursdayBefore;
		}

		public void setThursdayBefore(String thursdayBefore) {
			curfewVar.setTime("thursdayBefore", thursdayBefore);
			this.thursdayBefore = thursdayBefore;
		}
		
		public String getThursdayAfter() {
			return thursdayAfter;
		}

		public void setThursdayAfter(String thursdayAfter) {
			curfewVar.setTime("thursdayAfter", thursdayAfter);
			this.thursdayAfter = thursdayAfter;
		}
		
		public String getFridayBefore() {
			return fridayBefore;
		}

		public void setFridayBefore(String fridayBefore) {
			curfewVar.setTime("fridayBefore", fridayBefore);
			this.fridayBefore = fridayBefore;
		}

		public String getFridayAfter() {
			return fridayAfter;
		}

		public void setFridayAfter(String fridayAfter) {
			curfewVar.setTime("fridayAfter", fridayAfter);
			this.fridayAfter = fridayAfter;
		}		
		
		public String getSaturdayBefore() {
			return saturdayBefore;
		}

		public void setSaturdayBefore(String saturdayBefore) {
			curfewVar.setTime("saturdayBefore", saturdayBefore);
			this.saturdayBefore = saturdayBefore;
		}
		
		public String getSaturdayAfter() {
			return thursdayAfter;
		}

		public void setSaturdayAfter(String saturdayAfter) {
			curfewVar.setTime("saturdayAfter", saturdayAfter);
			this.saturdayAfter = saturdayAfter;
		}

		public String getSundayAfter() {
			return sundayAfter;
		}

		public void setSundayAfter(String sundayAfter) {
			curfewVar.setTime("sundayAfter", sundayAfter);
			this.sundayAfter = sundayAfter;
		}

		public String getSundayBefore() {
			return sundayBefore;
		}

		public void setSundayBefore(String sundayBefore) {
			curfewVar.setTime("sundayBefore", sundayBefore);
			this.sundayBefore = sundayBefore;
		}

	}

}
