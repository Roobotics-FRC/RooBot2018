package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.DriveDistanceAuton;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {
    private Command autonCommand = null;
    private SendableChooser<String> autonChooser = null;

    @Override
    public void robotInit() {
        autonChooser = new SendableChooser<>();
        autonChooser.addDefault("Disabled", "disabled");
        autonChooser.addObject("Tune Drive", "tune");
        // Add auton commands to auton chooser here
        SmartDashboard.putData("Auton Mode Selector", autonChooser);
        SmartDashboard.putNumber("Drivetrain P", RobotMap.DRIVETRAIN_P);
        SmartDashboard.putNumber("Drivetrain I", RobotMap.DRIVETRAIN_I);
        SmartDashboard.putNumber("Drivetrain D", RobotMap.DRIVETRAIN_D);
        SmartDashboard.putNumber("Drive Setpoint", 0);

        Drivetrain.getInstance();

        OI.getOI().getGyro().calibrate();
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

        String command = autonChooser.getSelected();

        switch (command) {
            case "tune":
                autonCommand = new DriveDistanceAuton(SmartDashboard.getNumber(
                        "Drive Setpoint", 0));
                break;
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
