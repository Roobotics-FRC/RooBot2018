package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class DriveStraightAuton extends PIDCommand {

    private static double kP = 0.1000d;
    private static double kI = 0.0001d;
    private static double kD = 0.0000d;

    private static double ROBOT_SPEED = 0.5d;
    private double pidOutput = 0;

    private Drivetrain2017 drivetrain;

    public DriveStraightAuton() {
        super("DriveStraightAuton", kP, kI, kD);
        requires(this.drivetrain = Drivetrain2017.getInstance());
    }

    @Override
    protected void initialize() {
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-1, 1);

        this.getPIDController().setPID(kP, kI, kD);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void execute() {
        this.drivetrain.setRight(ROBOT_SPEED + pidOutput);
        this.drivetrain.setLeft(-ROBOT_SPEED - pidOutput);
    }

    @Override
    protected void usePIDOutput(double output) {
        this.pidOutput = output;
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
