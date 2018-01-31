package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * OI encapsulates various inputs and outputs.
 *
 * @author aaplmath
 * @author Henry Pitcairn
 */
public class OI {
    private static OI oi = null;
    private Gyro gyro;

    private OI() {
        this.gyro = new AnalogGyro(RobotMap.GYRO_CHANNEL);
    }

    /**
     * The getter for the OI singleton.
     *
     * @return The static OI singleton object.
     */
    public static OI getOI() {
        if (oi == null) {
            synchronized (OI.class) {
                if (oi == null) {
                    oi = new OI();
                }
            }
        }
        return oi;
    }

    public Gyro getGyro() {
        return gyro;
    }

    /**
     * Gets the gyro angle in degrees.
     *
     * @return The gyro angle in degrees, -180 to 180.
     */
    public double getAngleRelative() {
        double angle = getGyro().getAngle();
        double relative = (Math.abs(angle) * 9 / 2) % 180;
        // TODO: Account for 180° boundary case
        relative *= Math.signum(angle);
        return relative;
    }

    /**
     * Gets the gyro angle in native units.
     *
     * @return The gyro angle, where 20 units = 90 degrees.
     */
    public double getAngleAbsolute() {
        return getGyro().getAngle();
    }
}
