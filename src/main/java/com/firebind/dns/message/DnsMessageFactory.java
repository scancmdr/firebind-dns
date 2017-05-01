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

import java.net.ProtocolException;

import com.firebind.dns.message.header.Header;
import com.firebind.dns.message.question.Question;
import com.firebind.dns.message.record.ResourceRecord;

public class DnsMessageFactory {

    public DnsQuery queryFromData(byte[] data) throws ProtocolException {     
        DnsQuery query = new DnsQuery();
        makeDnsMessage(query,data);
        return query;   
    }
    
    public DnsResponse responseFromData(byte[] data) throws ProtocolException {
        DnsResponse response = new DnsResponse();
        makeDnsMessage(response,data);
        return response;
    }
    
    private DnsMessage makeDnsMessage(DnsMessage message, byte[] data) throws ProtocolException {
        int index = 0;
        Header header = Header.fromBytes(data,index);
        message.setHeader(header);
        index += 12;
        for (int questions = 0; questions<header.getQdcount(); questions++) {
            Question question = Question.fromBytes(data, index);
            index += question.getLength();
            message.addQuestion(question);
        }
        for (int answers = 0; answers<header.getAncount(); answers++) {
            ResourceRecord record = ResourceRecord.fromBytes(data, index);
            index += record.getLength();
            message.addAnswer(record);
        }
        for (int authorities = 0; authorities<header.getNscount(); authorities++) {
            ResourceRecord record = ResourceRecord.fromBytes(data, index);
            index += record.getLength();
            message.addAuthority(record);
        }
        for (int additionals = 0; additionals<header.getArcount(); additionals++) {
            ResourceRecord record = ResourceRecord.fromBytes(data, index);
            index += record.getLength();
            message.addAdditional(record);
        }
        return message;
    }

}
