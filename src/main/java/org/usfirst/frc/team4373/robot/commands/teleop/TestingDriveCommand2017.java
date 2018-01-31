package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class TestingDriveCommand2017 extends Command {

    private Drivetrain2017 drivetrain;

    private double timeout = 15;

    public TestingDriveCommand2017() {
        requires(this.drivetrain = Drivetrain2017.getInstance());
    }

    @Override
    public void initialize() {
        setTimeout(timeout);
    }

    @Override
    public void execute() {
        this.drivetrain.setBoth(OI.getOI().getDriveJoystick().getY());
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
