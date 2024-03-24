package edu.java.domain;

import edu.java.service.UriToStringConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "links")
    private List<Chat> chats;
    @Convert(converter = UriToStringConverter.class)
    private URI url;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @CreationTimestamp
    private OffsetDateTime lastUpdateCheckTime;
    private OffsetDateTime lastModifiedTime;
}
