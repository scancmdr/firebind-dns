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

import java.util.List;

/**
 * An extracted set of labels
 * 
 * @author Jay Houghton
 *
 */
public class Labels {

    private List<String> labels;
    private int start;
    
    public Labels(List<String> labels, int start, int end) {
        this.labels = labels;
        this.start = start;
        this.end = end;
    }
    public List<String> getLabels() {
        return labels;
    }
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    private int end;

    @Override
    public String toString() {
        return "Labels [labels=" + labels + ", start=" + start + ", end=" + end + "]";
    }

    public String toName() {
        return Tools.toName(labels);
    }
}
