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

import com.firebind.dns.message.header.Header;
import com.firebind.dns.message.question.Question;
import com.firebind.dns.message.record.ResourceRecord;

/**
 * Base class for DNS messages.
 * @see <a href="https://www.ietf.org/rfc/rfc1035.txt">https://www.ietf.org/rfc/rfc1035.txt</a>
 * 
 * @author Jay Houghton
 *
 */
public abstract class DnsMessage {
    
    protected Header header;
    protected QuestionSection questionSection;
    protected AnswerSection answerSection;
    protected AuthoritySection authoritySection;
    protected AdditionalSection additionalSection;
    
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public QuestionSection getQuestionSection() {
        return questionSection;
    }

    public void setQuestionSection(QuestionSection questionSection) {
        this.questionSection = questionSection;
    }

    public AnswerSection getAnswerSection() {
        return answerSection;
    }

    public void setAnswerSection(AnswerSection answerSection) {
        this.answerSection = answerSection;
    }

    public AuthoritySection getAuthoritySection() {
        return authoritySection;
    }

    public void setAuthoritySection(AuthoritySection authoritySection) {
        this.authoritySection = authoritySection;
    }

    public AdditionalSection getAdditionalSection() {
        return additionalSection;
    }

    public void setAdditionalSection(AdditionalSection additionalSection) {
        this.additionalSection = additionalSection;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(header.toString());
        if (null != questionSection) {
            s.append(" ").append(questionSection.toString());
        }
        if (null != answerSection) {
            s.append(" ").append(answerSection.toString());
        }
        if (null != authoritySection) {
            s.append(" ").append(authoritySection.toString());
        }
        if (null != additionalSection) {
            s.append(" ").append(additionalSection.toString());
        }
        return s.toString();
    }
    
    public byte[] toBytes() {
        return null;
    }

    void addQuestion(Question question) {
        if (null == questionSection) {
            questionSection = new QuestionSection();
        }
        questionSection.add(question);
    }
    
    void addAnswer(ResourceRecord record) {
        if (null == answerSection) {
            answerSection = new AnswerSection();
        }
        answerSection.add(record);
    }
    
    void addAuthority(ResourceRecord record) {
        if (null == authoritySection) {
            authoritySection = new AuthoritySection();
        }
        authoritySection.add(record);
    }
    
    void addAdditional(ResourceRecord record) {
        if (null == additionalSection) {
            additionalSection = new AdditionalSection();
        }
        additionalSection.add(record);
    }
    
}
