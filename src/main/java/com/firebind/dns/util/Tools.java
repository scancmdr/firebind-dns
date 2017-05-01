/*
 * Copyright @ 2017 Firebind Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.firebind.dns.util;

import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility and helper methods
 * 
 * @author Jay Houghton
 *
 */
public class Tools {
    
    public static final byte ZERO = (byte)0x00;
    
    /**
     * Find boolean value of a specific bit
     * 
     * @param bitPosition which bit to test, valid values 0-7
     * @param b the byte with the bits in question
     * @return true if the bit is set, false if bit is not set
     */
    public static boolean isBit(int bitPosition, byte b) {
        return ((0xFF & b) & (1 << bitPosition)) > 0; // java byte is signed, mask for promotion
    }
    
    /**
     * Find boolean value of a specific bit, in RFC ordering (reversed) NOT system order
     * 
     * @param rfcBitPosition RFC position (left to right) of bit to test
     * @param b byte in question
     * @return true if the bit is set, false if bit is not set
     */
    public static boolean isRfcBit(int rfcBitPosition, byte b) {
        return isBit(7-rfcBitPosition,b);
    }
    
    /**
     * Utility method
     * 
     * @param bytes corpus to convert to string
     * @return UTF-8 string of bytes
     */
    public static String utf8String(byte[] bytes) {
        return new String(bytes,StandardCharsets.UTF_8);
    }
    
    /**
     * Fabricate a bit mask of specified length
     * @param length number of bits in mask
     * @return the mask as an integer
     */
    public static int mask(int length) {
        int mask = 0;
        // considered an IntStream, but this is faster, masks are usually << 1024 bits
        for (int i=0; i<length; i++) {
            mask = (mask << 1) | 1;
        }
        return mask;
    }
    
    /**
     * Helper for logging
     * 
     * @param b byte to expose as a string
     * @return string representation of the binary form of b
     */
    public static String byteToString(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }
    
    /**
     * extract a number from a byte using a bit range (normal sane computer bit ordering here)
     * 
     * @param start starting bit position, 0-7, inclusive
     * @param rfc_end ending bit position, 0-7, > start, inclusive
     * @param b byte to extract value from
     * @return integer value of extracted bit range
     */
    public static int bitsFromByte(int start, int end, byte b) {
        // @TODO bound checking, throw on out of bounds for start, end..., also consider memoizing
        int mask = mask(end - start + 1);
        b = (byte) ((0xFF) & (b >>> start));
        return ((0xFF & b) & mask); // promotion is painful
    }
    
    /**
     * extract a number from a byte using a bit range as defined in RFC message formats
     * note that the bit ordering is reversed from normal system binary representation
     * 
     * @param rfc_start starting bit position, 0-7, inclusive, in RFC order which is backward from system order
     * @param rfc_end ending bit position, 0-7, > start, inclusive
     * @param b byte to extract value from
     * @return integer value of extracted bit range
     */
    public static int bitsFromRfcByte(int rfc_start, int rfc_end, byte b) {
        // convert RFC bit ordering to Java bit ordering by inversion
        return bitsFromByte(7-rfc_end, 7-rfc_start, b);
    }
    
    public static int bytesToIntBigEndian(byte[] bytes, int start) {
        int val = 0;
        for (int i=start; i<start+4; i++) {
            val = (val << 8) + (0xFF & (int)bytes[i]);
        }
        return val;
    }
    
    public static long bytesToUnsignedIntBigEndian(byte[] bytes, int start) {
        long value = 0;
        for (int i=start; i<start+4; i++) {
            value = (value << 8) + (0xFF & (int)bytes[i]);
        }
        return value;
    }
    
    public static int bytesToIntBigEndian(byte high, byte low) {
        return ((0xFF & (int)high) << 8) + (0xFF & (int)low);
    }
    
    public static int bytesToIntBigEndian(byte high, byte mid, byte low) {
        return ((0xFF & (int)high) << 16) + ((0xFF & (int)mid) << 8) + (0xFF & (int)low);
    }
    
    public static String extractString(byte[] data, int start, int length) {
        return utf8String(Arrays.copyOfRange(data, start, start+length));
    }

    public static String toName(List<String> labels) {
        if ((null == labels) || (0 == labels.size())) return null;
        StringBuilder s = new StringBuilder();
        for (int i=0; i< labels.size()-1; i++) {
            s.append(labels.get(i)).append('.');
        }
        s.append(labels.get(labels.size()-1));
        return s.toString();
    }
    
    public static String describeAsHex(byte[] bytes) {
        StringBuilder s = new StringBuilder("[");
        for (int i=0; i<bytes.length-1; i++) {
            s.append("0x").append(Integer.toHexString(0xFF & bytes[i])).append(", ");
        }
        s.append("0x").append(Integer.toHexString(0xFF & bytes[bytes.length-1])).append("]");
        return s.toString();
    }

    public static int find(byte b, byte[] data) {
        for (int i=0; i<data.length; i++) {
            if (data[i] == b) {
                return i;
            }
        }
        return -1;
    }
        
    /**
     * Extract labels from a QName or Name field in the message. This method
     * supports compression. 
     * 
     * @param data raw DNS message
     * @param start offset in message to start label parsing
     * @return a Labels object with collection of labels and start/end indexes
     * @throws ProtocolException when the expected length is not met
     */
    public static Labels extractLabels(byte[] data, int start) throws ProtocolException {
        List<String> labels = new ArrayList<String>();
        int index = start;
        int label_end_index = start;
        boolean counting = true;
        while (index<data.length) {
            
            int octet = (0xFF & data[index]);
            if (octet == 0x00) {
                break;
            } else if ((0xC0 & octet) == 0xC0) { // check for compression
                int offset = ((0x3F & octet) << 8) + (0xFF & data[index+1]);
                if (counting) {
                    label_end_index = index+1;
                }
                index = offset;
                counting = false;
            } else {
                int label_length = data[index++];
                labels.add(Tools.extractString(data, index, label_length));
                index += label_length;
                if (index > data.length-1) {
                    throw new ProtocolException("malformed label section");
                }
                if (counting) {
                    label_end_index = index;
                }
            }
        }
        return new Labels(labels,start,label_end_index);
    }
}
