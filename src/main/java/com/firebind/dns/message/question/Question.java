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
package com.firebind.dns.message.question;

import java.net.ProtocolException;
import java.util.List;

import com.firebind.dns.message.DnsClass;
import com.firebind.dns.message.DnsRecordType;
import com.firebind.dns.util.Labels;
import com.firebind.dns.util.Tools;

/**
 * @author Jay Houghton
 *
 */
public class Question {

    private String qname;
    private List<String> labels;
    private DnsRecordType qtype; // may need to specialize to a QType enum
    private DnsClass qclass;
    private int length; // length of this question in bytes
    
    public String getQname() {
        return qname;
    }
    public void setQname(String qname) {
        this.qname = qname;
    }
    public List<String> getLabels() {
        return labels;
    }
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
    

    
    public static Question fromBytes(byte[] data, int start) throws ProtocolException {
        if (data.length - start < 6) throw new ProtocolException("data length is less than minimum required length ("+(data.length-start)+")");
        Question question = new Question();
        // extract qname        
        Labels labels = Tools.extractLabels(data, start);
        question.setQname(labels.toName());
        question.setLabels(labels.getLabels());
        int index = labels.getEnd();
        question.setQtype(DnsRecordType.valueOf(Tools.bytesToIntBigEndian(data[index+1], data[index+2])));
        question.setQclass(DnsClass.valueOf(Tools.bytesToIntBigEndian(data[index+3], data[index+4])));
        question.length = (labels.getEnd() + 4) - start +1;
        return question;
    }
    @Override
    public String toString() {
        return "Question [qname=" + qname + ", qtype=" + qtype + ", qclass=" + qclass + "]";
    }
    public DnsRecordType getQtype() {
        return qtype;
    }
    public void setQtype(DnsRecordType qtype) {
        this.qtype = qtype;
    }
    public DnsClass getQclass() {
        return qclass;
    }
    public void setQclass(DnsClass qclass) {
        this.qclass = qclass;
    }
    public int getLength() {
        return length;
    }


    
    
    
}
