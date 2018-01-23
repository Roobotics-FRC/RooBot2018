package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;

public class Drivetrain extends Subsystem {

    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;

    // Conversion factors to inches or inches/second
    // wheels are 6 inches in diameter, 4096 units = 1 revolution, velocity is in units/0.1sec
    /**
     * When a double is multiplied by this constant, it is converted from 'units' to inches.
     */
    public static final double POSITION_CONVERSION_FACTOR = 6 * Math.PI / 4096;
    /**
     * When a double is multiplied by this constant, it is converted from 'units'/0.1s to inches/s.
     */
    public static final double VELOCITY_CONVERSION_FACTOR = 10 * 6 * Math.PI / 4096;

    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        return instance == null ? instance = new Drivetrain() : instance;
    }

    private Drivetrain() {
        this.left1 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_1);
        this.left2 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_2);
        this.right1 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_1);
        this.right2 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_2);

        this.left1.setNeutralMode(NeutralMode.Brake);
        this.left2.setNeutralMode(NeutralMode.Brake);
        this.right1.setNeutralMode(NeutralMode.Brake);
        this.right2.setNeutralMode(NeutralMode.Brake);

        this.left2.follow(left1);
        this.right2.follow(right1);

        this.right1.setInverted(true);
        this.right2.setInverted(true);

        this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.left1.setSensorPhase(false);
        this.right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.right1.setSensorPhase(false);
    }

    public void setLeft(double power) {
        power = safetyCheckSpeed(power);
        this.left1.set(power);
    }

    public void setRight(double power) {
        power = safetyCheckSpeed(power);
        this.right1.set(power);
    }

    public void setBoth(double power) {
        this.setLeft(power);
        this.setRight(power);
    }

    public int getLeftPosition() {
        return left1.getSelectedSensorPosition(0);
    }

    public int getLeftVelocity() {
        return left1.getSelectedSensorVelocity(0);
    }

    public int getRightPosition() {
        return right1.getSelectedSensorPosition(0);
    }

    public int getRightVelocity() {
        return right1.getSelectedSensorVelocity(0);
    }

    private double safetyCheckSpeed(double power) {
        if (power > 1) {
            return 1;
        } else if (power < -1) {
            return -1;
        }
        return power;
    }

    @Override
    protected void initDefaultCommand() {

    }
}
