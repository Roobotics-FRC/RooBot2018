package org.usfirst.frc.team4373.robot.subsystems;

import static org.usfirst.frc.team4373.robot.Motors.safetyCheckSpeed;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.DefaultCommand;
import org.usfirst.frc.team4373.robot.RobotMap;

/**
 * The Drivetrain for the robot. This controls the movement of the bot. Each motor can be set individually.
 *
 * @author aaplmath
 * @author Samasaur
 */
public class Drivetrain extends Subsystem {

    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;
    private WPI_TalonSRX middle;

    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        return instance == null ? instance = new Drivetrain() : instance;
    }

    private Drivetrain() {
        this.left1 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_1);
        this.left2 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_2);
        this.right1 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_1);
        this.right2 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_2);
        this.middle = new WPI_TalonSRX(RobotMap.MIDDLE_DRIVE_MOTOR);

        this.left1.setNeutralMode(NeutralMode.Brake);
        this.left2.setNeutralMode(NeutralMode.Brake);
        this.right1.setNeutralMode(NeutralMode.Brake);
        this.right2.setNeutralMode(NeutralMode.Brake);
        this.middle.setNeutralMode(NeutralMode.Brake);

        this.right1.setInverted(true);
        this.right2.setInverted(true);
        this.middle.setInverted(true);

        this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.left1.setSensorPhase(false);
        this.right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.right1.setSensorPhase(false);
        this.middle.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.middle.setSensorPhase(false);
    }

    /**
     * Set the first left wheel to a safety-checked value.
     * @param power The power to set it to.
     */
    public void setLeft1(double power) {
        power = safetyCheckSpeed(power);
        this.left1.set(power);
        SmartDashboard.putNumber("Left 1 Power", power);
    }

    /**
     * Set the second left wheel to a safety-checked value.
     * @param power The power to set it to.
     */
    public void setLeft2(double power) {
        power = safetyCheckSpeed(power);
        this.left2.set(power);
        SmartDashboard.putNumber("Left 2 Power", power);
    }

    /**
     * Set the first right wheel to a safety-checked value.
     * @param power The power to set it to.
     */
    public void setRight1(double power) {
        power = safetyCheckSpeed(power);
        this.right1.set(power);
        SmartDashboard.putNumber("Right 1 Power", power);
    }

    /**
     * Set the second right wheel to a safety-checked value.
     * @param power The power to set it to.
     */
    public void setRight2(double power) {
        power = safetyCheckSpeed(power);
        this.right2.set(power);
        SmartDashboard.putNumber("Right 2 Power", power);
    }

    /**
     * Set the middle wheel to a safety-checked value.
     * @param power The power to set it to.
     */
    public void setMiddle(double power) {
        power = safetyCheckSpeed(power);
        this.middle.set(power);
        SmartDashboard.putNumber("Middle Power", power);
    }

    /**
     * Set the left wheels to a safety-checked value.
     * @param power The power to set them to.
     */
    public void setLeft(double power) {
        this.setLeft1(power);
        this.setLeft2(power);
    }

    /**
     * Set the right wheels to a safety-checked value.
     * @param power The power to set them to.
     */
    public void setRight(double power) {
        this.setRight1(power);
        this.setRight2(power);
    }

    /**
     * Set all wheels to a safety-checked value.
     * @param power The power to set them to.
     */
    public void setAll(double power) {
        this.setLeft(power);
        this.setRight(power);
        this.setMiddle(power);
    }

    /**
     * Gets the position of the left wheels in units.
     * @return The position of the left wheels, in 'units'.
     */
    public int getLeftPosition() {
        return left1.getSelectedSensorPosition(0);
    }

    /**
     * Gets the velocity of the left wheels in units/0.1s.
     * @return The velocity of the left wheels, in 'units'/0.1s.
     */
    public int getLeftVelocity() {
        return left1.getSelectedSensorVelocity(0);
    }

    /**
     * Gets the position of the right wheels in units.
     * @return The position of the right wheels, in 'units'.
     */
    public int getRightPosition() {
        return right1.getSelectedSensorPosition(0);
    }

    /**
     * Gets the velocity of the right wheels in units/0.1s.
     * @return The velocity of the right wheels, in 'units'/0.1s.
     */
    public int getRightVelocity() {
        return right1.getSelectedSensorVelocity(0);
    }

    /**
     * Gets the position of the middle wheel in units.
     * @return The position of the middle wheel, in 'units'.
     */
    public int getMiddlePosition() {
        return middle.getSelectedSensorPosition(0);
    }

    /**
     * Gets the velocity of the middle wheel in units/0.1s.
     * @return The velocity of the middle wheel, in 'units'/0.1s.
     */
    public int getMiddleVelocity() {
        return middle.getSelectedSensorVelocity(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(DefaultCommand.getInstance());
    }
}
