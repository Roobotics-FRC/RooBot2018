package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class DriveStraightAuton extends PIDCommand {

    PIDController distancePIDController;
    PIDSource distanceSource;
    PIDOutput distanceOutput;

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
        distanceSource = new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
                return;
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return null;
            }

            @Override
            public double pidGet() {
                return drivetrain.getLeftPosition() / Drivetrain2017.POSITION_CONVERSION_FACTOR;
            }
        };
        distanceOutput = output -> {
            System.out.println(output);
            this.robotSpeed = output;
        };
        this.distancePIDController = new PIDController(kP, kI, kD, distanceSource, distanceOutput);
        this.distancePIDController.setOutputRange(-0.5, 0.5);
        // Our setpoint is how far we want to move, in inches
        this.distancePIDController.setSetpoint(240);
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

    @Override
    protected void usePIDOutput(double output) {
        this.drivetrain.setRight(robotSpeed - output);
        this.drivetrain.setLeft(robotSpeed + output);
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("L Pos", drivetrain.getLeftPosition());
        SmartDashboard.putNumber("L Vel", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("L Pos (in)", drivetrain.getLeftPosition()
                * Drivetrain2017.POSITION_CONVERSION_FACTOR);
        SmartDashboard.putNumber("L Vel (in∕s)", drivetrain.getLeftVelocity()
                * Drivetrain2017.VELOCITY_CONVERSION_FACTOR);
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
