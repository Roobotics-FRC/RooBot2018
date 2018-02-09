package org.usfirst.frc.team4373.robot;

/**
 * RobotMap holds various constants.
 * @author Henry Pitcairn
 */
public class RobotMap {
    // Joystick axes and buttons
    public static final double THUMBSTICK_THRESHOLD = 0.5;
    public static final int ELEVATOR_AXIS = 1; // L stick Y
    public static final int INTAKE_AXIS = 5; // R stick Y
    public static final int INTAKE_INTAKE_BUTTON = 6; // RB
    public static final int INTAKE_RELEASE_BUTTON = 5; // RB
    public static final int INTAKE_UNFOLD_BUTTON = 12; // R thumb press
    public static final int INTAKE_FOLD_BUTTON = 11; // L thumb press
    public static final int SCALE_BUTTON = 3; // X button
    public static final int SWITCH_BUTTON = 1; // A button
    public static final int LOWER_BUTTON = 2; // B button
    public static final int CLIMB_BUTTON = 4; // Y button


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
    public static final int WHEELED_INTAKE_MOTOR_1 = 6;
    public static final int WHEELED_INTAKE_MOTOR_2 = 7;

    // Pneumatics
    public static final int COMPRESSOR_PORT = 0;
    public static final int PCM_PORT = 15;
    public static final int GRABBER_SOLENOID_FORWARD_PORT = 1;
    public static final int GRABBER_SOLENOID_BACKWARD_PORT = 0;
    public static final int RELEASE_SOLENOID_FORWARD_PORT = 3;
    public static final int RELEASE_SOLENOID_BACKWARD_PORT = 2;

    // Motor speeds
    public static final double VERTICAL_EXTENDER_SPEED = 0.5;
    public static final double WHEELED_INTAKE_SPEED = 1;
}
