package org.usfirst.frc.team4373.robot;

/**
 * A centralized class containing mappings for various constants.
 * A value of -1 means is has not yet been assigned.
 * @author aaplmath
 * @author Samasaur
 */
public class RobotMap {
    // Joystick axes and buttons
    public static final double THUMBSTICK_THRESHOLD = 0.5;
    public static final int ELEVATOR_AXIS = 1; // L stick Y
    public static final int INTAKE_AXIS = 5; // R stick Y TODO: 5 doesn't work
    public static final int INTAKE_INTAKE_BUTTON = 6; // RB
    public static final int INTAKE_RELEASE_BUTTON = 5; // RB
    public static final int INTAKE_UNFOLD_BUTTON = 12; // R thumb press
    public static final int INTAKE_FOLD_BUTTON = 11; // L thumb press
    public static final int SCALE_BUTTON = 3; // X button
    public static final int SWITCH_BUTTON = 1; // A button
    public static final int LOWER_BUTTON = 2; // B button
    public static final int CLIMB_BUTTON = 4; // Y button

    // Sensor and OI ports
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;
    public static final int GYRO_CHANNEL = 0;
    public static final int ELEVATOR_LOWER_LIMIT_SWITCH = -1; // TODO
    public static final int ELEVATOR_UPPER_LIMIT_SWITCH = 1;
    public static final int INTAKE_LOWER_LIMIT_SWITCH = 1; // TODO
    public static final int INTAKE_UPPER_LIMIT_SWITCH = 0; // TODO
    public static final int POT_CHANNEL = 1;

    // Motor ports
    public static final int LEFT_DRIVE_MOTOR_FRONT = 3;
    public static final int LEFT_DRIVE_MOTOR_REAR = 4;
    public static final int RIGHT_DRIVE_MOTOR_FRONT = 1;
    public static final int RIGHT_DRIVE_MOTOR_REAR = 2;
    public static final int MIDDLE_DRIVE_MOTOR = 5;
    public static final int ELEVATOR_MOTOR_1 = 6;
    public static final int ELEVATOR_MOTOR_2 = 8;
    public static final int INTAKE_MOTOR_1 = 7;
    public static final int INTAKE_MOTOR_2 = 9;
    public static final int WHEELED_INTAKE_MOTOR_1 = 6;
    public static final int WHEELED_INTAKE_MOTOR_2 = 7;

    // Pneumatics
    public static final int COMPRESSOR_PORT = 0;
    public static final int PCM_PORT = 15;
    public static final int GRABBER_SOLENOID_FORWARD_PORT = 2;
    public static final int GRABBER_SOLENOID_BACKWARD_PORT = 3;
    public static final int RELEASE_SOLENOID_FORWARD_PORT = 1;
    public static final int RELEASE_SOLENOID_BACKWARD_PORT = 0;

    // Miscellaneous
    public static final double AUTON_DRIVE_SPEED = 1;

    // PID constants
    public static final double DRIVETRAIN_P = 0.025;
    public static final double DRIVETRAIN_I = 0.0;
    public static final double DRIVETRAIN_D = 0.006;
    // Motor speeds
    public static final double VERTICAL_EXTENDER_SPEED = 0.2;
    public static final double WHEELED_INTAKE_SPEED = 1;

    // Dimensions - in inches
    public static final double INTAKE_HEIGHT = 51;
    public static final double ELEVATOR_HEIGHT = 38;
    public static final double VE_SAFETY_MARGIN = 1; // ve = vertical extender
    public static final double AUTON_DRIVE_DISTANCE = 125;
    public static final double AUTON_SWITCH_DISTANCE = 172;
    public static final double AUTON_SCALE_DISTANCE = -1; // TODO
}
