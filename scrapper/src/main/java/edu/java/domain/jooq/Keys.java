/*
 * This file is generated by jOOQ.
 */
package edu.java.domain.jooq;


import edu.java.domain.jooq.tables.Chat;
import edu.java.domain.jooq.tables.ChatLink;
import edu.java.domain.jooq.tables.GithubRepository;
import edu.java.domain.jooq.tables.Link;
import edu.java.domain.jooq.tables.StackoverflowQuestion;
import edu.java.domain.jooq.tables.records.ChatLinkRecord;
import edu.java.domain.jooq.tables.records.ChatRecord;
import edu.java.domain.jooq.tables.records.GithubRepositoryRecord;
import edu.java.domain.jooq.tables.records.LinkRecord;
import edu.java.domain.jooq.tables.records.StackoverflowQuestionRecord;

import javax.annotation.processing.Generated;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatRecord> CONSTRAINT_1 = Internal.createUniqueKey(Chat.CHAT, DSL.name("CONSTRAINT_1"), new TableField[] { Chat.CHAT.ID }, true);
    public static final UniqueKey<ChatLinkRecord> CONSTRAINT_8 = Internal.createUniqueKey(ChatLink.CHAT_LINK, DSL.name("CONSTRAINT_8"), new TableField[] { ChatLink.CHAT_LINK.LINK_ID, ChatLink.CHAT_LINK.CHAT_ID }, true);
    public static final UniqueKey<GithubRepositoryRecord> CONSTRAINT_B3 = Internal.createUniqueKey(GithubRepository.GITHUB_REPOSITORY, DSL.name("CONSTRAINT_B3"), new TableField[] { GithubRepository.GITHUB_REPOSITORY.ID }, true);
    public static final UniqueKey<LinkRecord> CONSTRAINT_2 = Internal.createUniqueKey(Link.LINK, DSL.name("CONSTRAINT_2"), new TableField[] { Link.LINK.ID }, true);
    public static final UniqueKey<LinkRecord> CONSTRAINT_23 = Internal.createUniqueKey(Link.LINK, DSL.name("CONSTRAINT_23"), new TableField[] { Link.LINK.URL }, true);
    public static final UniqueKey<StackoverflowQuestionRecord> CONSTRAINT_9D = Internal.createUniqueKey(StackoverflowQuestion.STACKOVERFLOW_QUESTION, DSL.name("CONSTRAINT_9D"), new TableField[] { StackoverflowQuestion.STACKOVERFLOW_QUESTION.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ChatLinkRecord, ChatRecord> FK_CHAT_ID = Internal.createForeignKey(ChatLink.CHAT_LINK, DSL.name("FK_CHAT_ID"), new TableField[] { ChatLink.CHAT_LINK.CHAT_ID }, Keys.CONSTRAINT_1, new TableField[] { Chat.CHAT.ID }, true);
    public static final ForeignKey<ChatLinkRecord, LinkRecord> FK_LINK_ID = Internal.createForeignKey(ChatLink.CHAT_LINK, DSL.name("FK_LINK_ID"), new TableField[] { ChatLink.CHAT_LINK.LINK_ID }, Keys.CONSTRAINT_2, new TableField[] { Link.LINK.ID }, true);
    public static final ForeignKey<GithubRepositoryRecord, LinkRecord> CONSTRAINT_B = Internal.createForeignKey(GithubRepository.GITHUB_REPOSITORY, DSL.name("CONSTRAINT_B"), new TableField[] { GithubRepository.GITHUB_REPOSITORY.LINK_ID }, Keys.CONSTRAINT_2, new TableField[] { Link.LINK.ID }, true);
    public static final ForeignKey<StackoverflowQuestionRecord, LinkRecord> CONSTRAINT_9 = Internal.createForeignKey(StackoverflowQuestion.STACKOVERFLOW_QUESTION, DSL.name("CONSTRAINT_9"), new TableField[] { StackoverflowQuestion.STACKOVERFLOW_QUESTION.LINK_ID }, Keys.CONSTRAINT_2, new TableField[] { Link.LINK.ID }, true);
}
