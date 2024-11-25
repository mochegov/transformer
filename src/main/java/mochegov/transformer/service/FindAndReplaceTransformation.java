package mochegov.transformer.service;

import mochegov.transformer.params.FindAndReplaceTransformationParams;
import org.springframework.stereotype.Service;

@Service
public class FindAndReplaceTransformation implements Transformation<FindAndReplaceTransformationParams> {
    @Override
    public String perform(String value, FindAndReplaceTransformationParams params) {
        return value.replaceAll(params.getRegex(), params.getReplacement());
    }
}
