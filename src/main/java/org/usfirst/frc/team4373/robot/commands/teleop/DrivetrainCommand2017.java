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
        double twistAxis = OI.getOI().getDriveJoystick().getAxis(2);
        double horizontalAxis = OI.getOI().getDriveJoystick().getAxis(0);
        double forwardAxis = OI.getOI().getDriveJoystick().getAxis(1);

        double right = forwardAxis;
        double left = forwardAxis;
        if (twistAxis == 0 && forwardAxis != 0) { // Just forward
            drivetrain.setLeft(left);
            drivetrain.setRight(right);
        } else if (twistAxis != 0 && forwardAxis != 0) { // Twist and forward
            if (twistAxis > 0) {
                right -= twistAxis;
            } else if (twistAxis < 0) {
                left -= Math.abs(twistAxis);
            }
            drivetrain.setRight(right);
            drivetrain.setLeft(left);
        } else if (twistAxis != 0 && forwardAxis == 0) { // Just twist
            drivetrain.setRight(-twistAxis);
            drivetrain.setLeft(twistAxis);
        } else {
            drivetrain.setBoth(0);
        }
        drivetrain.setMiddle(horizontalAxis);
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
