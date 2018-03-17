package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.input.hid.Motors;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

public class DrivetrainCommand extends Command {

    private Drivetrain drivetrain;

    public DrivetrainCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double x = OI.getOI().getDriveJoystick().rooGetX();
        double y = OI.getOI().getDriveJoystick().rooGetY();
        double z = OI.getOI().getDriveJoystick().rooGetZ();
        drivetrain.setRight(y + z);
        drivetrain.setLeft(y - z);

        SmartDashboard.putNumber("drivetrain Right", y + z);
        SmartDashboard.putNumber("drivetrain Left", y - z);


        // Logging
        SmartDashboard.putNumber("L Pos", drivetrain.getLeftPosition());
        SmartDashboard.putNumber("L Pos (in)", drivetrain.getLeftPosition()
                * Motors.POSITION_CONVERSION_FACTOR);
        SmartDashboard.putNumber("L Vel", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("L Vel (in p s)", drivetrain.getLeftVelocity()
                * Motors.VELOCITY_CONVERSION_FACTOR);
        SmartDashboard.putNumber("R Pos", drivetrain.getRightPosition());
        SmartDashboard.putNumber("R Pos (in)", drivetrain.getRightPosition()
                * Motors.POSITION_CONVERSION_FACTOR);
        SmartDashboard.putNumber("R Vel", drivetrain.getRightVelocity());
        SmartDashboard.putNumber("R Vel (in p s)", drivetrain.getRightVelocity()
                * Motors.VELOCITY_CONVERSION_FACTOR);
        SmartDashboard.putNumber("Relative Angle (°)", OI.getOI().getAngleRelative());
    }

    @Override
    public void interrupted() {
        this.drivetrain.setBoth(0);
    }

    @Override
    public void end() {
        this.drivetrain.setBoth(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
