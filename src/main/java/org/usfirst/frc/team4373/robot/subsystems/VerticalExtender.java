package org.usfirst.frc.team4373.robot.subsystems;

import static org.usfirst.frc.team4373.robot.Motors.safetyCheckSpeed;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.Motors;

/**
 * The basis for all components that extend vertically on the robot.
 *
 * @author aaplmath
 * @author Samasaur
 */
public abstract class VerticalExtender extends Subsystem {

    protected WPI_TalonSRX motor;
    protected DigitalInput bottomSwitch;
    protected DigitalInput topSwitch;

    protected VerticalExtender(String name) {
        super(name);
    }

    /**
     * The position of the VerticalExtender when instantiated. Assumed to be 0 vertically (on the
     * ground).
     */
    protected double initialPosition;

    /**
     * Puts motor in brake mode, sets up encoder, and establishes initial position
     * for future fetching of relative position.
     */
    protected void configureMotor() {
        this.motor.setNeutralMode(NeutralMode.Brake);
        this.motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.motor.setSensorPhase(false);
        this.initialPosition = this.motor.getSelectedSensorPosition(0)
                * Motors.POSITION_CONVERSION_FACTOR;
    }

    /**
     * Sets the elevator motor to the specified power.
     * Positive values will make the elevator ascend.
     *
     * @param power The power, from -1 to 1, to which to set the motor.
     *              This value is safety checked to ensure it is not out of this range.
     */
    public void set(double power) {
        power = safetyCheckSpeed(power);
        if (power > 0) {
            if (atTop()) {
                power = 0;
            }
        } else {
            if (atBottom()) {
                power = 0;
            }
        }
        this.motor.set(power);
    }

    /**
     * Gets the position of the elevator.
     * @return The position of the elevator, in inches.
     */
    public double getPosition() {
        return this.motor.getSelectedSensorPosition(0) * Motors.POSITION_CONVERSION_FACTOR;
    }

    /**
     * Gets the position of the intake relative to its initial position. This should be the
     * position above the ground.
     * @return The relative position of the intake, in inches.
     */
    public double getRelativePosition() {
        return (this.getPosition() - this.initialPosition) * Motors.POSITION_CONVERSION_FACTOR;
    }

    /**
     * Gets the velocity of the elevator.
     * @return the velocity of the elevator, in inches/sec.
     */
    public double getVelocity() {
        return this.motor.getSelectedSensorVelocity(0) * Motors.VELOCITY_CONVERSION_FACTOR;
    }

    /**
     * Detects whether the elevator has reached the bottom of its track from limit switch output.
     * If this returns true, the motor should <b>not</b> be set to a negative power.
     * @return a boolean describing whether the elevator has reached its bottom position.
     */
    public boolean atBottom() {
        return bottomSwitch.get();
    }

    /**
     * Detects whether the elevator has reached the top of its track from limit switch output.
     * If this returns true, the motor should <b>not</b> be set to a positive power.
     * @return a boolean describing whether the elevator has reached its top position.
     */
    public boolean atTop() {
        return topSwitch.get();
    }
}