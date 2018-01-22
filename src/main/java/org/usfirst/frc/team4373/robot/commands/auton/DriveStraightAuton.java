package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class DriveStraightAuton extends PIDCommand {
    private static double kP = 0.0100d;
    private static double kI = 0.0000d;
    private static double kD = 0.0010d;

    private double robotSpeed = 0.5d;

    private Drivetrain2017 drivetrain;

    /**
     * Does stuff.
     */
    public DriveStraightAuton() {
        super("DriveStraightAuton", kP, kI, kD);
        requires(this.drivetrain = Drivetrain2017.getInstance());
    }

    @Override
    protected void initialize() {
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-0.5, 0.5);

        this.getPIDController().setPID(kP, kI, kD);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    // @Override
    // protected void execute() {
    //     this.drivetrain.setRight(ROBOT_SPEED + pidOutput);
    //     this.drivetrain.setLeft(-ROBOT_SPEED - pidOutput);
    // }

    @Override
    protected void usePIDOutput(double output) {
        this.drivetrain.setRight(robotSpeed - output);
        this.drivetrain.setLeft(robotSpeed + output);
        // this.drivetrain.setRight(ROBOT_SPEED);
        // this.drivetrain.setLeft(ROBOT_SPEED);
        // this.pidOutput = output;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void end() {
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }
}
