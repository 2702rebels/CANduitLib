package com.rebels2702.canduit;

import com.rebels2702.canduit.util.ByteManipulator;

import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj.CAN;


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

    /** 
     * Sends an RTR frame to the CANduit and expects a response
     * @param apiIndex The apiIndex of the identifier to request to
     * @param apiClass The apiClass of the identifier to request to
     * @param length The length of the data to recieve

     */
    byte[] readData(int apiIndex, int apiClass, int length){
        int apiId = apiClass << 4 | apiIndex;
        can.writeRTRFrame(length, apiId);
        boolean success = can.readPacketTimeout(apiId, 100, data);

        if (success) {
            return data.data;
        }
        
        return null;
    }

    /**
     * If available, consumes a specified data frame from the CANBus
     * @param apiIndex The apiIndex of the identifier to consume
     * @param apiClass The apiClass of the identifier to consume
     * @param length The length of the data to recieve
     */
    byte[] getPacket(int apiIndex, int apiClass, int length) {
        int apiId = apiClass << 4 | apiIndex;

        if (can.readPacketNew(apiId,data)){
            return data.data;
        }

        return null;
    }

    void writeData(int apiIndex, int apiClass, byte[] data) {
        int apiId = apiClass << 4 | apiIndex;
        can.writePacket(data, apiId);
        
    }

    /**
     * Sets the delay in ms of the broadcast interval of data from the CANduit
     */
    void setBroadcastPeriod(int msec){
        int apiClass = 6;
        int apiIndex = 0;
        byte[] data = ByteManipulator.packData(
                new int[]{msec}, 
                new int[]{16},
                2
        );
        writeData(apiIndex,apiClass, data);
    } 

    /**
     * Sets the delay in ms of the time between PWM samples on each port on the CANduit
     */
    void setPWMSamplePeriod(int msec){
        int apiClass = 6;
        int apiIndex = 1;
        byte[] data = ByteManipulator.packData(
                new int[]{msec}, 
                new int[]{16},
                2
        );
        writeData(apiIndex,apiClass, data);
    } 
}
