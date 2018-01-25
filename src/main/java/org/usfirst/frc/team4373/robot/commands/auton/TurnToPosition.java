package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class TurnToPosition extends PIDCommand {
    private static double kP = 0.02d;
    private static double kI = 0.001d;
    private static double kD = 0.005d;
    private double setpoint;

    private Drivetrain2017 driveTrain;

    private boolean finished = false;
    private boolean coolingDown = false;
    private long cooldownStart = 0;
    private static final long COOLDOWN_TIME = 1000;
    private static final double COOLDOWN_THRESHOLD = 0.015;

    /**
     * Constructs a TurnToPosition command.
     * @param angle A position, from -180 to 180°, to which to turn.
     */
    public TurnToPosition(double angle) {
        super("TurnToPosition", kP, kI, kD);
        this.setpoint = angle;
        requires(Drivetrain2017.getInstance());
        driveTrain = Drivetrain2017.getInstance();
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        this.getPIDController().setPID(kP, kI, kD);
        this.setSetpoint(setpoint);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-0.4, 0.4);
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
        this.driveTrain.setLeft(output);
        this.driveTrain.setRight(-output);
        SmartDashboard.putNumber("PID Output", output);
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        System.out.println("***TurnToPosition ended***");
        OI.getOI().getGyro().reset();
        this.getPIDController().reset();
        this.driveTrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        System.out.println("***TurnToPosition interrupted***");
        this.getPIDController().reset();
        this.driveTrain.setBoth(0);
    }
}