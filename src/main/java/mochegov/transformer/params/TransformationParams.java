package mochegov.transformer.params;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import mochegov.transformer.enums.TransformerKind;

@Getter
@SuperBuilder
public abstract class TransformationParams {
    private TransformerKind kind;
}
