package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.input.hid.RooJoystick;
import org.usfirst.frc.team4373.robot.input.utilities.DoubleComparator;
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
        // get all the data
        double jX = this.joystick.getX();
        double jY = this.joystick.getY();
        double twist = this.joystick.getZ();

        if (DoubleComparator.eq(twist, 0.0d)) {
            // if we are driving, just use middle to move left/right
            driveTrain.setBoth(jY);
            driveTrain.setMiddle(jX);
        } else {
            // if we are twisting, positive == towards right (negate the sign)
            // make it turn
            driveTrain.setRight(-twist);
            driveTrain.setLeft(twist);
            // do not move
            driveTrain.setMiddle(0d);
        }
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
