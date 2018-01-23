package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class TurnToPositionAuton extends PIDCommand {
    private static double kP = 0.1d;
    private static double kI = 0.0d;
    private static double kD = 0.0d;

    private static Drivetrain2017 drivetrain;

    private Boolean isCompleted;

    /**
     * Create the Auton.
     */
    public TurnToPositionAuton() {
        super("TurnToPositionAuton", kP, kI, kD);
        requires(Drivetrain2017.getInstance());
        drivetrain = Drivetrain2017.getInstance();
        this.isCompleted = false;
    }

    @Override
    protected void initialize() {
        OI.getOI().getGyro().reset();
        this.setSetpoint(SmartDashboard.getNumber("PID Setpoint: ", 0));
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-0.2, 0.2);
        this.getPIDController().setPID(kP, kI, kD);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double output) {
        this.drivetrain.setRight(-output);
        this.drivetrain.setLeft(output);
        if (output == 0) {
            this.isCompleted = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.isCompleted;
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