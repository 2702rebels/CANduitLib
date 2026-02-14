package com.rebels2702.canduit;
/**
 * Class representing a digital input on the CANduit device. This class allows you to read the status of a digital input pin on the CANduit.
 */
public class DigitalInput implements AutoCloseable {
    private final int GPIO;
    private final CANduit canduit;
    /**
     * Initializes a DigitalInput.
     * 
     * @param canduit The CANduit to attach this DigitalInput to.
     * @param gpio The GPIO pin this DigitalInput is placed in.
     */
    public DigitalInput(CANduit canduit, int gpio) {
        this.canduit = canduit;
        canduit.registerGPIO(gpio);
        canduit.setUpPin(gpio, 1);
        this.GPIO = gpio;
    }

    /**
     * Request the status of the Digital input.
     * 
     * @return The status of the input.
    */
    public Boolean get() {
        byte[] data = canduit.readData(GPIO, 2, 1);
        if (data == null) {
            return false;
        }
        return (data[0] & 0b00000001) == 1;
    }

    /**
     * Close the DigitalInput and free the GPIO pin for use by other objects.
    */
    public void close() {
        canduit.removeGPIO(GPIO);
    }
}
