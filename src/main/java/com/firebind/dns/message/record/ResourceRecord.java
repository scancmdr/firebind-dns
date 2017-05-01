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
package com.firebind.dns.message.record;

import java.net.ProtocolException;

import com.firebind.dns.message.DnsClass;
import com.firebind.dns.message.DnsRecordType;
import com.firebind.dns.util.Labels;
import com.firebind.dns.util.Tools;

/**
 * DNS Resource record
 * 
 * @author Jay Houghton
 * @see https://www.ietf.org/rfc/rfc1035.txt
 */
public class ResourceRecord {

    private String name;
    private DnsRecordType type;
    private DnsClass rdataClass;
    private long ttl; // 32-bit unsigned
    private int rdlength;
    private byte[] rdata;
    private int length; // length in bytes
    
    
    public int getLength() {
        return length;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public DnsRecordType getType() {
        return type;
    }
    public void setType(DnsRecordType type) {
        this.type = type;
    }
    public DnsClass getRdataClass() {
        return rdataClass;
    }
    public void setRdataClass(DnsClass rdataClass) {
        this.rdataClass = rdataClass;
    }
    public long getTtl() {
        return ttl;
    }
    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
    public int getRdlength() {
        return rdlength;
    }
    public void setRdlength(int rdlength) {
        this.rdlength = rdlength;
    }
    public byte[] getRdata() {
        return rdata;
    }
    public void setRdata(byte[] rdata) {
        this.rdata = rdata;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ResourceRecord name:").append(name)
            .append(" type:").append(type.name())
            .append(" class:").append(rdataClass.name())
            .append(" ttl:").append(ttl)
            .append(" rdlength:").append(rdlength)
            .append(" rdata:").append(Tools.describeAsHex(rdata));
        return s.toString();
    }
    
    /**
     * Fabricate a DNS resource record
     * Minimum byte size is 1(name) + 2(type) + 2(class) + 4(ttl) + 2(rdlength) = 11  
     * 
     * @param data raw wire data to source record from
     * @param start index in the raw data to start parsing
     * @return
     * @throws ProtocolException 
     */
    public static ResourceRecord fromBytes(byte[] data, int start) throws ProtocolException {
        if (data.length - start < 11) throw new ProtocolException("invalid size for resource record "+(data.length - start));
        ResourceRecord record = new ResourceRecord();
        
        System.out.println("RR.fromBytes() - "+Tools.describeAsHex(data)); 
        
        // TODO check for compression
        Labels labels = Tools.extractLabels(data, start);
        int end_of_name = labels.getEnd();
        record.name = labels.toName();
        record.type = DnsRecordType.valueOf(Tools.bytesToIntBigEndian(data[end_of_name+1],data[end_of_name+2]));
        record.rdataClass = DnsClass.valueOf(Tools.bytesToIntBigEndian(data[end_of_name+3],data[end_of_name+4]));
        record.ttl = Tools.bytesToUnsignedIntBigEndian(data, end_of_name+5);
        record.rdlength = Tools.bytesToIntBigEndian(data[end_of_name+9], data[end_of_name+10]);
        System.out.println("ttl="+record.ttl);
        if (end_of_name+10+record.rdlength > data.length) {
            throw new ProtocolException("rdlength extends beyond size of buffer");
        }
        
        record.rdata = new byte[record.rdlength];
        System.arraycopy(data, end_of_name+10+1, record.rdata, 0, record.rdlength);
        record.length = (end_of_name-start)+ 10 +1+ record.rdlength;
        
        return record;
    }
    
}
