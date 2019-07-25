package org.jenkinsci.plugins.curfew;

import static org.junit.Assert.*;

import org.jenkinsci.plugins.curfew.Customizer.CurfewConfigDesc;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class CustomizerTest {
	
	@Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
	
    @Test
    public void shouldKeepMinForWaitTime() {
    	CurfewConfigDesc descriptor = new CurfewConfigDesc();
    	
    	descriptor.setWaitTime("5");
    	assertEquals(descriptor.getWaitTime(), "15");
    	
    	descriptor.setWaitTime("25");
    	assertEquals(descriptor.getWaitTime(), "25");
    	
    	descriptor.setWaitTime("15");
    	assertEquals(descriptor.getWaitTime(), "15");
    	
    	descriptor.setWaitTime("");
    	assertEquals(descriptor.getWaitTime(), "15");
    	
    	descriptor.setWaitTime(" ");
    	assertEquals(descriptor.getWaitTime(), "15");

    	descriptor.setWaitTime(null);
    	assertEquals(descriptor.getWaitTime(), "15");
    	
    	descriptor.setWaitTime("-30");
    	assertEquals(descriptor.getWaitTime(), "15");
    }

}
