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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.firebind.dns.util.Labels;
import com.firebind.dns.util.Tools;

public class TestLabelExtraction {

    @Test
    public void simpleLabel() throws ProtocolException {
        byte[] data = new byte[] { 0x00, (byte)0x03, (byte)0x77, (byte)0x77, (byte)0x77, (byte)0x04, (byte)0x63, (byte)0x65, (byte)0x72, (byte)0x74, (byte)0x03, (byte)0x6f, (byte)0x72, (byte)0x67, (byte)0x00 };
        Labels labels = Tools.extractLabels(data, 1);
        assertEquals(labels.toName(),"www.cert.org");
    }
    
    @Test
    public void compressedLabel() throws ProtocolException {
        Labels labels = Tools.extractLabels(Messages.RESPONSE_CMU_EDU, 41);
        assertEquals(labels.toName(),"WWW-CMU-PROD-VIP.ANDREW.cmu.edu");
    }
}
