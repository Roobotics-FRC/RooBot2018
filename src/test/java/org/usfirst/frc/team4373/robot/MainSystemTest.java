package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.util.WPILibVersion;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by thefangbear on 1/21/18.
 */
public class MainSystemTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp(): Running MainSystemTest/runSystemAssertions...");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void runSystemAssertions() throws Exception {
        // assert WPILib version
        Assert.assertEquals(WPILibVersion.Version, "2018.1.1");
    }
}
