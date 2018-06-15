package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

/**
 * Turns to a given angle using PID.
 *
 * @author aaplmath
 */
public class TurnToAngleAuton extends PIDCommand {
    private double setpoint;

    private Drivetrain drivetrain;

    private boolean finished = false;
    private boolean coolingDown = false;
    private long cooldownStart = 0;
    private static final long COOLDOWN_TIME = 500;
    private static final double COOLDOWN_THRESHOLD = RobotMap.AUTON_DRIVE_SPEED * 0.2;

    /**
     * Constructs a TurnToPosition command.
     * @param angle A position, from -180 to 180°, to which to turn.
     */
    public TurnToAngleAuton(double angle) {
        super("TurnToPosition", RobotMap.DRIVETRAIN_P, RobotMap.DRIVETRAIN_I,
                RobotMap.DRIVETRAIN_D);
        this.setpoint = angle;
        requires(this.drivetrain = Drivetrain.getInstance());
        setInterruptible(true);
        setTimeout(2);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(setpoint);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-RobotMap.AUTON_DRIVE_SPEED,
                RobotMap.AUTON_DRIVE_SPEED);
        this.getPIDController().setPID(SmartDashboard.getNumber("kP", RobotMap.DRIVETRAIN_P),
                SmartDashboard.getNumber("kI", RobotMap.DRIVETRAIN_I),
                SmartDashboard.getNumber("kD", RobotMap.DRIVETRAIN_D));
        SmartDashboard.putBoolean("TTA Finished", false);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double output) {
        SmartDashboard.putNumber("TTA Output", output);
        if (Math.abs(output) < COOLDOWN_THRESHOLD) {
            this.coolingDown = true;
            this.cooldownStart = System.currentTimeMillis();
        }
        if (coolingDown) {
            if (System.currentTimeMillis() - COOLDOWN_TIME > this.cooldownStart) {
                this.finished = true;
                this.drivetrain.setBoth(0);
                return;
            }
        }
        this.drivetrain.setLeft(-output);
        this.drivetrain.setRight(output);
    }

    @Override
    protected boolean isFinished() {
        return this.finished || this.isTimedOut();
    }

    @Override
    protected void end() {
        OI.getOI().getGyro().reset();
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
        SmartDashboard.putBoolean("TTA Finished", true);
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }
}