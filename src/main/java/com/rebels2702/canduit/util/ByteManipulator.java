package com.rebels2702.canduit.util;
import java.util.ArrayList;

/**
 * Utility class for manipulating bytes.
*/
public class ByteManipulator {
    /**
     * Convert a byte array to an int, treating the byte array as little-endian.
     * @param data The byte array to convert.
     * @return The converted int value.
     */
    public static int bytesToInt(byte[] data) {
        if (data == null || data.length == 0) {
            return 0;
        }
        long out = 0L;
        int len = Math.min(data.length, 4);
        for (int i = 0; i < len; i++) {
            out |= ((long) Byte.toUnsignedInt(data[i])) << (8 * i);
        }
        return (int) out;
    }

    /**
     * Convert an array of ints and an array of bitsizes into a byte array, arranged in little-endian
     * @param data The values to convert
     * @param bitSizes The respective bitsizes of the values
     * @param byteSize The byteSize of the array
     */
    public static byte[] packData(int[] data, int[] bitSizes, int byteSize){
        byte[] packedData = new byte[byteSize];
        if (data.length != bitSizes.length) return packedData;

        long bitset = 0;
        int totalSize = 0;

        for (int idx = 0; idx<data.length;idx++){
            bitset |= (
                (long) (data[idx] & ((1L << bitSizes[idx])-1))
                ) << totalSize;
            totalSize += bitSizes[idx];        
        }

        if (totalSize >= 64) return packedData;

        long mask = 0xFF;
        for (int idx = 0; idx < byteSize;idx++){
            packedData[idx] = (byte) ((int) (bitset & mask));
            packedData[idx] = (byte) ((int) (bitset & mask));
            bitset >>= 8;
        }
        
        return packedData;
    }

    /**
     * Convert an array of bytes and an array of bitsizes into a int array, where each int i is a bitsize[i] slice of the array of bytes, which should be ordered in little endian
     * @param data The values to convert
     * @param bitSizes The respective bitsizes of the values
     * @param byteSize The byteSize of the array
     */
    public static int[] unpackData(byte[] data, int[] bitSizes){
        int[] unpackedData = new int[bitSizes.length];
        
        long dataInt = 0;
        for (int idx = 0; idx<data.length; idx++){
            dataInt |= (((long)data[idx]) << (idx));
        }

        for (int idx = 0; idx<bitSizes.length; idx++){
            unpackedData[idx] = (int) (dataInt & (1L << bitSizes[idx]));
            dataInt <<= bitSizes[idx];
        }

        return unpackedData;

    }
    
}
