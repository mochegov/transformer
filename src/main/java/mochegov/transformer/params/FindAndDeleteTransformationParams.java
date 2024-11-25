package mochegov.transformer.params;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindAndDeleteTransformationParams extends TransformationParams {
    private String regex;
}
