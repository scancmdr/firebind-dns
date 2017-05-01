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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.firebind.dns.message.record.ResourceRecord;

public class AnswerSection implements Serializable {
    
    private List<ResourceRecord> records = new ArrayList<>();

    @Override
    public String toString() {
        return "Answer [records=" + records + "]";
    }

    public List<ResourceRecord> getRecords() {
        return records;
    }

    public void setRecords(List<ResourceRecord> records) {
        this.records = records;
    }
    
    public void add(ResourceRecord record) {
        records.add(record);
    }
    
}
