package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class TestTurnToPositionAuton extends PIDCommand {
    private static double kP = 0.025d;
    private static double kI = 0.000d;
    private static double kD = 0.006d;
    private double setpoint;

    private Drivetrain2017 driveTrain;

    /**
     * Constructs a TurnToPosition command.
     */
    public TestTurnToPositionAuton() {
        super("TurnToPosition", kP, kI, kD);
        this.setpoint = SmartDashboard.getNumber("PID Setpoint", 0);
        requires(Drivetrain2017.getInstance());
        driveTrain = Drivetrain2017.getInstance();
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        kP = SmartDashboard.getNumber("kP", 0);
        kI = SmartDashboard.getNumber("kI", 0);
        kD = SmartDashboard.getNumber("kD", 0);
        this.setpoint = SmartDashboard.getNumber("PID Setpoint", 0);
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
        this.driveTrain.setLeft(output);
        this.driveTrain.setRight(-output);
        SmartDashboard.putNumber("PID Output", output);
    }

    @Override
    protected boolean isFinished() {
        return false;
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