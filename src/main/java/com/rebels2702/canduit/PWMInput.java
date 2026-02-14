package com.rebels2702.canduit;

import com.rebels2702.canduit.util.ByteManipulator;
/**
 * Class representing a PWM input on the CANduit device. This class allows you to read the period, frequency, high time, low time, and duty percentage of a PWM signal on a GPIO pin on the CANduit.
 */
public class PWMInput implements AutoCloseable {
    private final int GPIO;
    private final CANduit canduit;
    /**
     * Initializes a PWMInput.
     * 
     * @param canduit The CANduit to attach this PWMInput to.
     * @param gpio The GPIO pin this PWMInput is placed in.
     */
    public PWMInput(CANduit canduit, int gpio) {
        this.canduit = canduit;
        canduit.registerGPIO(gpio);
        canduit.setUpPin(gpio, 3);
        this.GPIO = gpio;
    }

    /**
     * Request the period of the PWM input.
     * 
     * @return The period in ns.
    */
    public int getPeriod() {
        byte[] data = canduit.readData(GPIO, 3, 4);
        if (data == null) {
            return 0;
        }
        return ByteManipulator.bytesToInt(data);
    }

    /**
     * Request the frequency of the PWM input.
     * 
     * @return The frequency in hz.
    */
    public int getFrequency() {
        int period = getPeriod();
        if (period <= 0) {
            return 0;
        }
        return 1000000000 / period;
    }

    /**
     * Request the high time of the PWM input.
     * 
     * @return The high time in ns.
    */
    public int getHighTime() {
        byte[] data = canduit.readData(GPIO + 10, 3, 4);
        if (data == null) {
            return 0;
        }
        return ByteManipulator.bytesToInt(data);
    }

    /**
     * Request the low time of the PWM input.
     * 
     * @return The low time in ns.
    */
    public int getLowTime() {
        byte[] data = canduit.readData(GPIO + 20, 3, 4);
        if (data == null) {
            return 0;
        }
        return ByteManipulator.bytesToInt(data);
    }

    /**
     * Request the duty percentage of the PWM input.
     * 
     * @return The duty percentage (0-100).
    */
    public int getDuty() {
        int period = getPeriod();
        if (period <= 0) {
            return 0;
        }
        return (getHighTime() * 100 + (period / 2)) / period;
    }

    /**
     * Close the PWMInput and free the GPIO pin for use by other objects.
    */
    public void close() {
        canduit.removeGPIO(GPIO);
    }
}
