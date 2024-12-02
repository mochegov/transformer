package mochegov.transformer.service;

import static mochegov.transformer.enums.TransformerKind.CYRILLIC;
import static mochegov.transformer.enums.TransformerKind.FIND_AND_DELETE;
import static mochegov.transformer.enums.TransformerKind.FIND_AND_REPLACE;
import static mochegov.transformer.enums.TransformerKind.GREEK;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mochegov.transformer.db.model.Transformer;
import mochegov.transformer.dto.ParameterDto;
import mochegov.transformer.dto.RequestDto;
import mochegov.transformer.dto.RequestItemDto;
import mochegov.transformer.dto.TransformerDto;
import mochegov.transformer.enums.ParameterKind;
import mochegov.transformer.errors.ErrorType;
import mochegov.transformer.errors.FieldValidationException;
import mochegov.transformer.params.CyrillicTransformationParams;
import mochegov.transformer.params.ElementForProcessing;
import mochegov.transformer.params.FindAndDeleteTransformationParams;
import mochegov.transformer.params.FindAndReplaceTransformationParams;
import mochegov.transformer.params.GreekTransformationParams;
import mochegov.transformer.params.TransformationParams;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ValidateService {
    private final TransformerService transformerService;

    public List<ElementForProcessing> getElements(RequestDto requestDto) {
        return requestDto.getItems().stream()
            .map(this::getElement)
            .toList();
    }

    private ElementForProcessing getElement(RequestItemDto requestItemDto) {
        List<? extends TransformationParams> transformationParams = getListParams(requestItemDto.getTransforms());

        return ElementForProcessing.builder()
            .value(requestItemDto.getOriginalValue())
            .params(transformationParams)
            .build();
    }

    private List<? extends TransformationParams> getListParams(List<TransformerDto> transformerDtoList) {
        return transformerDtoList.stream()
            .map(this::getParams)
            .toList();
    }

    private TransformationParams getParams(TransformerDto transformerDto) {
        Transformer transformer = transformerService.findBy(transformerDto.getGroupId(), transformerDto.getTransformerId());
        return switch (transformer.getKind()) {
            case FIND_AND_DELETE -> getFindAndDeleteTransformationParams(transformerDto.getParameters());
            case FIND_AND_REPLACE -> getFindAndReplaceTransformationParams(transformerDto.getParameters());
            case CYRILLIC -> getCyrillicTransformationParams(transformerDto.getParameters());
            case GREEK -> getGreekTransformationParams(transformerDto.getParameters());
        };
    }

    private FindAndDeleteTransformationParams getFindAndDeleteTransformationParams(List<ParameterDto> params) {
        if (params.size() != 1) {
            throw new FieldValidationException("Find and delete transformation must have only one parameter",
                ErrorType.VALIDATION_ERRORS);
        }

        String regex = getParameterByName(params, ParameterKind.REGEX)
            .orElseThrow(() -> new FieldValidationException("Parameter 'REGEX' for 'Find and delete' transformation not found",
                ErrorType.VALIDATION_ERRORS));

        return FindAndDeleteTransformationParams.builder()
            .kind(FIND_AND_DELETE)
            .regex(regex)
            .build();
    }

    private FindAndReplaceTransformationParams getFindAndReplaceTransformationParams(List<ParameterDto> params) {
        if (params.size() != 2) {
            throw new FieldValidationException("Find and replace transformation must have two parameters",
                ErrorType.VALIDATION_ERRORS);
        }

        String regex = getParameterByName(params, ParameterKind.REGEX)
            .orElseThrow(() -> new FieldValidationException("Parameter 'REGEX' for 'Find and replace' transformation not found",
                ErrorType.VALIDATION_ERRORS));

        String replacement = getParameterByName(params, ParameterKind.REPLACEMENT)
            .orElseThrow(() -> new FieldValidationException("Parameter 'REPLACEMENT' for 'Find and replace' transformation not found",
                ErrorType.VALIDATION_ERRORS));

        return FindAndReplaceTransformationParams.builder()
            .kind(FIND_AND_REPLACE)
            .regex(regex)
            .replacement(replacement)
            .build();
    }

    private CyrillicTransformationParams getCyrillicTransformationParams(List<ParameterDto> params) {
        if (!params.isEmpty()) {
            throw new FieldValidationException("Cyrillic transformation must not have any parameter", ErrorType.VALIDATION_ERRORS);
        }

        return CyrillicTransformationParams.builder()
            .kind(CYRILLIC)
            .build();
    }


    private GreekTransformationParams getGreekTransformationParams(List<ParameterDto> params) {
        if (!params.isEmpty()) {
            throw new FieldValidationException("Greek transformation must not have any parameter", ErrorType.VALIDATION_ERRORS);
        }

        return GreekTransformationParams.builder()
            .kind(GREEK)
            .build();
    }

    private Optional<String> getParameterByName(List<ParameterDto> params, ParameterKind parameterKind) {
        return params.stream()
            .filter(parameterDto -> parameterDto.getName().equals(parameterKind))
            .map(ParameterDto::getValue)
            .findFirst();
    }
}
