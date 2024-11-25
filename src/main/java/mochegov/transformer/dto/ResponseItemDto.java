package mochegov.transformer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseItemDto {
    private String originalValue;
    private String processedValue;
}
