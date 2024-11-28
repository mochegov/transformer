package mochegov.transformer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemDto {
    @NotBlank
    @NotNull(message = "String for processing must not be null")
    private String originalValue;

    @Valid
    @NotNull
    private List<@Valid TransformerDto> transforms;
}
