package frc.robot.libs.canduit;

import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Timer;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * The main class for the CANduit library. This class manages the CAN communication and GPIO pin allocation for the CANduit device.
 */
public class CANduit {
    private Set<Integer> allocatedChannels = ConcurrentHashMap.newKeySet();
    private final int deviceID;
    private final CAN can;
    private final CANData data = new CANData();
    /**
     * Initializes a CANduit device.
     * 
     * @param deviceID The CAN ID of the CANduit device.
     */
    public CANduit(int deviceID) {
        this.deviceID = deviceID;
        this.can = new CAN(deviceID);
    }

    /**
     * Get the CAN device ID of the CANduit.
     * @return The ID of the device.
    */
    public int getDeviceID() {
        return deviceID;
    }

    void registerGPIO(int gpio) {
        if (!allocatedChannels.add(gpio)) {
            throw new IllegalArgumentException("GPIO pin " + gpio + " in use.");
        }
    }

    void removeGPIO(int gpio) {
        allocatedChannels.remove(gpio);
        setUpPin(gpio, 0);
    }

    void setUpPin(int gpio, int mode) {
        int apiClass = 1;
        int apiIndex = gpio;
        int apiId = apiClass << 4 | apiIndex;
        byte[] data = {(byte) mode};
        can.writePacket(data, apiId);
    }

    byte[] readData(int gpio, int apiClass, int length) {
        int apiIndex = gpio;
        int apiId = apiClass << 4 | apiIndex;
        // System.out.println("Reading data");
        // System.out.println("Length: " + length);
        while (can.readPacketNew(apiId, data)) {

        }
        can.writeRTRFrame(length, apiId);
        // Timer.delay(0.005);
        
        Timer packetTimer = new Timer();
        packetTimer.start();

        while (!can.readPacketNew(apiId, data)) {
            if (packetTimer.hasElapsed(0.01)) {
                System.out.println("Timed out waiting for packet with API class " + apiClass + " and GPIO " + gpio);
                packetTimer.stop();
                return null;
            }
        }
        packetTimer.stop();
        System.out.println("Data read for GPIO " + gpio + ", API class " + apiClass + ": " + data.data[0]);
        return data.data;
    }
        // }

    void writeData(int apiIndex, int apiClass, byte[] data) {
        int apiId = apiClass << 4 | apiIndex;
        can.writePacket(data, apiId);
    }
}