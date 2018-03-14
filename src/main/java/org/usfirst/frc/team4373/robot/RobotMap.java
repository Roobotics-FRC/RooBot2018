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
    public static final int INTAKE_OPEN_BUTTON = 6; // RB
    public static final int INTAKE_RETRACT_BUTTON = 5; // LB
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
    public static final double AUTON_DRIVE_SPEED = 0.5;

    // PID constants
    public static final double DRIVETRAIN_P = 0.03;
    public static final double DRIVETRAIN_I = 0.0;
    public static final double DRIVETRAIN_D = 0.003;
    public static final double ELEVATOR_P = 0.01; //TODO: Tune
    public static final double ELEVATOR_I = 0.001; //TODO:   /\
    public static final double ELEVATOR_D = 0.005; //TODO    |
    public static final double INTAKE_P = 0.01; //TODO       |
    public static final double INTAKE_I = 0.001; //TODO      |
    public static final double INTAKE_D = 0.005; //TODO      |

    // Motor speeds
    public static double ELEVATOR_SPEED = 0.7; // TODO: See below \/
    public static double INTAKE_SPEED = 0.85;
    /* This needs to be different for elevator and intake

     */
    public static final double WHEELED_INTAKE_SPEED = 1;

    // Dimensions - in inches
    public static final double INTAKE_HEIGHT = 51;
    public static final double ELEVATOR_HEIGHT = 38;
    public static final double VE_SAFETY_MARGIN = 1; // ve = vertical extender
    public static final double AUTON_DRIVE_DISTANCE = 125;
    public static final double AUTON_SWITCH_DISTANCE = 172;
    public static final double AUTON_SCALE_DISTANCE = -1; // TODO

    // More dimensions
    public static final double INTAKE_SAFE_TOP = 100;   // TODO measure
    public static final double INTAKE_SAFE_BOTTOM = 0;
    public static final double ELEVATOR_SAFE_TOP = 100;
    public static final double ELEVATOR_SAFE_BOTTOM = 0;
}
