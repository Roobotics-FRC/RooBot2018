package org.usfirst.frc.team4373.robot.subsystems;

import static org.usfirst.frc.team4373.robot.input.hid.Motors.safetyCheckSpeed;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;

public class Elevator extends Subsystem {

    private WPI_TalonSRX motor;
    private DigitalInput bottomSwitch;
    private DigitalInput topSwitch;

    private static Elevator instance;

    public static Elevator getInstance() {
        return instance == null ? instance = new Elevator() : instance;
    }

    private Elevator() {
        this.motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR);
        this.bottomSwitch = new DigitalInput(RobotMap.LOWER_LIMIT_SWITCH);
        this.topSwitch = new DigitalInput(RobotMap.UPPER_LIMIT_SWITCH);

        this.motor.setNeutralMode(NeutralMode.Brake);
        this.motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.motor.setSensorPhase(false);
    }

    /**
     * Sets the elevator motor to the specified power.
     * Positive values will make the elevator ascend.
     *
     * @param power The power, from -1 to 1, to which to set the motor.
     *              This value is safety checked to make sure it is not out of this range.
     */
    public void set(double power) {
        power = safetyCheckSpeed(power);
        this.motor.set(power);
    }

    /**
     * Gets the position of the elevator.
     * @return the position of the elevator, in 'units'.
     */
    public double getPosition() {
        return motor.getSelectedSensorPosition(0);
    }

    /**
     * Gets the velocity of the elevator.
     * @return the velocity of the elevator, in 'units'.
     */
    public double getVelocity() {
        return motor.getSelectedSensorVelocity(0);
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

    @Override
    protected void initDefaultCommand() {

    }
}
