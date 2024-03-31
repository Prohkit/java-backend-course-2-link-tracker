/*
 * This file is generated by jOOQ.
 */
package edu.java.domain.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastUpdateCheckTime;
    private OffsetDateTime lastModifiedTime;

    public Link() {}

    public Link(Link value) {
        this.id = value.id;
        this.url = value.url;
        this.createdAt = value.createdAt;
        this.lastUpdateCheckTime = value.lastUpdateCheckTime;
        this.lastModifiedTime = value.lastModifiedTime;
    }

    @ConstructorProperties({ "id", "url", "createdAt", "lastUpdateCheckTime", "lastModifiedTime" })
    public Link(
        @Nullable Long id,
        @NotNull String url,
        @Nullable OffsetDateTime createdAt,
        @Nullable OffsetDateTime lastUpdateCheckTime,
        @Nullable OffsetDateTime lastModifiedTime
    ) {
        this.id = id;
        this.url = url;
        this.createdAt = createdAt;
        this.lastUpdateCheckTime = lastUpdateCheckTime;
        this.lastModifiedTime = lastModifiedTime;
    }

    /**
     * Getter for <code>LINK.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINK.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINK.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINK.CREATED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Setter for <code>LINK.CREATED_AT</code>.
     */
    public void setCreatedAt(@Nullable OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for <code>LINK.LAST_UPDATE_CHECK_TIME</code>.
     */
    @Nullable
    public OffsetDateTime getLastUpdateCheckTime() {
        return this.lastUpdateCheckTime;
    }

    /**
     * Setter for <code>LINK.LAST_UPDATE_CHECK_TIME</code>.
     */
    public void setLastUpdateCheckTime(@Nullable OffsetDateTime lastUpdateCheckTime) {
        this.lastUpdateCheckTime = lastUpdateCheckTime;
    }

    /**
     * Getter for <code>LINK.LAST_MODIFIED_TIME</code>.
     */
    @Nullable
    public OffsetDateTime getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    /**
     * Setter for <code>LINK.LAST_MODIFIED_TIME</code>.
     */
    public void setLastModifiedTime(@Nullable OffsetDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Link other = (Link) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.url == null) {
            if (other.url != null)
                return false;
        }
        else if (!this.url.equals(other.url))
            return false;
        if (this.createdAt == null) {
            if (other.createdAt != null)
                return false;
        }
        else if (!this.createdAt.equals(other.createdAt))
            return false;
        if (this.lastUpdateCheckTime == null) {
            if (other.lastUpdateCheckTime != null)
                return false;
        }
        else if (!this.lastUpdateCheckTime.equals(other.lastUpdateCheckTime))
            return false;
        if (this.lastModifiedTime == null) {
            if (other.lastModifiedTime != null)
                return false;
        }
        else if (!this.lastModifiedTime.equals(other.lastModifiedTime))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        result = prime * result + ((this.lastUpdateCheckTime == null) ? 0 : this.lastUpdateCheckTime.hashCode());
        result = prime * result + ((this.lastModifiedTime == null) ? 0 : this.lastModifiedTime.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(lastUpdateCheckTime);
        sb.append(", ").append(lastModifiedTime);

        sb.append(")");
        return sb.toString();
    }
}