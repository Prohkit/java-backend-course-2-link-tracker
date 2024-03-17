package edu.java.domain;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Link {
    private Long id;
    private URI url;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastUpdateCheckTime;
    private OffsetDateTime lastModifiedTime;
}
