/*
 * This file is generated by jOOQ.
 */
package edu.java.domain.jooq.tables.records;


import edu.java.domain.jooq.tables.StackoverflowQuestion;

import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class StackoverflowQuestionRecord extends TableRecordImpl<StackoverflowQuestionRecord> implements Record7<Long, Long, String, Boolean, Integer, Long, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>STACKOVERFLOW_QUESTION.QUESTION_ID</code>.
     */
    public void setQuestionId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>STACKOVERFLOW_QUESTION.QUESTION_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getQuestionId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>STACKOVERFLOW_QUESTION.LINK_ID</code>.
     */
    public void setLinkId(@Nullable Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>STACKOVERFLOW_QUESTION.LINK_ID</code>.
     */
    @Nullable
    public Long getLinkId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>STACKOVERFLOW_QUESTION.TITLE</code>.
     */
    public void setTitle(@NotNull String value) {
        set(2, value);
    }

    /**
     * Getter for <code>STACKOVERFLOW_QUESTION.TITLE</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>STACKOVERFLOW_QUESTION.IS_ANSWERED</code>.
     */
    public void setIsAnswered(@NotNull Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>STACKOVERFLOW_QUESTION.IS_ANSWERED</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Boolean getIsAnswered() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>STACKOVERFLOW_QUESTION.SCORE</code>.
     */
    public void setScore(@NotNull Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>STACKOVERFLOW_QUESTION.SCORE</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Integer getScore() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>STACKOVERFLOW_QUESTION.ANSWER_COUNT</code>.
     */
    public void setAnswerCount(@NotNull Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>STACKOVERFLOW_QUESTION.ANSWER_COUNT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getAnswerCount() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>STACKOVERFLOW_QUESTION.LAST_ACTIVITY_DATE</code>.
     */
    public void setLastActivityDate(@Nullable OffsetDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>STACKOVERFLOW_QUESTION.LAST_ACTIVITY_DATE</code>.
     */
    @Nullable
    public OffsetDateTime getLastActivityDate() {
        return (OffsetDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row7<Long, Long, String, Boolean, Integer, Long, OffsetDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row7<Long, Long, String, Boolean, Integer, Long, OffsetDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return StackoverflowQuestion.STACKOVERFLOW_QUESTION.QUESTION_ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return StackoverflowQuestion.STACKOVERFLOW_QUESTION.LINK_ID;
    }

    @Override
    @NotNull
    public Field<String> field3() {
        return StackoverflowQuestion.STACKOVERFLOW_QUESTION.TITLE;
    }

    @Override
    @NotNull
    public Field<Boolean> field4() {
        return StackoverflowQuestion.STACKOVERFLOW_QUESTION.IS_ANSWERED;
    }

    @Override
    @NotNull
    public Field<Integer> field5() {
        return StackoverflowQuestion.STACKOVERFLOW_QUESTION.SCORE;
    }

    @Override
    @NotNull
    public Field<Long> field6() {
        return StackoverflowQuestion.STACKOVERFLOW_QUESTION.ANSWER_COUNT;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field7() {
        return StackoverflowQuestion.STACKOVERFLOW_QUESTION.LAST_ACTIVITY_DATE;
    }

    @Override
    @NotNull
    public Long component1() {
        return getQuestionId();
    }

    @Override
    @Nullable
    public Long component2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public String component3() {
        return getTitle();
    }

    @Override
    @NotNull
    public Boolean component4() {
        return getIsAnswered();
    }

    @Override
    @NotNull
    public Integer component5() {
        return getScore();
    }

    @Override
    @NotNull
    public Long component6() {
        return getAnswerCount();
    }

    @Override
    @Nullable
    public OffsetDateTime component7() {
        return getLastActivityDate();
    }

    @Override
    @NotNull
    public Long value1() {
        return getQuestionId();
    }

    @Override
    @Nullable
    public Long value2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public String value3() {
        return getTitle();
    }

    @Override
    @NotNull
    public Boolean value4() {
        return getIsAnswered();
    }

    @Override
    @NotNull
    public Integer value5() {
        return getScore();
    }

    @Override
    @NotNull
    public Long value6() {
        return getAnswerCount();
    }

    @Override
    @Nullable
    public OffsetDateTime value7() {
        return getLastActivityDate();
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord value1(@NotNull Long value) {
        setQuestionId(value);
        return this;
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord value2(@Nullable Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord value3(@NotNull String value) {
        setTitle(value);
        return this;
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord value4(@NotNull Boolean value) {
        setIsAnswered(value);
        return this;
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord value5(@NotNull Integer value) {
        setScore(value);
        return this;
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord value6(@NotNull Long value) {
        setAnswerCount(value);
        return this;
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord value7(@Nullable OffsetDateTime value) {
        setLastActivityDate(value);
        return this;
    }

    @Override
    @NotNull
    public StackoverflowQuestionRecord values(@NotNull Long value1, @Nullable Long value2, @NotNull String value3, @NotNull Boolean value4, @NotNull Integer value5, @NotNull Long value6, @Nullable OffsetDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StackoverflowQuestionRecord
     */
    public StackoverflowQuestionRecord() {
        super(StackoverflowQuestion.STACKOVERFLOW_QUESTION);
    }

    /**
     * Create a detached, initialised StackoverflowQuestionRecord
     */
    @ConstructorProperties({ "questionId", "linkId", "title", "isAnswered", "score", "answerCount", "lastActivityDate" })
    public StackoverflowQuestionRecord(@NotNull Long questionId, @Nullable Long linkId, @NotNull String title, @NotNull Boolean isAnswered, @NotNull Integer score, @NotNull Long answerCount, @Nullable OffsetDateTime lastActivityDate) {
        super(StackoverflowQuestion.STACKOVERFLOW_QUESTION);

        setQuestionId(questionId);
        setLinkId(linkId);
        setTitle(title);
        setIsAnswered(isAnswered);
        setScore(score);
        setAnswerCount(answerCount);
        setLastActivityDate(lastActivityDate);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised StackoverflowQuestionRecord
     */
    public StackoverflowQuestionRecord(edu.java.domain.jooq.tables.pojos.StackoverflowQuestion value) {
        super(StackoverflowQuestion.STACKOVERFLOW_QUESTION);

        if (value != null) {
            setQuestionId(value.getQuestionId());
            setLinkId(value.getLinkId());
            setTitle(value.getTitle());
            setIsAnswered(value.getIsAnswered());
            setScore(value.getScore());
            setAnswerCount(value.getAnswerCount());
            setLastActivityDate(value.getLastActivityDate());
            resetChangedOnNotNull();
        }
    }
}
