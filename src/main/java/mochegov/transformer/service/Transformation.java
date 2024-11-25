package mochegov.transformer.service;

import mochegov.transformer.params.TransformationParams;

public interface Transformation<T extends TransformationParams> {
    String perform(String value, T params);
}
