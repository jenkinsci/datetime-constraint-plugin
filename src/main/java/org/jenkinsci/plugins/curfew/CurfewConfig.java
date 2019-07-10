package org.jenkinsci.plugins.curfew;

import javax.inject.Inject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.Descriptor;
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
		
		private String mondayBefore = "6:00"; // todo other days of the week, time_zone, timeout
		
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

		public String getMondayBefore() {
			return mondayBefore;
		}

		public void setMondayBefore(String mondayBefore) {
			curfewVar.setBefore(mondayBefore);
			this.mondayBefore = mondayBefore;
		}

	}

}
