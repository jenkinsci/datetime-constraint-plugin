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

	public String getMondayBefore(){    	
		CurfewConfigDesc descriptor = (CurfewConfigDesc) super.getDescriptor();    	
		return descriptor.getMondayBefore();
	}
	
	@Extension
    public static final class CurfewConfigDesc extends Descriptor<GlobalConfiguration> {
		
		@Inject
		private transient CurfewGlobalVariable curfewVar;
		
		private String mondayBefore = "8"; // todo other days of the week, time_zone, timeout
		private String mondayAfter = "16";
		
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

		private Option option(int i, String field) { // todo later NONE & ALL DAY wont be displayed
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
			curfewVar.setMondayBefore(mondayBefore);
			this.mondayBefore = mondayBefore;
		}

		public String getMondayAfter() {
			return mondayAfter;
		}

		public void setMondayAfter(String mondayAfter) {
			curfewVar.setMondayAfter(mondayAfter);
			this.mondayAfter = mondayAfter;
		}

	}

}
