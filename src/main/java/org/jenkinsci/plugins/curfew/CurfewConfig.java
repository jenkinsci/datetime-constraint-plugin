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
		
		// todo other days of the week, time_zone, timeout
		private String mondayBefore = "8"; 
		private String mondayAfter = "16";
		private String thursdayBefore = "8"; 
		private String thursdayAfter = "16";
		private String saturdayBefore = "8"; 
		private String saturdayAfter = "16";
		
		
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
        	setThursdayBefore(formData.getString("thursdayBefore"));
        	setThursdayAfter(formData.getString("thursdayAfter"));
        	setSaturdayBefore(formData.getString("saturdayBefore"));
        	setSaturdayAfter(formData.getString("saturdayAfter"));
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

	}

}
