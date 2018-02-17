package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * This is the main robot class.
 *
 * @author aaplmath
 * @author Henry Pitcairn
 * @author thefangbear
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

        Drivetrain.getInstance();
        Elevator.getInstance();
        Intake.getInstance();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        OI.getOI().getGyro().reset();
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
    public void testInit() {}

    @Override
    public void disabledInit() {}

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void disabledPeriodic() {}
}
