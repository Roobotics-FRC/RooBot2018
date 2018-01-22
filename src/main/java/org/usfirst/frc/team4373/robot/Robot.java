package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.DriveStraightAuton;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

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
        autonChooser.addObject("Drive Straight", "driveStraight");
        SmartDashboard.putData("Auton Mode Selector", autonChooser);

        OI.getOI().getGyro().calibrate();
        Drivetrain2017.getInstance();
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
        OI.getOI().getGyro().reset();
        if (autonCommand != null) {
            autonCommand.cancel();
        }

        String command = (String) autonChooser.getSelected();

        switch (command) {
            case "driveStraight":
                autonCommand = new DriveStraightAuton();
                break;
            default:
                autonCommand = null;
        }
        if (autonCommand != null) {
            autonCommand.start();
        }
    }

    @Override
    public void testInit() {
        Scheduler.getInstance().removeAll();
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
