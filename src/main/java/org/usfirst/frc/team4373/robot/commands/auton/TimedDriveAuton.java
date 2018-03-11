package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

/**
 * Created by derros on 3/10/18.
 */
public class TimedDriveAuton extends Command {

    private double time;
    private Drivetrain drivetrain;
    private double start;
    private double maxDist;
    private double power;
    private boolean finished = false;
    private double initialPosition;

    /**
     * Timed drive autonomous.
     */
    public TimedDriveAuton(double secs, double distance, double power) {
        this.time = secs;
        this.maxDist = distance;
        this.power = -power;
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.start = Timer.getFPGATimestamp();
        this.initialPosition = drivetrain.getConvertedAveragePosition();
    }

    @Override
    protected void execute() {
        if (Timer.getFPGATimestamp() - this.start >= time
                || drivetrain.getConvertedAveragePosition() - this.initialPosition
                >= this.maxDist) {
            drivetrain.setBoth(0d);
            this.finished = true;
        } else {
            drivetrain.setBoth(power);
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void interrupted() {
        drivetrain.setBoth(0d);
    }

    @Override
    protected void end() {
        drivetrain.setBoth(0d);
    }
}
