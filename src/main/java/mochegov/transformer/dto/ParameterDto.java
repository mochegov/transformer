package mochegov.transformer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mochegov.transformer.enums.ParameterKind;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDto {
    @Valid
    private ParameterKind name;

    @NotBlank
    @NotNull(message = "Parameter value must not be null")
    private String value;
}
