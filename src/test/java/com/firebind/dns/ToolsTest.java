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
package com.firebind.dns;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.firebind.dns.util.Tools;

public class ToolsTest {
    
    @Test
    public void testBits() {
        assertEquals(true, Tools.isBit(0, (byte)0x01));
        assertEquals(true, Tools.isBit(1, (byte)0x02));
        assertEquals(true, Tools.isBit(2, (byte)0x04));
        assertEquals(true, Tools.isBit(3, (byte)0x08));
        assertEquals(true, Tools.isBit(4, (byte)0x10));
        assertEquals(true, Tools.isBit(5, (byte)0x20));
        assertEquals(true, Tools.isBit(6, (byte)0x40));
        assertEquals(true, Tools.isBit(7, (byte)0x80));
        assertEquals(false, Tools.isBit(1, (byte)0x00));
        assertEquals(false, Tools.isBit(0, (byte)0x08));
    }
    
    @Test
    public void testMask() {
        assertEquals("1",Integer.toBinaryString(Tools.mask(1)));
        assertEquals("11",Integer.toBinaryString(Tools.mask(2)));
        assertEquals("111",Integer.toBinaryString(Tools.mask(3)));
        assertEquals("1111",Integer.toBinaryString(Tools.mask(4)));
        assertEquals("1111111",Integer.toBinaryString(Tools.mask(7)));
        assertEquals("11111111",Integer.toBinaryString(Tools.mask(8)));
    }

    @Test
    public void testbitsFromByte() {
        assertEquals(0xFF,Tools.bitsFromByte(0,7,(byte) 0xFF));
        assertEquals(0x01,Tools.bitsFromByte(0,0,(byte) 0xFF));
        assertEquals(6,Tools.bitsFromByte(1,4,(byte) 0x6C));
        assertEquals(3,Tools.bitsFromByte(5,7,(byte) 0x6C));
    }
    
    @Test
    public void testbitsFromRfcByte() {
        assertEquals(0xFF,Tools.bitsFromRfcByte(0,7,(byte) 0xFF));
        assertEquals(0x01,Tools.bitsFromRfcByte(0,0,(byte) 0xFF));
        
        // 0x6c = 01101100, RFC bits 1-4 are 1101 = 13
        assertEquals(13,Tools.bitsFromRfcByte(1,4,(byte) 0x6C));
        
        // 0x6c = 01101100, RFC bits 5-7 are 100 = 4  
        assertEquals(4,Tools.bitsFromRfcByte(5,7,(byte) 0x6C));

        // 0x8F = 10001111, RFC bits 5-7 are 111 = 7 
        assertEquals(7,Tools.bitsFromRfcByte(5,7,(byte) 0x8F));
        
        // 0x8F = 10001111, RFC bits 0-3 are 1000 = 8
        assertEquals(8,Tools.bitsFromRfcByte(0,3,(byte) 0x8F));
        
        // 0x8F = 10001111, RFC bits 1-7 are 0001111 = 15 
        assertEquals(15,Tools.bitsFromRfcByte(1,7,(byte) 0x8F));
        
        // 0x8F = 10001111, RFC bits 1-6 are 000111 = 7 
        assertEquals(7,Tools.bitsFromRfcByte(1,6,(byte) 0x8F));
        
        
    }
}
