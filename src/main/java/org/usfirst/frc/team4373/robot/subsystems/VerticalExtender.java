package org.usfirst.frc.team4373.robot.subsystems;

import static org.usfirst.frc.team4373.robot.input.hid.Motors.safetyCheckSpeed;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.Motors;



/**
 * A class that  serves as the basis for all VerticalExtenders on the bot.
 *
 * <p>This class cannot be instantiated; its subclasses must be singletons. A "vertical extender"
 * is something that extends vertically on a track. Another word for it would be an "elevator."
 * A VerticalExtender has a single-motor system, including an encoder and limit switches. Setting
 * power to the motors is checked both for too much power and against the limit switches.
 *
 * @author aaplmath
 */
public abstract class VerticalExtender extends Subsystem {

    private static final double LOCKING_THRESHOLD = 0.1;
    protected WPI_TalonSRX motor1;
    protected WPI_TalonSRX motor2;
    protected DigitalInput bottomSwitch;
    protected DigitalInput topSwitch;
    protected double extenderBottom;
    protected double extenderTop;
    protected LockingType locking;
    /**
     * The position of the VerticalExtender when instantiated. Assumed to be 0 vertically (on the
     * ground).
     */
    protected double initialPosition;

    protected VerticalExtender(String name, double bottom, double top) {
        super(name);
        this.extenderBottom = bottom;
        this.extenderTop = top;
        this.disableLocking();
    }

    protected double applyLocking(double power) {
        if (power <= 0.01 && power >= -0.01) {
            return this.locking == LockingType.UP
                    ? LOCKING_THRESHOLD :
                    (LockingType.DOWN == this.locking ? -LOCKING_THRESHOLD : 0);
        } else {
            return power;
        }
    }

    /**
     * Puts motor in brake mode, sets up encoder, and establishes initial position
     * for future fetching of relative position.
     */
    protected void configureMotors() {
        this.motor1.setNeutralMode(NeutralMode.Brake);
        this.motor2.setNeutralMode(NeutralMode.Brake);
        this.initialPosition = this.getPosition(); // FIX 3/10/18: avoid double conversions!
    }

    /**
     * Sets the elevator motor to the specified power.
     * Positive values will make the elevator ascend.
     *
     * @param power The power, from -1 to 1, to which to set the motor.
     *              This value is safety checked to ensure it is not out of this range.
     */
    public void set(double power) {
        power = -power;
        if (power < 0 && atTop()) {
            power = 0;
        } else if (power > 0 && atBottom()) {
            power = 0;
        }
        power = safetyCheckSpeed(applyLocking(power));
        this.motor1.set(power);
        this.motor2.set(power);
        SmartDashboard.putNumber(this.getName() + " Power", power);
    }

    /**
     * Gets the position of the elevator.
     *
     * @return The position of the elevator, in inches.
     */
    public double getPosition() {
        return this.motor1.getSelectedSensorPosition(0) * Motors.POSITION_CONVERSION_FACTOR;
    }

    /**
     * Gets the position of the intake relative to its initial position. This should be the
     * position above the ground.
     *
     * @return The relative position of the intake, in inches.
     */
    public double getRelativePosition() {
        return (this.getPosition() - this.initialPosition);
    }

    /**
     * Gets the velocity of the elevator.
     *
     * @return the velocity of the elevator, in in/sec.
     */
    public double getVelocity() {
        return this.motor1.getSelectedSensorVelocity(0) * Motors.VELOCITY_CONVERSION_FACTOR;
    }

    /**
     * Detects whether the elevator has reached the bottom of its track from limit switch output.
     * If this returns true, the motor should <b>not</b> be set to a negative power.
     *
     * @return a boolean describing whether the elevator has reached its bottom position.
     */
    private boolean atBottom() {
        return this.getRelativePosition() - this.extenderBottom < RobotMap.VE_SAFETY_MARGIN;
    }

    /**
     * Detects whether the elevator has reached the top of its track from limit switch output.
     * If this returns true, the motor should <b>not</b> be set to a positive power.
     *
     * @return a boolean describing whether the elevator has reached its top position.
     */
    private boolean atTop() {
        return this.extenderTop - this.getRelativePosition() < RobotMap.VE_SAFETY_MARGIN;
    }

    public void enableUpwardsLocking() {
        this.locking = LockingType.UP;
    }

    public void enableDownwardsLocking() {
        this.locking = LockingType.DOWN;
    }

    public void disableLocking() {
        this.locking = LockingType.NONE;
    }

    enum LockingType {
        NONE, UP, DOWN
    }
}
