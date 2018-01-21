package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.ExperimentalRooDriveTrain;

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
        SmartDashboard.putData("Auton Mode Selector", autonChooser);

        OI.getOI().getGyro().calibrate();
        ExperimentalRooDriveTrain.getInstance();
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
