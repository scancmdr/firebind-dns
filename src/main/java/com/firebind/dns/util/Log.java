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

import java.util.function.Function;
import java.util.function.Supplier;

public interface Log {

    public Level getLevel();
    public void setLevel(Level level);
    
    public void debug(String msg);
    public void info(String msg);
    public void warn(String msg);
    public void error(String msg);
    
    default Function<String,Log> getSimpleLogger(String name) {
        return new Function<String,Log>() {
            public Log apply(String t) {
                return new SimpleLogger(name);
            }
        };
    }
    
    default Supplier<Log> getSimpleLogger() {
        return new Supplier<Log>() {
            @Override
            public Log get() {
                return new SimpleLogger();
            }
            
        };
    }
    
    default void setError() {
        setLevel(Level.ERROR);  
    }
    
    default void setDebug() {
        setLevel(Level.DEBUG);
    }

    default boolean isDebug() {
        return getLevel() == Level.DEBUG;
    }

    default void setInfo() {
        setLevel(Level.INFO);
    }

    default void setWarn() {
        setLevel(Level.WARN);
    }

    public enum Level {
        DEBUG(1),
        INFO(2),
        WARN(3),
        ERROR(4);
        
        final int value;
        
        private Level(int value) {
            this.value = value;
        }
        
        public static Level valueOf(int value) {
            for (Level level : values()) {
                if (level.value == value) {
                    return level;
                }
            }
            return null;
        }
    }
}
