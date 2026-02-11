package canduit;
/**
 * Class representing a digital output on the CANduit device.
 */
public class DigitalOutput implements AutoCloseable {
    private final int GPIO;
    private final CANduit canduit;
    /**
     * Initializes a DigitalOutput.
     * 
     * @param canduit The CANduit to attach this DigitalOutput to.
     * @param gpio The GPIO pin this DigitalOutput is placed in.
     */
    public DigitalOutput(CANduit canduit, int gpio) {
        this.canduit = canduit;
        canduit.registerGPIO(gpio);
        canduit.setUpPin(gpio, 2);
        this.GPIO = gpio;
    }

    /**
     * Set the status of the Digital input.
     * @param value The status to set the output to.
    */
    public void set(Boolean value) {
        byte[] data = {(byte) (value ? 0b00000001 : 0b00000000)};
        canduit.writeData(GPIO, 2, data);
    }

    /**
     * Close the DigitalOutput and free the GPIO pin for use by other objects.
    */
    public void close() {
        canduit.removeGPIO(GPIO);
    }
}
