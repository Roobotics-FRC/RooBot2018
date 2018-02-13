package org.usfirst.frc.team4373.robot;

/**
 * RobotMap holds various constants.
 * @author Henry Pitcairn
 */
public class RobotMap {
    // Joystick axes and buttons

    // Sensor ports
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;
    public static final int GYRO_CHANNEL = 0;
    public static final int LOWER_LIMIT_SWITCH = 1;
    public static final int UPPER_LIMIT_SWITCH = 1;

    // Motor ports
    public static final int LEFT_DRIVE_MOTOR_FRONT = 3;
    public static final int LEFT_DRIVE_MOTOR_REAR = 4;
    public static final int RIGHT_DRIVE_MOTOR_FRONT = 1;
    public static final int RIGHT_DRIVE_MOTOR_REAR = 2;
    public static final int MIDDLE_DRIVE_MOTOR_LEFT = 6;
    public static final int MIDDLE_DRIVE_MOTOR_RIGHT = 5;
    public static final int ELEVATOR_MOTOR = 0; // TODO

    // Pneumatics

    // Miscellaneous
    public static final double AUTON_DRIVE_SPEED = 1;

    // PID constants
    public static final double DRIVETRAIN_P = 0.025;
    public static final double DRIVETRAIN_I = 0.0;
    public static final double DRIVETRAIN_D = 0.006;
}
