package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.input.hid.RooJoystick;
import org.usfirst.frc.team4373.robot.subsystems.ExperimentalRooDriveTrain;

public class ExperimentalDriveCommand extends Command {

    private ExperimentalRooDriveTrain driveTrain;
    private OI oi;
    private RooJoystick joystick;

    /**
     * Teleop command for controlling driveTrain via RooJoystick on Testing bot.
     */
    public ExperimentalDriveCommand() {
        super("ExperimentalDriveCommand");
        requires(driveTrain = ExperimentalRooDriveTrain.getInstance());
        this.oi = OI.getOI();
        this.joystick = this.oi.getDriveJoystick();
    }

    @Override
    protected void initialize() {
        this.driveTrain.stop();
    }

    @Override
    protected void execute() {
        driveTrain.setRight(this.joystick.getY() - this.joystick.getZ());
        driveTrain.setLeft(this.joystick.getY() + this.joystick.getZ());
        driveTrain.setMiddle(this.joystick.getX());
        SmartDashboard.putNumber("Left Velocity", driveTrain.getLeftEncoder()[1]);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.driveTrain.stop();
    }

    @Override
    protected void interrupted() {
        this.driveTrain.stop();
    }
}
