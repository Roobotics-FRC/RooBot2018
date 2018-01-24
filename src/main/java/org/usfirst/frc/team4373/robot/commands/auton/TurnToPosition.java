package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class TurnToPosition extends PIDCommand {
    private static double kP = 0.0d;
    private static double kI = 0.0d;
    private static double kD = 0.0d;
    private Drivetrain2017 driveTrain;

    private boolean finished = false;
    private boolean coolingDown = false;
    private long cooldownStart = 0;
    private static final long COOLDOWN_TIME = 1000;
    private static final double COOLDOWN_THRESHOLD = 0.01;

    /**
     * Constructs a TurnToPosition command.
     */
    public TurnToPosition() {
        super("TurnToPosition", kP, kI, kD);
        requires(Drivetrain2017.getInstance());
        driveTrain = Drivetrain2017.getInstance();
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        kP = SmartDashboard.getNumber("kP", 0.0d);
        kI = SmartDashboard.getNumber("kI", 0.0d);
        kD = SmartDashboard.getNumber("kD", 0.0d);
        this.getPIDController().setPID(kP, kI, kD);
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
                System.out.println("*******SETTING FINISHED TO TRUE*********");
                this.finished = true;
            }
        }
        this.driveTrain.setLeft(output);
        this.driveTrain.setRight(-output);
        this.setSetpoint(SmartDashboard.getNumber("PID Setpoint", 0));
        SmartDashboard.putNumber("PID Output", output);
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        System.out.println("***TurnToPosition terminated***");
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