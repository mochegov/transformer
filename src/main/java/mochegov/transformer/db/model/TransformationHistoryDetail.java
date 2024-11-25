package mochegov.transformer.db.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransformationHistoryDetail {
    private UUID id;
    private UUID historyId;
    private String originalValue;
    private String processedValue;
}
