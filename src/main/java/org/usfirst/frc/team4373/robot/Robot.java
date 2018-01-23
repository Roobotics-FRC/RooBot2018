package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.TurnToPosition;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {
    private Command autonCommand = null;
    private SendableChooser<Character> positionChoooser = null;
    private SendableChooser<String> priority1Chooser = null;
    private SendableChooser<String> priority2Chooser = null;

    @Override
    public void robotInit() {
        positionChoooser = new SendableChooser();
        positionChoooser.addDefault("Center", 'C');
        positionChoooser.addObject("Left", 'L');
        positionChoooser.addObject("Right", 'R');

        priority1Chooser = new SendableChooser();
        priority1Chooser.addDefault("Just Drive", "drive");
        priority1Chooser.addObject("Switch", "switch");
        priority1Chooser.addObject("Scale", "scale");

        priority2Chooser = new SendableChooser();
        priority2Chooser.addDefault("Just Drive", "drive");
        priority2Chooser.addObject("Switch", "switch");
        priority2Chooser.addObject("Scale", "scale");

        SmartDashboard.putData("Auton Start Position", positionChoooser);
        SmartDashboard.putData("Auton Primary Goal", priority1Chooser);
        SmartDashboard.putData("Auton Secondary Goal", priority2Chooser);

        // P,I,D constants: 0.05,0.001,0.1
        SmartDashboard.putNumber("PID Setpoint", 0);
        SmartDashboard.putNumber("kP", 0.0d);
        SmartDashboard.putNumber("kI", 0.0d);
        SmartDashboard.putNumber("kD", 0.0d);

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

        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        Character switchData = gameData.charAt(0);
        Character scaleData = gameData.charAt(1);
        Character pos = positionChoooser.getSelected();
        String priority1 = priority1Chooser.getSelected();
        String priority2 = priority2Chooser.getSelected();

        if (priority1.equals("switch")) {
            if (pos == switchData) {
                autonCommand = new TurnToPosition();
                System.out.println("GOING FOR SWITCH");
            } else if (priority2.equals("scale") && pos == scaleData) {
                System.out.println("GOING FOR SCALE");
            } else {
                System.out.println("DRIVING");
            }
        } else if (priority1.equals("scale")) {
            if (pos == scaleData) {
                System.out.println("GOING FOR SCALE");
            } else if (priority2.equals("switch") && pos == switchData) {
                System.out.println("GOING FOR SWITCH");
            } else {
                System.out.println("DRIVING");
            }
        } else {
            System.out.println("DRIVING");
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
