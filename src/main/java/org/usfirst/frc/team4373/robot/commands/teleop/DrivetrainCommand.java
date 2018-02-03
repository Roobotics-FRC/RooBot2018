package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

public class DrivetrainCommand extends Command {

    private Drivetrain drivetrain;

    public DrivetrainCommand() {
        this.drivetrain = Drivetrain.getInstance();
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double x = OI.getOI().getDriveJoystick().rooGetX();
        double y = OI.getOI().getDriveJoystick().rooGetY();
        double z = OI.getOI().getDriveJoystick().rooGetZ();
        drivetrain.setRight(y - z);
        drivetrain.setLeft(y + z);
        drivetrain.setMiddle(x);
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
