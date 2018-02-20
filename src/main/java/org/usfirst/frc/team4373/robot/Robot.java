package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.DualPIDTuning;
import org.usfirst.frc.team4373.robot.commands.VerticalExtenderSetter;
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
        autonChooser.addObject("Tune Double", "double");
        autonChooser.addObject("Tune Dual", "dual");

        // Add auton commands to auton chooser here
        SmartDashboard.putData("Auton Mode Selector", autonChooser);

        SmartDashboard.putNumber("Elevator Setpoint", 15);
        SmartDashboard.putNumber("PrimaryIntake Setpoint", 15);
        SmartDashboard.putNumber("P", 0);
        SmartDashboard.putNumber("I", 0);
        SmartDashboard.putNumber("D", 0);

        OI.getOI().getGyro().calibrate();

        Drivetrain.getInstance();
        Elevator.getInstance();
        Intake.getInstance().startCompressor();
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

        int primary = (int) SmartDashboard.getNumber("PrimaryIntake Setpoint", 15);
        int elevator = (int) SmartDashboard.getNumber("Elevator Setpoint", 15);
        double prop = SmartDashboard.getNumber("P", 0);
        double itg = SmartDashboard.getNumber("I", 0);
        double drv = SmartDashboard.getNumber("D", 0);

        switch (command) {
            case "double":
                autonCommand = new VerticalExtenderSetter("Tune Double", primary, elevator);
                break;
            case "dual":
                autonCommand = new DualPIDTuning(primary, prop, itg, drv);
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
