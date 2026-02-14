package com.rebels2702.canduit.util;

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
}
