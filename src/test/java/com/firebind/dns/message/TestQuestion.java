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

import static org.junit.Assert.assertEquals;

import java.net.ProtocolException;

import org.junit.Test;

import com.firebind.dns.message.question.Question;
import com.firebind.dns.util.Log;
import com.firebind.dns.util.SimpleLogger;

public class TestQuestion {
    
    Log log = new SimpleLogger();

    @Test(expected=ProtocolException.class)
    public void questionFromBytesTest1() throws ProtocolException {
        byte[] question_bytes = new byte[] { 0x00 };
        Question question = Question.fromBytes(question_bytes, 0);
    }
    
    @Test
    public void questionFromBytesTest2() throws ProtocolException {
        byte[] question_bytes = new byte[] 
                {(byte)0x03, (byte)0x77, (byte)0x77, (byte)0x77, (byte)0x04, (byte)0x63, (byte)0x65, (byte)0x72, 
                 (byte)0x74, (byte)0x03, (byte)0x6f, (byte)0x72, (byte)0x67, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01};
        
        Question question = Question.fromBytes(question_bytes, 0);
        log.info("questionFromBytesTest2() "+question);
        assertEquals("www.cert.org", question.getQname());
    }
    
    @Test
    public void textMxQuestion() throws ProtocolException {
        byte[] question_bytes = new byte[] {
                (byte)0x35, (byte)0x02, (byte)0x01, (byte)0x20, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x08, (byte)0x66, (byte)0x69, (byte)0x72,  
                (byte)0x65, (byte)0x62, (byte)0x69, (byte)0x6e, (byte)0x64, (byte)0x03, (byte)0x63, (byte)0x6f,
                (byte)0x6d, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,  
                (byte)0x29, (byte)0x10, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x00 };
        Question question = Question.fromBytes(question_bytes, 12); // question starts at index 12
        log.info("textMxQuestion() "+question);
        assertEquals("firebind.com", question.getQname());
    }
    
    /**
     * test the underflow handling
     * 
     * @throws ProtocolException
     */
    @Test(expected=ProtocolException.class)
    public void textMxQuestion2() throws ProtocolException {
        byte[] question_bytes = new byte[] {
                (byte)0x35, (byte)0x02, (byte)0x01, (byte)0x20, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x08, (byte)0x66, (byte)0x69, (byte)0x72,  
                (byte)0x65, (byte)0x62, (byte)0x69, (byte)0x6e, (byte)0x64, (byte)0x03, (byte)0x63, (byte)0x6f,
                (byte)0x6d, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,  
                (byte)0x29, (byte)0x10, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x00 };
        Question question = Question.fromBytes(question_bytes, 0); // question starts at index 12
        log.info("textMxQuestion2() "+question);
        assertEquals("firebind.com", question.getQname());
    }
    
    
    public void textMxResponse() throws ProtocolException {
        byte[] question_bytes = new byte[] {
                (byte)0x35, (byte)0x02, (byte)0x01, (byte)0x20, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x08, (byte)0x66, (byte)0x69, (byte)0x72,  
                (byte)0x65, (byte)0x62, (byte)0x69, (byte)0x6e, (byte)0x64, (byte)0x03, (byte)0x63, (byte)0x6f,
                (byte)0x6d, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,  
                (byte)0x29, (byte)0x10, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x00 };
        Question question = Question.fromBytes(question_bytes, 0);
        log.info("textMxResponse() "+question);
        assertEquals("www.cert.org", question.getQname());
                
    }
    

}
