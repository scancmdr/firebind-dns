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

import org.junit.Test;

import com.firebind.dns.util.Log;
import com.firebind.dns.util.SimpleLogger;
import com.firebind.dns.util.Tools;

public class TestMessageFactory {
    Log log = new SimpleLogger();
    DnsMessageFactory f = new DnsMessageFactory();
    
    
    @Test
    public void testQuery1() throws ProtocolException {
        
        log.info("testQuery1() - "+Tools.describeAsHex(Messages.QUERY_CERT_ORG));
        
        DnsQuery query = f.queryFromData(Messages.QUERY_CERT_ORG);
        System.out.print(query.toString());
    }
    
    @Test
    public void testResponseParsing1() throws ProtocolException {
        log.info("testResponseParsing1() "+Tools.describeAsHex(Messages.RESPONSE_CERT_ORG));
        DnsResponse response = f.responseFromData(Messages.RESPONSE_CERT_ORG);
        log.info("testResponseParsing1() "+response.toString());
    }
    
    @Test
    public void testResponseParsing2() throws ProtocolException {
        log.info("testResponseParsing1() "+Tools.describeAsHex(Messages.RESPONSE_CMU_EDU));
        DnsResponse response = f.responseFromData(Messages.RESPONSE_CMU_EDU);
        log.info("testResponseParsing1() "+response.toString());
    }
    
    @Test
    public void testResponseParsing3() throws ProtocolException {
        DnsResponse response = f.responseFromData(Messages.RESPONSE_WPI_EDU);
        log.info("RESPONSE_WPI_EDU:"+response.toString());
    }
    
    
    //@Test
    public void testCmuRequest() throws ProtocolException {
        DnsQuery cmu = f.queryFromData(Messages.QUERY_CMU_EDU);
        org.junit.Assert.assertArrayEquals(Messages.QUERY_CMU_EDU,cmu.toBytes());
    }

}
