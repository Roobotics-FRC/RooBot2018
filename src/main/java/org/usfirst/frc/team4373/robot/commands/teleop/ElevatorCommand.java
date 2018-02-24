package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.Motors;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;

/**
 * This command lets the operator joystick control the elevator VerticalExtender.
 *
 * @author aaplmath
 */
public class ElevatorCommand extends Command {

    private Elevator elevator;

    public ElevatorCommand() {
        requires(this.elevator = Elevator.getInstance());
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double axis = OI.getOI().getOperatorJoystick().getAxis(RobotMap.ELEVATOR_AXIS);
        if (Math.abs(axis) > RobotMap.THUMBSTICK_THRESHOLD) {
            this.elevator.set(Math.signum(axis) * RobotMap.VERTICAL_EXTENDER_SPEED);
        } else {
            this.elevator.set(0);
        }

        // Logging
        //SmartDashboard.putNumber("Elevator Pos (in)", elevator.getRelativePosition());
        //SmartDashboard.putNumber("Elevator Pos Abs", elevator.getPosition());
        //SmartDashboard.putNumber("Elevator Pos Abs (in)", elevator.getPosition()
        //        * Motors.POSITION_CONVERSION_FACTOR);
        //SmartDashboard.putNumber("Elevator Vel", elevator.getVelocity());
        //SmartDashboard.putNumber("Elevator Vel (in p s)", elevator.getVelocity()
        //        * Motors.VELOCITY_CONVERSION_FACTOR);

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.elevator.set(0);
    }

    @Override
    protected void interrupted() {
        this.elevator.set(0);
    }
}
