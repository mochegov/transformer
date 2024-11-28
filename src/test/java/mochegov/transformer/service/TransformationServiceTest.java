package mochegov.transformer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.UUID;
import mochegov.transformer.data.TestData;
import mochegov.transformer.db.model.Transformer;
import mochegov.transformer.dto.RequestDto;
import mochegov.transformer.dto.ResponseDto;
import mochegov.transformer.dto.ResponseItemDto;
import mochegov.transformer.enums.TransformerKind;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {TransformationService.class, ValidateService.class,
    FindAndDeleteTransformation.class, FindAndReplaceTransformation.class, CyrillicTransformation.class})
class TransformationServiceTest implements TestData {

    @Autowired
    private TransformationService transformationService;

    @Autowired
    private ValidateService validateService;

    @Autowired
    private FindAndDeleteTransformation findAndDeleteTransformation;
    @Autowired
    private FindAndReplaceTransformation findAndReplaceTransformation;
    @Autowired
    private CyrillicTransformation cyrillicTransformation;

    @MockBean
    private TransformerService transformerService;

    @MockBean
    private TransformationHistoryService transformationHistoryService;

    private final Transformer FindAndDeleteTransformer = Transformer
        .builder()
        .kind(TransformerKind.FIND_AND_DELETE)
        .build();

    private final Transformer FindAndReplaceTransformer = Transformer
        .builder()
        .kind(TransformerKind.FIND_AND_REPLACE)
        .build();

    private final Transformer CyrillicTransformer = Transformer
        .builder()
        .kind(TransformerKind.CYRILLIC)
        .build();

    @Test
    void transform_ok() {
        final UUID requestId = UUID.randomUUID();
        final RequestDto requestDto = buildRequestDto();

        when(transformerService.findBy(anyInt(), anyInt())).thenAnswer(invocationOnMock -> {
            int transformerId = invocationOnMock.getArgument(1);
            return switch (transformerId) {
                case 1 -> FindAndDeleteTransformer;
                case 2 -> FindAndReplaceTransformer;
                case 3 -> CyrillicTransformer;
                default -> throw new IllegalStateException("Unexpected value: " + transformerId);
            };
        });

        ResponseDto responseDto = transformationService.transform(requestId, requestDto);

        assertThat(responseDto.getItems()).isNotEmpty();

        ResponseItemDto firstItem = responseDto.getItems().get(0);
        ResponseItemDto secondItem = responseDto.getItems().get(1);

        assertAll(
            () -> assertThat(firstItem.getOriginalValue()).isEqualTo("Абракадабра"),
            () -> assertThat(firstItem.getProcessedValue()).isEqualTo("АбXXXаб"),
            () -> assertThat(secondItem.getOriginalValue()).isEqualTo("Hello, Дмитрий"),
            () -> assertThat(secondItem.getProcessedValue()).isEqualTo("Dobar dan, Dmitriy")
        );
    }
}