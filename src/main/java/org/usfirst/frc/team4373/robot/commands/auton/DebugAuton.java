package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An auton that helps with debugging.
 *
 * @author Samasaur
 */
public class DebugAuton extends Command {

    private boolean executed;
    private String message;

    /**
     * Creates a DebugAuton with a message.
     * @param message The message to print.
     */
    public DebugAuton(String message) {
        this.message = message;
        System.out.println("Debug Auton Constructor Called - Message:");
        System.out.println(this.message);
        System.out.println();
        System.out.println("Time (ms): " + System.currentTimeMillis());
        System.out.println();
        this.setTimeout(0.1);
    }

    /**
     * Creates a DebugAuton that requires subsystems.
     * @param message The message to print.
     * @param systemsToRequire The subsystems to require.
     */
    public DebugAuton(String message, Subsystem[] systemsToRequire) {
        this.message = message;
        System.out.println("Debug Auton Constructor Called - Message:");
        System.out.println(this.message);
        System.out.println();
        System.out.println("Requiring:");
        for (Subsystem system : systemsToRequire) {
            requires(system);
            System.out.println(system.getName());
        }
        System.out.println();
        System.out.println("Time (ms): " + System.currentTimeMillis());
        System.out.println();
        this.setTimeout(0.1);
    }

    @Override
    protected void initialize() {
        executed = false;
        System.out.println("Debug Auton Initialized - Message:");
        System.out.println(message);
        System.out.println();
        System.out.println("Time (ms): " + System.currentTimeMillis());
        System.out.println();
    }

    @Override
    protected void execute() {
        System.out.println("Debug Auton Executing - Message:");
        System.out.println(message);
        System.out.println();
        System.out.println("Time (ms): " + System.currentTimeMillis());
        System.out.println();
        executed = true;
    }

    @Override
    protected boolean isFinished() {
        System.out.println("Debug Auton Checking isFinished - Message:");
        System.out.println(message);
        System.out.println();
        System.out.println("Value: " + executed);
        System.out.println();
        System.out.println("Time (ms): " + System.currentTimeMillis());
        System.out.println();
        return executed;
    }

    @Override
    protected void end() {
        System.out.println("Debug Auton Ending - Message:");
        System.out.println(message);
        System.out.println();
        System.out.println("Time (ms): " + System.currentTimeMillis());
        System.out.println();
    }

    @Override
    protected void interrupted() {
        System.out.println("Debug Auton Interrupted - Message:");
        System.out.println(message);
        System.out.println();
        System.out.println("Time (ms): " + System.currentTimeMillis());
        System.out.println();
    }
}
