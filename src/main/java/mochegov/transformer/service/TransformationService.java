package mochegov.transformer.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mochegov.transformer.dto.RequestDto;
import mochegov.transformer.dto.ResponseDto;
import mochegov.transformer.dto.ResponseItemDto;
import mochegov.transformer.params.CyrillicTransformationParams;
import mochegov.transformer.params.ElementForProcessing;
import mochegov.transformer.params.FindAndDeleteTransformationParams;
import mochegov.transformer.params.FindAndReplaceTransformationParams;
import mochegov.transformer.params.TransformationParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransformationService {
    private final FindAndDeleteTransformation findAndDeleteTransformation;
    private final FindAndReplaceTransformation findAndReplaceTransformation;
    private final CyrillicTransformation cyrillicTransformation;

    private final ValidateService validateService;
    private final TransformationHistoryService transformationHistoryService;

    @Value("${transformation.history.enabled}")
    private Boolean needToSaveHistory;

    public ResponseDto transform(UUID requestId, RequestDto requestDto) {
        List<ElementForProcessing> elements = validateService.getElements(requestDto);
        List<ResponseItemDto> items = elements.stream()
            .map(element -> ResponseItemDto.builder()
                .originalValue(element.getValue())
                .processedValue(processElement(element))
                .build()
            ).toList();

        ResponseDto responseDto = ResponseDto.builder().items(items).build();

        if (needToSaveHistory) {
            transformationHistoryService.saveHistory(requestId.toString(), responseDto);
        }

        return responseDto;
    }

    private String processElement(ElementForProcessing element) {
        String value = element.getValue();

        for (TransformationParams params : element.getParams()) {
            value = switch (params.getKind()) {
                case FIND_AND_DELETE -> findAndDeleteTransformation.perform(value, (FindAndDeleteTransformationParams) params);
                case FIND_AND_REPLACE -> findAndReplaceTransformation.perform(value, (FindAndReplaceTransformationParams) params);
                case CYRILLIC -> cyrillicTransformation.perform(value, (CyrillicTransformationParams) params);
            };
        }
        return value;
    }
}
