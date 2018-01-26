package org.usfirst.frc.team4373.robot;

/**
 * RobotMap holds various constants.
 * @author Henry Pitcairn
 */
public class RobotMap {
    // Joystick axes and buttons
    public static final int ELEVATOR_AXIS = 1;
    public static final int INTAKE_AXIS = 1;
    public static final double THUMBSTICK_THRESHOLD = 0.5;

    // Sensor ports
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;
    public static final int GYRO_CHANNEL = 0;
    public static final int ELEVATOR_LOWER_LIMIT_SWITCH = 1;
    public static final int ELEVATOR_UPPER_LIMIT_SWITCH = 1;
    public static final int INTAKE_LOWER_LIMIT_SWITCH = 1;
    public static final int INTAKE_UPPER_LIMIT_SWITCH = 1;

    // Motor ports
    public static final int LEFT_DRIVE_MOTOR_1 = 0;
    public static final int LEFT_DRIVE_MOTOR_2 = 1;
    public static final int RIGHT_DRIVE_MOTOR_1 = 2;
    public static final int RIGHT_DRIVE_MOTOR_2 = 3;
    public static final int ELEVATOR_MOTOR = 4;
    public static final int INTAKE_MOTOR = 5;

    // Pneumatics

    // Miscellaneous
    public static final double ELEVATOR_SPEED = 0.5;
    public static final double INTAKE_SPEED = 0.5;
}
