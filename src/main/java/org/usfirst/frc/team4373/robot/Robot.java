package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
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
 * @author Samasaur
 */
public class Robot extends IterativeRobot {

    private SendableChooser<String> pistonState;

    @Override
    public void robotInit() {
        DefaultCommand.getInstance();
        Drivetrain.getInstance();
        Elevator.getInstance();
        Intake.getInstance().startCompressor();

        OI.getOI().getGyro().calibrate();

        pistonState = new SendableChooser<>();
        pistonState.addDefault("Off", "Off");
        pistonState.addObject("On", "On");
        pistonState.addObject("Neutral", "Neutral");
        SmartDashboard.putData("Intake State", pistonState);
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        OI.getOI().getGyro().reset();
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        OI.getOI().getGyro().reset();
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
