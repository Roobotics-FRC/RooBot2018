package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        SmartDashboard.putNumber("L Pos", drivetrain.getLeftEncoder()[0]);
        SmartDashboard.putNumber("L Vel", drivetrain.getLeftEncoder()[1]);
        SmartDashboard.putNumber("L Pos (in)", drivetrain.getLeftEncoder()[0]
                * Drivetrain2017.POSITION_CONVERSION_FACTOR);
        SmartDashboard.putNumber("L Vel (in∕s)", drivetrain.getLeftEncoder()[1]
                * Drivetrain2017.VELOCITY_CONVERSION_FACTOR);
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
