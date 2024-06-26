/*
 * This file is generated by jOOQ.
 */
package edu.java.domain.jooq.tables;


import edu.java.domain.jooq.DefaultSchema;
import edu.java.domain.jooq.Keys;
import edu.java.domain.jooq.tables.records.StackoverflowQuestionRecord;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


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
public class StackoverflowQuestion extends TableImpl<StackoverflowQuestionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>STACKOVERFLOW_QUESTION</code>
     */
    public static final StackoverflowQuestion STACKOVERFLOW_QUESTION = new StackoverflowQuestion();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<StackoverflowQuestionRecord> getRecordType() {
        return StackoverflowQuestionRecord.class;
    }

    /**
     * The column <code>STACKOVERFLOW_QUESTION.QUESTION_ID</code>.
     */
    public final TableField<StackoverflowQuestionRecord, Long> QUESTION_ID = createField(DSL.name("QUESTION_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>STACKOVERFLOW_QUESTION.LINK_ID</code>.
     */
    public final TableField<StackoverflowQuestionRecord, Long> LINK_ID = createField(DSL.name("LINK_ID"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>STACKOVERFLOW_QUESTION.TITLE</code>.
     */
    public final TableField<StackoverflowQuestionRecord, String> TITLE = createField(DSL.name("TITLE"), SQLDataType.VARCHAR(1000000000).nullable(false), this, "");

    /**
     * The column <code>STACKOVERFLOW_QUESTION.IS_ANSWERED</code>.
     */
    public final TableField<StackoverflowQuestionRecord, Boolean> IS_ANSWERED = createField(DSL.name("IS_ANSWERED"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>STACKOVERFLOW_QUESTION.SCORE</code>.
     */
    public final TableField<StackoverflowQuestionRecord, Integer> SCORE = createField(DSL.name("SCORE"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>STACKOVERFLOW_QUESTION.ANSWER_COUNT</code>.
     */
    public final TableField<StackoverflowQuestionRecord, Long> ANSWER_COUNT = createField(DSL.name("ANSWER_COUNT"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>STACKOVERFLOW_QUESTION.LAST_ACTIVITY_DATE</code>.
     */
    public final TableField<StackoverflowQuestionRecord, OffsetDateTime> LAST_ACTIVITY_DATE = createField(DSL.name("LAST_ACTIVITY_DATE"), SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "");

    private StackoverflowQuestion(Name alias, Table<StackoverflowQuestionRecord> aliased) {
        this(alias, aliased, null);
    }

    private StackoverflowQuestion(Name alias, Table<StackoverflowQuestionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>STACKOVERFLOW_QUESTION</code> table reference
     */
    public StackoverflowQuestion(String alias) {
        this(DSL.name(alias), STACKOVERFLOW_QUESTION);
    }

    /**
     * Create an aliased <code>STACKOVERFLOW_QUESTION</code> table reference
     */
    public StackoverflowQuestion(Name alias) {
        this(alias, STACKOVERFLOW_QUESTION);
    }

    /**
     * Create a <code>STACKOVERFLOW_QUESTION</code> table reference
     */
    public StackoverflowQuestion() {
        this(DSL.name("STACKOVERFLOW_QUESTION"), null);
    }

    public <O extends Record> StackoverflowQuestion(Table<O> child, ForeignKey<O, StackoverflowQuestionRecord> key) {
        super(child, key, STACKOVERFLOW_QUESTION);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public List<ForeignKey<StackoverflowQuestionRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_9);
    }

    private transient Link _link;

    /**
     * Get the implicit join path to the <code>PUBLIC.LINK</code> table.
     */
    public Link link() {
        if (_link == null)
            _link = new Link(this, Keys.CONSTRAINT_9);

        return _link;
    }

    @Override
    @NotNull
    public StackoverflowQuestion as(String alias) {
        return new StackoverflowQuestion(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public StackoverflowQuestion as(Name alias) {
        return new StackoverflowQuestion(alias, this);
    }

    @Override
    @NotNull
    public StackoverflowQuestion as(Table<?> alias) {
        return new StackoverflowQuestion(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public StackoverflowQuestion rename(String name) {
        return new StackoverflowQuestion(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public StackoverflowQuestion rename(Name name) {
        return new StackoverflowQuestion(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public StackoverflowQuestion rename(Table<?> name) {
        return new StackoverflowQuestion(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row7<Long, Long, String, Boolean, Integer, Long, OffsetDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function7<? super Long, ? super Long, ? super String, ? super Boolean, ? super Integer, ? super Long, ? super OffsetDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Long, ? super Long, ? super String, ? super Boolean, ? super Integer, ? super Long, ? super OffsetDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
