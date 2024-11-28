package mochegov.transformer.dto;

import jakarta.validation.Valid;
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
public class TransformerDto {
    @NotNull
    private Integer groupId;

    @NotNull
    private Integer transformerId;

    @Valid
    private List<@Valid ParameterDto> parameters;
}
