package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.*;
import org.usfirst.frc.team4373.robot.datatypes.Position;
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
        priority1Chooser.addObject("Do Nothing", "nothing");
        priority1Chooser.addObject("Switch", "switch");
        priority1Chooser.addObject("Scale", "scale");

        priority2Chooser = new SendableChooser<>();
        priority2Chooser.addDefault("Just Drive", "drive");
        priority2Chooser.addObject("Do Nothing", "nothing");
        priority2Chooser.addObject("Switch", "switch");
        priority2Chooser.addObject("Scale", "scale");

        SmartDashboard.putData("Auton Start Position", positionChoooser);
        SmartDashboard.putData("Auton Primary Goal", priority1Chooser);
        SmartDashboard.putData("Auton Secondary Goal", priority2Chooser);

        SmartDashboard.putNumber("Elevator Speed", SmartDashboard.getNumber(
                "Elevator Speed", RobotMap.ELEVATOR_SPEED));
        SmartDashboard.putNumber("Intake Speed", SmartDashboard.getNumber(
                "Intake Speed", RobotMap.INTAKE_SPEED));
        SmartDashboard.putNumber("Driving Time", 2.5);
        SmartDashboard.putNumber("Driving Distance", 250);
        SmartDashboard.putNumber("Driving Power", 0.5);

        SmartDashboard.putBoolean("Try TurnToAngleAuton", false);
        SmartDashboard.putNumber("Angle to turn to", 90);

        SmartDashboard.putNumber("kP", RobotMap.DRIVETRAIN_P);
        SmartDashboard.putNumber("kI", RobotMap.DRIVETRAIN_I);
        SmartDashboard.putNumber("kD", RobotMap.DRIVETRAIN_D);

        OI.getOI().getGyro().calibrate();

        Drivetrain.getInstance();
        Elevator.getInstance();
        Intake.getInstance().startCompressor();
    }

    @Override
    public void teleopInit() {
        RobotMap.ELEVATOR_SPEED = SmartDashboard.getNumber("Elevator Speed",
                RobotMap.ELEVATOR_SPEED);
        RobotMap.INTAKE_SPEED = SmartDashboard.getNumber("Intake Speed",
                RobotMap.INTAKE_SPEED);
        Scheduler.getInstance().removeAll();
        OI.getOI().getGyro().reset();
    }

    @Override
    public void autonomousInit() {
        OI.getOI().getGyro().reset();
        RobotMap.ELEVATOR_SPEED = SmartDashboard.getNumber("Elevator Speed",
                RobotMap.ELEVATOR_SPEED);
        RobotMap.INTAKE_SPEED = SmartDashboard.getNumber("Intake Speed",
                RobotMap.INTAKE_SPEED);
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
        Position startPos;
        Position switchPos;
        Position scalePos;
        if (pos == 'L') {
            startPos = Position.Center;
        } else if (pos == 'C') {
            startPos = Position.Left;
        } else {
            startPos = Position.Right;
        }
        if (switchData == 'L') {
            switchPos = Position.Left;
        } else {
            switchPos = Position.Right;
        }
        if (scaleData == 'L') {
            scalePos = Position.Left;
        } else {
            scalePos = Position.Right;
        }

        switch (priority1) {
            case "nothing":
                System.out.println("Doing nothing");
                autonCommand = new RetainCubeAuton();
                break;
            case "drive":
                System.out.println("Driving");
                if (startPos != Position.Center) {
                    //autonCommand = new DriveDistanceAuton(RobotMap.AUTON_DRIVE_DISTANCE);
                    autonCommand = new TimedDriveAuton(
                            SmartDashboard.getNumber("Driving Time", 2.5),
                            SmartDashboard.getNumber("Driving Distance", 250),
                            SmartDashboard.getNumber("Driving Power", 0.5));
                } else {
                    System.out.println("Can't drive; in center!");
                    switch (priority2) {
                        case "nothing":
                            System.out.println("Doing nothing");
                            autonCommand = new RetainCubeAuton();
                            break;
                        case "switch":
                            System.out.println("Going for switch");
                            if (startPos == Position.Center || startPos == switchPos) {
                                autonCommand = new CaptureSwitchAuton(startPos, switchPos);
                            } else {
                                System.out.println("Can't get switch; opposite sides!");
                                System.out.println("Doing nothing");
                                autonCommand = new RetainCubeAuton();
                            }
                            break;
                        case "scale":
                            System.out.println("Going for scale");
                            if (startPos == scalePos) {
                                autonCommand = new CaptureScaleAuton(scalePos);
                            } else {
                                System.out.println("Can't get scale; not on same side!");
                                System.out.println("Doing nothing");
                                autonCommand = new RetainCubeAuton();
                            }
                            break;
                        default:
                            System.out.println("Doing nothing");
                            autonCommand = new RetainCubeAuton();
                    }
                }
                break;
            case "switch":
                System.out.println("Going for switch");
                if (startPos == Position.Center || startPos == switchPos) {
                    autonCommand = new CaptureSwitchAuton(startPos, switchPos);
                } else {
                    System.out.println("Can't get switch; opposite sides!");
                    switch (priority2) {
                        case "nothing":
                            System.out.println("Doing nothing");
                            autonCommand = new RetainCubeAuton();
                            break;
                        case "drive":
                            System.out.println("Driving");
                            if (startPos != Position.Center) {
                                //autonCommand = new DriveDistanceAuton(RobotMap.AUTON_DRIVE_DISTANCE);
                                autonCommand = new TimedDriveAuton(
                                        SmartDashboard.getNumber("Driving Time", 2.5),
                                        SmartDashboard.getNumber("Driving Distance", 250),
                                        SmartDashboard.getNumber("Driving Power", 0.5));
                            } else {
                                System.out.println("Can't drive; in center!");
                                System.out.println("Doing nothing");
                                autonCommand = new RetainCubeAuton();
                            }
                            break;
                        case "scale":
                            System.out.println("Going for scale");
                            if (startPos == scalePos) {
                                autonCommand = new CaptureScaleAuton(scalePos);
                            } else {
                                System.out.println("Can't get scale; not on same side!");
                                System.out.println("Doing nothing");
                                autonCommand = new RetainCubeAuton();
                            }
                            break;
                        default:
                            System.out.println("Doing nothing");
                            autonCommand = new RetainCubeAuton();
                    }
                }
                break;
            case "scale":
                System.out.println("Going for scale");
                if (startPos == scalePos) {
                    autonCommand = new CaptureScaleAuton(scalePos);
                } else {
                    System.out.println("Can't get scale; not on same side!");
                    switch (priority2) {
                        case "nothing":
                            System.out.println("Doing nothing");
                            autonCommand = new RetainCubeAuton();
                            break;
                        case "drive":
                            System.out.println("Driving");
                            if (startPos != Position.Center) {
                                //autonCommand = new DriveDistanceAuton(RobotMap.AUTON_DRIVE_DISTANCE);
                                autonCommand = new TimedDriveAuton(
                                        SmartDashboard.getNumber("Driving Time", 2.5),
                                        SmartDashboard.getNumber("Driving Distance", 250),
                                        SmartDashboard.getNumber("Driving Power", 0.5));
                            } else {
                                System.out.println("Can't drive; in center!");
                                System.out.println("Doing nothing");
                                autonCommand = new RetainCubeAuton();
                            }
                            break;
                        case "switch":
                            System.out.println("Going for switch");
                            if (startPos == Position.Center || startPos == switchPos) {
                                autonCommand = new CaptureSwitchAuton(startPos, switchPos);
                            } else {
                                System.out.println("Can't get switch; opposite sides!");
                                System.out.println("Doing nothing");
                                autonCommand = new RetainCubeAuton();
                            }
                            break;
                        default:
                            System.out.println("Doing nothing");
                            autonCommand = new RetainCubeAuton();
                    }
                }
                break;
            default:
                System.out.println("Doing nothing");
                autonCommand = new RetainCubeAuton();
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
