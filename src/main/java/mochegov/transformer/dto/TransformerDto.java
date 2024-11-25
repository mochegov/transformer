package mochegov.transformer.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransformerDto {
    private Integer groupId;
    private Integer transformerId;
    private List<ParameterDto> parameters;
}
