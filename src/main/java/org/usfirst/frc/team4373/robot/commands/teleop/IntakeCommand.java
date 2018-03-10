package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * This command lets the operator joystick control the intake VerticalExtender.
 *
 * @author aaplmath
 * @author Samasaur
 */
public class IntakeCommand extends Command {

    private Intake intake;

    public IntakeCommand() {
        requires(this.intake = Intake.getInstance());
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if (OI.getOI().getOperatorJoystick().getPOV() != -1) {
            switch (OI.getOI().getOperatorJoystick().getPOV()) {
                case 315:
                case 0:
                case 45:
                    this.intake.set(RobotMap.VERTICAL_EXTENDER_SPEED);
                    break;
                case 135:
                case 180:
                case 225:
                    this.intake.set(-RobotMap.VERTICAL_EXTENDER_SPEED);
                    break;
                case 90:
                case 270:
                default:
                    this.intake.set(0);
                    break;
            }
        } else {
            this.intake.set(0);
        }
        if (OI.getOI().getOperatorJoystick().getRawButton(RobotMap.INTAKE_OPEN_BUTTON)) {
            this.intake.releaseCube();
        } else {
            this.intake.retainCube();
        }

        if (OI.getOI().getOperatorJoystick().getRawButton(RobotMap.INTAKE_RETRACT_BUTTON)) {
            this.intake.retractIntake();
        } else {
            this.intake.releaseIntake();
        }

        // Logging
        SmartDashboard.putNumber("Intake Pos (in)", intake.getRelativePosition());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.intake.set(0);
        // this.intake.neutralizePiston();
        // this.intake.neutralizeRelease();
        // this.intake.stopCompressor();
    }

    @Override
    protected void interrupted() {
        this.intake.set(0);
    }
}
