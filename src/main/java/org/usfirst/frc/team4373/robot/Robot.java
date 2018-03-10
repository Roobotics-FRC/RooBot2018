package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.CaptureSwitchAuton;
import org.usfirst.frc.team4373.robot.commands.auton.DriveDistanceAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimedDriveAuton;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * This is the main robot class.
 *
 * @author aaplmath
 * @author thefangbear
 */
public class Robot extends IterativeRobot {
    private Command autonCommand = null;
    private SendableChooser<Character> positionChoooser = null;
    private SendableChooser<String> priority1Chooser = null;
    private SendableChooser<String> priority2Chooser = null;

    @Override
    public void robotInit() {
        positionChoooser = new SendableChooser<>();
        positionChoooser.addDefault("Center", 'C');
        positionChoooser.addObject("Left", 'L');
        positionChoooser.addObject("Right", 'R');

        priority1Chooser = new SendableChooser<>();
        priority1Chooser.addDefault("Just Drive", "drive");
        priority1Chooser.addObject("Switch", "switch");
        priority1Chooser.addObject("Scale", "scale");

        priority2Chooser = new SendableChooser<>();
        priority2Chooser.addDefault("Just Drive", "drive");
        priority2Chooser.addObject("Switch", "switch");
        priority2Chooser.addObject("Scale", "scale");

        SmartDashboard.putData("Auton Start Position", positionChoooser);
        SmartDashboard.putData("Auton Primary Goal", priority1Chooser);
        SmartDashboard.putData("Auton Secondary Goal", priority2Chooser);

        SmartDashboard.putNumber("Vertical Extender Speed", SmartDashboard.getNumber(
                "Vertical Extender Speed", RobotMap.VERTICAL_EXTENDER_SPEED));
        SmartDashboard.putNumber("Driving Time", 15);
        SmartDashboard.putNumber("Driving Distance", 250);
        SmartDashboard.putNumber("Driving Power", 0.5);

        OI.getOI().getGyro().calibrate();

        Drivetrain.getInstance();
        Elevator.getInstance();
        Intake.getInstance().startCompressor();
    }

    @Override
    public void teleopInit() {
        RobotMap.VERTICAL_EXTENDER_SPEED = SmartDashboard.getNumber("Vertical Extender Speed",
                RobotMap.VERTICAL_EXTENDER_SPEED);
        Scheduler.getInstance().removeAll();
        OI.getOI().getGyro().reset();
    }

    @Override
    public void autonomousInit() {
        RobotMap.VERTICAL_EXTENDER_SPEED = SmartDashboard.getNumber("Vertical Extender Speed",
                RobotMap.VERTICAL_EXTENDER_SPEED);
        Scheduler.getInstance().removeAll();
        if (autonCommand != null) {
            autonCommand.cancel();
        }

        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        Character switchData = gameData.charAt(0);
        Character scaleData = gameData.charAt(1);
        Character pos = positionChoooser.getSelected();
        String priority1 = priority1Chooser.getSelected();
        String priority2 = priority2Chooser.getSelected();
        boolean onLeft = pos == 'L';

        if (priority1.equals("switch")) {
            if (pos == switchData) {
                System.out.println("GOING FOR SWITCH");
                autonCommand = new CaptureSwitchAuton(onLeft);
            } else if (priority2.equals("scale") && pos == scaleData) {
                System.out.println("GOING FOR SCALE");
            } else {
                System.out.println("DRIVING");
                //autonCommand = new DriveDistanceAuton(RobotMap.AUTON_DRIVE_DISTANCE);
                autonCommand = new TimedDriveAuton(SmartDashboard.getNumber("Driving Time", 15),
                        SmartDashboard.getNumber("Driving Distance", 250),
                        SmartDashboard.getNumber("Driving Power", 0.5));
            }
        } else if (priority1.equals("scale")) {
            if (pos == scaleData) {
                System.out.println("GOING FOR SCALE");
            } else if (priority2.equals("switch") && pos == switchData) {
                System.out.println("GOING FOR SWITCH");
                autonCommand = new CaptureSwitchAuton(onLeft);
            } else {
                System.out.println("DRIVING");
                //autonCommand = new DriveDistanceAuton(RobotMap.AUTON_DRIVE_DISTANCE);
                autonCommand = new TimedDriveAuton(SmartDashboard.getNumber("Driving Time", 15),
                        SmartDashboard.getNumber("Driving Distance", 250),
                        SmartDashboard.getNumber("Driving Power", 0.5));
            }
        } else {
            System.out.println("DRIVING");
            //autonCommand = new DriveDistanceAuton(RobotMap.AUTON_DRIVE_DISTANCE);
            autonCommand = new TimedDriveAuton(SmartDashboard.getNumber("Driving Time", 15),
                    SmartDashboard.getNumber("Driving Distance", 250),
                    SmartDashboard.getNumber("Driving Power", 0.5));
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
