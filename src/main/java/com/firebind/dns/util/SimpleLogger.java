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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SimpleLogger implements Log {
    
    public static Level level = Level.DEBUG; // be sure to default to WARN or above
    private static final DateFormat timeStampFmt = new SimpleDateFormat("HH:mm:ss.SSS");
    private String name;
    
    public SimpleLogger(String name) {
        this.name = name;
    }
    
    public SimpleLogger() {
        
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String caller = stElements[2].getClassName();
        this.name = caller.substring(caller.lastIndexOf('.')+1);
        
        //this.name = new Exception().getStackTrace()[1].getClassName(); // yeah this is a bad idea
    }
    
    public SimpleLogger(Class type) {
        this.name = type.getSimpleName();
    }
    
    public void debug(String msg) {
        if (level.value > Level.DEBUG.value) return;
        output(format(msg,"DEBUG"));
    }
    public void info(String msg) {
        if (level.value > Level.INFO.value) return;
        output(format(msg,"INFO "));
    }
    public void warn(String msg) {
        if (level.value > Level.WARN.value) return;
        output(format(msg,"WARN "));
    }
    public void error(String msg) {
        if (level.value > Level.ERROR.value) return;
        output(format(msg,"ERROR"));
    }

    public String format(String msg, String prefix) {
        StringBuilder s = new StringBuilder();
        synchronized (timeStampFmt) {
            s.append(timeStampFmt.format(System.currentTimeMillis()));
        }
        s.append(" ").append(prefix).append(" ").append(name).append(" - ");
        s.append(msg);
        return s.toString();
    }
    
    private void output(String out) {
        System.out.println(out);
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

}
