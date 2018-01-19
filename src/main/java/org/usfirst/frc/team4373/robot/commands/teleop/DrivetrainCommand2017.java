package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class DrivetrainCommand2017 extends Command {

    private Drivetrain2017 drivetrain;

    public DrivetrainCommand2017() {
        super("DrivetrainCommand2017");
        requires(drivetrain = Drivetrain2017.getInstance());
    }

    @Override
    protected void initialize() {
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void execute() {
        drivetrain.setBoth(OI.getOI().getDriveJoystick().getAxis(1));
    }


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.drivetrain.setBoth(0);
    }
}
