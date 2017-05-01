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
package com.firebind.dns.message.header;

public enum Opcode {
    
    /**
     * Standard Query (QUERY)
     */
    QUERY(0),
    
    /**
     * Inverse Query
     */
    IQUERY(1),
    
    /**
     * Server status request
     */
    STATUS(2),
    
    /**
     * Reserved for future use (values 3-15)
     */
    RESERVED_3(3),
    RESERVED_4(4),
    RESERVED_5(5),
    RESERVED_6(6),
    RESERVED_7(7),
    RESERVED_8(8),
    RESERVED_9(9),
    RESERVED_10(10),
    RESERVED_11(11),
    RESERVED_12(12),
    RESERVED_13(13),
    RESERVED_14(14),
    RESERVED_15(15);
    
    private final int value;
    
    private Opcode(int value) {
        this.value = value;
    }
    
    public static Opcode valueOf(int value) {
        for (Opcode opcode : values()) {
            if (opcode.value == value) {
                return opcode;
            }
        }
        return null;
    }
}
