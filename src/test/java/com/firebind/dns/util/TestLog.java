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
package com.firebind.dns.util;

import org.junit.Test;

public class TestLog {

    
    @Test
    public void testLog() {
        Log log = new SimpleLogger();
        log.setLevel(Log.Level.DEBUG);
        
        
        log.error("this is error level");
        log.warn("this is warn level");
        log.info("this is info level");
        log.debug("this is debug level");
    }
}
