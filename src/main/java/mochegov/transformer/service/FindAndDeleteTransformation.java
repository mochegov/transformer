package mochegov.transformer.service;

import mochegov.transformer.params.FindAndDeleteTransformationParams;
import org.springframework.stereotype.Service;

@Service
public class FindAndDeleteTransformation implements Transformation<FindAndDeleteTransformationParams> {
    @Override
    public String perform(String value, FindAndDeleteTransformationParams params) {
        return value.replaceAll(params.getRegex(), "");
    }
}
