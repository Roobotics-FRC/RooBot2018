package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {
    private Command autonCommand = null;
    private SendableChooser autonChooser = null;

    @Override
    public void robotInit() {
        autonChooser = new SendableChooser();
        autonChooser.addDefault("Disabled", "disabled");
        // Add auton commands to auton chooser here
        SmartDashboard.putData("Auton Mode Selector", autonChooser);

        OI.getOI().getGyro().calibrate();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        if (autonCommand != null) {
            autonCommand.cancel();
        }

        String command = (String) autonChooser.getSelected();

        switch (command) {
            // Check for auton command names here
            default:
                autonCommand = null;
        }
        if (autonCommand != null) {
            autonCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
