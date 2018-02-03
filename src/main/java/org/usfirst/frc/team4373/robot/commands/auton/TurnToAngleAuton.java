package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

public class TurnToAngleAuton extends PIDCommand {
    private double setpoint;

    private Drivetrain drivetrain;

    private boolean finished = false;
    private boolean coolingDown = false;
    private long cooldownStart = 0;
    private static final long COOLDOWN_TIME = 1000;
    private static final double COOLDOWN_THRESHOLD = 0.015;

    /**
     * Constructs a TurnToPosition command.
     * @param angle A position, from -180 to 180°, to which to turn.
     */
    public TurnToAngleAuton(double angle) {
        super("TurnToPosition", RobotMap.DRIVETRAIN_P, RobotMap.DRIVETRAIN_I,
                RobotMap.DRIVETRAIN_D);
        this.setpoint = angle;
        requires(Drivetrain.getInstance());
        drivetrain = Drivetrain.getInstance();
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(setpoint);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-RobotMap.AUTON_DRIVE_SPEED,
                RobotMap.AUTON_DRIVE_SPEED);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (Math.abs(output) < COOLDOWN_THRESHOLD) {
            this.coolingDown = true;
            this.cooldownStart = System.currentTimeMillis();
        }
        if (coolingDown) {
            if (System.currentTimeMillis() > this.cooldownStart + COOLDOWN_TIME) {
                this.finished = true;
            }
        }
        this.drivetrain.setLeft(output);
        this.drivetrain.setRight(-output);
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        OI.getOI().getGyro().reset();
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }
}