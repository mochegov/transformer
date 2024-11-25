package mochegov.transformer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mochegov.transformer.enums.ParameterKind;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDto {
    private ParameterKind name;
    private String value;
}
