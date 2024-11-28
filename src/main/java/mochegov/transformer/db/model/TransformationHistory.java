package mochegov.transformer.db.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransformationHistory {
    private UUID id;
    private String requestId;
    private LocalDateTime processedAt;
}
