package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class DriveStraightAuton extends PIDCommand {
    private static double kP = 0.1d;
    private static double kI = 0.0d;
    private static double kD = 0.0d;
    private Drivetrain2017 drivetrain;
    private double pidOutput;

    private boolean isFinished = false;

    /**
     * Constructs a TurnToPosition command.
     */
    public DriveStraightAuton() {
        super("TurnToPosition", kP, kI, kD);
        requires(Drivetrain2017.getInstance());
        drivetrain = Drivetrain2017.getInstance();
        setInterruptible(true);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double output) {
        this.pidOutput = output;
        SmartDashboard.putNumber("PID Output", this.pidOutput);
    }

    @Override
    protected void initialize() {
        OI.getOI().getGyro().reset();
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-0.2, 0.2);
    }

    @Override
    protected void execute() {
        if (isFinished) return;
        kP = SmartDashboard.getNumber("kP", 0.0d);
        kI = SmartDashboard.getNumber("kI", 0.0d);
        kD = SmartDashboard.getNumber("kD", 0.0d);
        this.getPIDController().setPID(kP, kI, kD);

        this.drivetrain.setLeft(this.pidOutput);
        this.drivetrain.setRight(this.pidOutput);

        SmartDashboard.putNumber("Current Setpoint", this.getSetpoint());

        this.setSetpoint(SmartDashboard.getNumber("PID Setpoint", 0));

        if (SmartDashboard.getBoolean("Toggle PID Command?", true)) {
            isFinished = true;
            SmartDashboard.putBoolean("Toggle PID Command?", false);
        }

    }

    @Override
    protected boolean isFinished() {
        return this.isFinished;
    }

    @Override
    protected void end() {
        System.out.println("***PID Command terminated***");
        OI.getOI().getGyro().reset();
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }
//
//    private static double kP = 0.1000d;
//    private static double kI = 0.0001d;
//    private static double kD = 0.0000d;
//
//    private static double ROBOT_SPEED = 0.6d;
//    private double pidOutput = 0;
//
//    private Drivetrain2017 drivetrain;
//
//    public DriveStraightAuton() {
//        super("DriveStraightAuton", kP, kI, kD);
//        requires(this.drivetrain = Drivetrain2017.getInstance());
//    }
//
//    @Override
//    protected void initialize() {
//        OI.getOI().getGyro().reset();
//        this.setSetpoint(0);
//        this.setInputRange(-180, 180);
//        this.getPIDController().setOutputRange(-1, 1);
//
//        this.getPIDController().setPID(kP, kI, kD);
//    }
//
//    @Override
//    protected double returnPIDInput() {
//        return OI.getOI().getAngleRelative();
//    }
//
//    @Override
//    protected void execute() {
//        this.drivetrain.setRight(ROBOT_SPEED + pidOutput);
//        this.drivetrain.setLeft(-ROBOT_SPEED - pidOutput);
//    }
//
//    @Override
//    protected void usePIDOutput(double output) {
//        this.pidOutput = output;
//    }
//
//    @Override
//    protected boolean isFinished() {
//        return false;
//    }
//
//    @Override
//    protected void interrupted() {
//        this.getPIDController().reset();
//        this.drivetrain.setBoth(0);
//    }
//
//    @Override
//    protected void end() {
//        this.getPIDController().reset();
//        this.drivetrain.setBoth(0);
//    }
}
