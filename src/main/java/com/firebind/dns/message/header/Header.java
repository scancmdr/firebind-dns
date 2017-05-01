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

import java.net.ProtocolException;
import java.util.Arrays;

import com.firebind.dns.util.Tools;

/**
 * DNS Message header
 * 
 * @author Jay Houghton
 * @see Section 4.1.1 https://www.ietf.org/rfc/rfc1035.txt
 */
public class Header {
    
    /**
     * A 16 bit identifier assigned by the generator of the request
     */
    private int id;
    
    /**
     * Indicates query or response
     */
    private boolean qr;
    
    /**
     * Four bit field that specifies kind of query
     * @see com.firebind.dns.message.header.Opcode
     */
    private Opcode opcode;
    
    /**
     * Authoritative answer:
     * specifies that the responding name server is an
     * authority for the domain name in question
     */
    private boolean aa;
    
    /**
     * "TrunCation" (as per RFC)
     * Specifies that this message was truncated
     * 
     */
    private boolean tc;
    
    /**
     * Recursion Desired
     * Directs the name server to pursue the query recursively
     */
    private boolean rd;
    
    /**
     * Recursion Available
     * Indicates whether recursive query support is
     * supported by the name server
     */
    private boolean ra;

    /**
     * Reserved for future use.  Must be zero.
     */
    private final int z = 0;

    /**
     * 4 bit field is set as part of responses
     */
    private ResponseCode rcode;
    
    /**
     * 16 bit integer specifying the number of
     * entries in the question section
     */
    private int qdcount;
    
    /**
     * 16 bit integer specifying the number of
     * resource records in the answer section
     */
    private int ancount;
    
    /**
     * 16 bit integer specifying the number of name
     * server resource records in the authority records section
     */
    private int nscount;
    
    /**16 bit integer specifying the number of
     * resource records in the additional records section
     */
    private int arcount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isQr() {
        return qr;
    }
    
    public boolean query() {
        return !qr;
    }
    
    public boolean response() {
        return qr;
    }

    public void setQr(boolean qr) {
        this.qr = qr;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public void setOpcode(Opcode opcode) {
        this.opcode = opcode;
    }

    public boolean isAa() {
        return aa;
    }

    public void setAa(boolean aa) {
        this.aa = aa;
    }

    public boolean isTc() {
        return tc;
    }

    public void setTc(boolean tc) {
        this.tc = tc;
    }

    public boolean isRd() {
        return rd;
    }

    public void setRd(boolean rd) {
        this.rd = rd;
    }

    public boolean isRa() {
        return ra;
    }

    public void setRa(boolean ra) {
        this.ra = ra;
    }

    public ResponseCode getRcode() {
        return rcode;
    }

    public void setRcode(ResponseCode rcode) {
        this.rcode = rcode;
    }

    public int getQdcount() {
        return qdcount;
    }

    public void setQdcount(int qdcount) {
        this.qdcount = qdcount;
    }

    public int getAncount() {
        return ancount;
    }

    public void setAncount(int ancount) {
        this.ancount = ancount;
    }

    public int getNscount() {
        return nscount;
    }

    public void setNscount(int nscount) {
        this.nscount = nscount;
    }

    public int getArcount() {
        return arcount;
    }

    public void setArcount(int arcount) {
        this.arcount = arcount;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(qr?"RESPONSE":"QUERY");
        s.append("(").append(opcode.name()).append(")");
        s.append(" id=0x").append(Integer.toHexString(id));
        
        s.append(" authoritative=").append(aa);
        s.append(" truncation=").append(tc);
        s.append(" recursion_desired=").append(rd);
        s.append(" recursion_available=").append(ra);
        s.append(" z=").append(z);
        s.append(" rcode=").append(rcode.name());
        
        s.append(" questions:").append(qdcount).append(" answers:").append(ancount).append(" ns_records:").append(nscount)
         .append(" additional:").append(arcount);
        return s.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (aa ? 1231 : 1237);
        result = prime * result + ancount;
        result = prime * result + arcount;
        result = prime * result + id;
        result = prime * result + nscount;
        result = prime * result + ((opcode == null) ? 0 : opcode.hashCode());
        result = prime * result + qdcount;
        result = prime * result + (qr ? 1231 : 1237);
        result = prime * result + (ra ? 1231 : 1237);
        result = prime * result + ((rcode == null) ? 0 : rcode.hashCode());
        result = prime * result + (rd ? 1231 : 1237);
        result = prime * result + (tc ? 1231 : 1237);
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Header other = (Header) obj;
        if (aa != other.aa)
            return false;
        if (ancount != other.ancount)
            return false;
        if (arcount != other.arcount)
            return false;
        if (id != other.id)
            return false;
        if (nscount != other.nscount)
            return false;
        if (opcode != other.opcode)
            return false;
        if (qdcount != other.qdcount)
            return false;
        if (qr != other.qr)
            return false;
        if (ra != other.ra)
            return false;
        if (rcode != other.rcode)
            return false;
        if (rd != other.rd)
            return false;
        if (tc != other.tc)
            return false;
        if (z != other.z)
            return false;
        return true;
    }
    
    
    /**
     * Fabricate the wire level representation
     * of this Header
     * 
     * @return wire ready bytes encoded per RFC1035
     */
    public byte[] toBytes() {
        byte[] header = new byte[12];
        
        return header;
    }
    
    /**
     * Fabricate a Header object from a 12 bytes
     * 
     * @param data bytes wire level bytes of the header
     * @param start position in data array that header starts
     * @return Header that represents the parsing of the raw data
     * @throws ProtocolException
     */
    public static Header fromBytes(byte[] data, int start) throws ProtocolException {
        if ((null == data) || ((start+12) > data.length)) {
            throw new ProtocolException("Invalid bytes for header, bad length or null: start="+start+" length="+data.length+" data="+Arrays.toString(data));
        }
        Header header = new Header();
        
        header.setId(Tools.bytesToIntBigEndian(data[start], data[start+1]));
        header.setQr(Tools.isRfcBit(0,data[start+2]));
        header.setOpcode(Opcode.valueOf(Tools.bitsFromRfcByte(1, 4, data[start+2])));
        header.setAa(Tools.isRfcBit(5, data[start+2]));
        header.setTc(Tools.isRfcBit(6, data[start+2]));
        header.setRd(Tools.isRfcBit(7, data[start+2]));
        header.setRa(Tools.isRfcBit(0, data[start+3]));
        //header.setZ(); for future use
        header.setRcode(ResponseCode.valueOf(Tools.bitsFromRfcByte(4, 7, data[start+3])));
        
        header.setQdcount(Tools.bytesToIntBigEndian(data[start+4],data[start+5]));
        header.setAncount(Tools.bytesToIntBigEndian(data[start+6],data[start+7]));
        header.setNscount(Tools.bytesToIntBigEndian(data[start+8],data[start+9]));
        header.setArcount(Tools.bytesToIntBigEndian(data[start+10],data[start+11]));
        return header;
    }
    
}
