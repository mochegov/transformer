package mochegov.transformer.params;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindAndReplaceTransformationParams extends TransformationParams {
    private String regex;
    private String replacement;
}
