package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.IntakeCommand;

/**
 * A programmatic representation of the intake VerticalExtender. This is the innermost,
 * (supposedly) faster one.
 *
 * @author aaplmath
 */
public class Intake extends VerticalExtender {

    private static Intake instance;
    private DoubleSolenoid intakePiston;
    private DoubleSolenoid releasePiston;
    private Compressor compressor;
    private Potentiometer pot;

    private Intake() {
        super("Intake", RobotMap.INTAKE_SAFE_BOTTOM, RobotMap.INTAKE_SAFE_TOP); // 18, 42
        this.motor1 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_1);
        this.motor2 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_2);

        this.configureMotors();
        this.motor1.setInverted(true);

        this.compressor = new Compressor(RobotMap.COMPRESSOR_PORT);
        this.pot = new AnalogPotentiometer(RobotMap.POT_CHANNEL, 47, 0);
        this.releasePiston = new DoubleSolenoid(RobotMap.PCM_PORT,
                RobotMap.GRABBER_SOLENOID_FORWARD_PORT, RobotMap.GRABBER_SOLENOID_BACKWARD_PORT);
        this.intakePiston = new DoubleSolenoid(RobotMap.PCM_PORT,
                RobotMap.RELEASE_SOLENOID_FORWARD_PORT, RobotMap.RELEASE_SOLENOID_BACKWARD_PORT);
    }

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    @Override
    public double getRelativePosition() {
        return this.pot.get();
    }

    @Override
    public double getPosition() {
        return this.pot.get();
    }

    public void retractIntake() {
        this.releasePiston.set(DoubleSolenoid.Value.kForward);
    }

    public void releaseIntake() {
        this.releasePiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void neutralizeRelease() {
        this.releasePiston.set(DoubleSolenoid.Value.kOff);
    }

    public void retainCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void releaseCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kForward);
    }

    public void neutralizeGrabberPiston() {
        this.intakePiston.set(DoubleSolenoid.Value.kOff);
    }

    public void startCompressor() {
        this.compressor.start();
    }

    public void stopCompressor() {
        this.compressor.stop();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new IntakeCommand());
    }
}
