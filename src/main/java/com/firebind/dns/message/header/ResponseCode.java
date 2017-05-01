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

/**
 * Response code
 * 
 * @author Jay Houghton
 * @see https://www.ietf.org/rfc/rfc1035.txt
 */
public enum ResponseCode {
    
    /**
     * No error condition
     */
    NO_ERROR(0),
    
    /**
     * Unable to interpret the query
     */
    FORMAT_ERROR(1),
    
    /**
     * Server was unable to process this query
     */
    SERVER_FAILURE(2),
    
    /**
     * 
     */
    NAME_ERROR(3),
    
    /**
     * 
     */
    NOT_IMPLEMENTED(4),
    
    /**
     * 
     */
    REFUSED(5),
    
    /**
     * Reserved for future use (values 6-15)
     */
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
    
    private ResponseCode(int value) {
        this.value = value;
    }
    
    public static ResponseCode valueOf(int value) {
        for (ResponseCode opcode : values()) {
            if (opcode.value == value) {
                return opcode;
            }
        }
        return null;
    }
}
