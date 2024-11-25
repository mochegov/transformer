package mochegov.transformer.db.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mochegov.transformer.enums.TransformerKind;

@Getter
@Setter
@Builder
public class Transformer {
    private UUID id;
    private Integer groupId;
    private Integer transformerId;
    private TransformerKind kind;
    private String name;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
