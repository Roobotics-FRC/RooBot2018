package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.DefaultCommand;
import org.usfirst.frc.team4373.robot.RobotMap;

/**
 * The intake mechanism for managing cubes. This is on the outside and can handle cubes with its piston
 *
 * @author aaplmath
 * @author Samasaur
 */
public class Intake extends VerticalExtender {

    private DoubleSolenoid intakePiston;
    private Compressor compressor;

    private static Intake instance;

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    private Intake() {
        super("Intake");
        this.motor = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR);
        this.bottomSwitch = new DigitalInput(RobotMap.INTAKE_LOWER_LIMIT_SWITCH);
        this.topSwitch = new DigitalInput(RobotMap.INTAKE_UPPER_LIMIT_SWITCH);

        this.configureMotor();

        this.compressor = new Compressor(RobotMap.COMPRESSOR_PORT);
        this.intakePiston = new DoubleSolenoid(RobotMap.PCM_PORT, RobotMap.SOLENOID_BACKWARD_PORT,
                RobotMap.SOLENOID_FORWARD_PORT);
    }

    public void retainCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kReverse);
        SmartDashboard.putString("Intake State", "Close / Off");
    }

    public void releaseCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kForward);
        SmartDashboard.putString("Intake State", "Open / On");
    }

    public void setNeutral() {
        this.intakePiston.set(DoubleSolenoid.Value.kOff);
        SmartDashboard.putString("Intake State", "Neutral / Neutral");
    }

    public void startCompressor() {
        this.compressor.start();
        SmartDashboard.putBoolean("Compressor On", true);
    }

    public void stopCompressor() {
        this.compressor.stop();
        SmartDashboard.putBoolean("Compressor On", false);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(DefaultCommand.getInstance());
    }

    @Override
    public void set(double power) {
        super.set(power);
        SmartDashboard.putNumber("Intake Motor Power", power);
    }
}