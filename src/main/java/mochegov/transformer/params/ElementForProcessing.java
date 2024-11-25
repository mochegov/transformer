package mochegov.transformer.params;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ElementForProcessing {
    private String value;
    private List<? extends TransformationParams> params;
}
