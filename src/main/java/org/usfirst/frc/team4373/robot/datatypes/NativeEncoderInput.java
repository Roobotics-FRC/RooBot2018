package org.usfirst.frc.team4373.robot.datatypes;

/**
 * Created by derros on 3/10/18.
 */
public class NativeEncoderInput extends EncoderInput {
    /**
     * When a double is multiplied by this constant, it is converted from 'units' to inches.
     */
    public static final double POSITION_CONVERSION_FACTOR = 6 * Math.PI / 4096;

    public NativeEncoderInput(double val) {
        super(val);
    }

    @Override
    public double toInches() {
        return val * POSITION_CONVERSION_FACTOR;
    }

    @Override
    public double fromInches(double val) {
        return val / POSITION_CONVERSION_FACTOR;
    }
}
