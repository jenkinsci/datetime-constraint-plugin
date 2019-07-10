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
		
		private String mondayBefore = "16"; // todo other days of the week, time_zone, timeout
		
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
        	save();
            return false;
        }
        
    	
    	public ListBoxModel doFillMondayBeforeItems(){
    	    return new ListBoxModel(
    	        new Option("None", "00", mondayBefore.equals("00")),
    	        new Option("01:00", "01", mondayBefore.equals("01")),
    	        new Option("02:00", "02", mondayBefore.equals("02")),
    	        new Option("03:00", "03", mondayBefore.equals("03")),
    	        new Option("04:00", "04", mondayBefore.equals("04")),
    	        new Option("05:00", "05", mondayBefore.equals("05")),
    	        new Option("06:00", "06", mondayBefore.equals("06")),
    	        new Option("07:00", "07", mondayBefore.equals("07")),
    	        new Option("08:00", "08", mondayBefore.equals("08")),
    	        new Option("09:00", "09", mondayBefore.equals("09")),
    	        new Option("10:00", "10", mondayBefore.equals("10")),
    	        new Option("11:00", "11", mondayBefore.equals("11")),
    	        new Option("12:00", "12", mondayBefore.equals("12")),
    	        new Option("13:00", "13", mondayBefore.equals("13")),
    	        new Option("14:00", "14", mondayBefore.equals("14")),
    	        new Option("15:00", "15", mondayBefore.equals("15")),
    	        new Option("16:00", "16", mondayBefore.equals("16")),
    	        new Option("17:00", "17", mondayBefore.equals("17")),
    	        new Option("18:00", "18", mondayBefore.equals("18")),
    	        new Option("19:00", "19", mondayBefore.equals("19")),
    	        new Option("20:00", "20", mondayBefore.equals("20")),
    	        new Option("21:00", "21", mondayBefore.equals("21")),
    	        new Option("22:00", "22", mondayBefore.equals("22")),
    	        new Option("23:00", "23", mondayBefore.equals("23")),
    	        new Option("All Day", "24", mondayBefore.equals("24")));
    	}

		public String getMondayBefore() {
			return mondayBefore;
		}

		public void setMondayBefore(String mondayBefore) {
			curfewVar.setBefore(mondayBefore);
			this.mondayBefore = mondayBefore;
		}

	}

}
