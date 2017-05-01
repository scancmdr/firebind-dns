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
package com.firebind.dns.message;

/**
 * Type of query, extracted from the Question component of the request
 * 
 * @author Jay Houghton
 * @see https://www.ietf.org/rfc/rfc1035.txt
 */
public enum DnsRecordType {
    
    A(1), // https://tools.ietf.org/html/rfc1035
    NS(2), // https://tools.ietf.org/html/rfc1035
    CNAME(5), // https://tools.ietf.org/html/rfc1035
    SOA(6), // https://tools.ietf.org/html/rfc1035
    WKS(11), // https://tools.ietf.org/html/rfc1035
    PTR(12), // https://tools.ietf.org/html/rfc1035
    HINFO(13), // https://tools.ietf.org/html/rfc1035
    MX(15), // https://tools.ietf.org/html/rfc1035
    TXT(16), // https://tools.ietf.org/html/rfc1035
    RP(17), // https://tools.ietf.org/html/rfc1183
    AFSDB(18), // top secret, https://tools.ietf.org/html/rfc1183
    X25(19), // https://tools.ietf.org/html/rfc1183
    ISDN(20), // https://tools.ietf.org/html/rfc1183
    RT(21), // https://tools.ietf.org/html/rfc1183
    SIG(24), // https://tools.ietf.org/html/rfc2535
    KEY(25), // https://tools.ietf.org/html/rfc2535
    AAAA(28), // https://tools.ietf.org/html/rfc3596
    LOC(29), // https://tools.ietf.org/html/rfc1876
    NXT(30),
    SRV(33), // https://tools.ietf.org/html/rfc2872
    NAPTR(35), // https://tools.ietf.org/html/rfc3403
    A6(38), // deprecated, https://tools.ietf.org/html/rfc6563
    DNAME(39), // https://tools.ietf.org/html/rfc6672
    DS(43), // signer, // https://tools.ietf.org/html/rfc4034
    RRSIG(46), // https://tools.ietf.org/html/rfc4034
    NSEC(47),  // https://tools.ietf.org/html/rfc4034
    DNSKEY(48), // dnssec key, // https://tools.ietf.org/html/rfc4034
    SPF(99), // https://tools.ietf.org/html/rfc4408
    EUI48(108), // Extended Unique Identifier, see https://tools.ietf.org/html/rfc7043
    EUI64(109), // Extended Unique Identifier, see https://tools.ietf.org/html/rfc7043
    ANY(255),
    URI(256); // https://tools.ietf.org/html/rfc7553
    
    
    private final int value;
    
    private DnsRecordType(int value) {
        this.value = value;
    }
    
    public static DnsRecordType valueOf(int value) {
        for (DnsRecordType qtype : values()) {
            if (qtype.value == value) {
                return qtype;
            }
        }
        return null;
    }

}
