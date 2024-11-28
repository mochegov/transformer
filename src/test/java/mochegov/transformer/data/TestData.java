package mochegov.transformer.data;

import java.util.List;
import mochegov.transformer.dto.ParameterDto;
import mochegov.transformer.dto.RequestDto;
import mochegov.transformer.dto.RequestItemDto;
import mochegov.transformer.dto.TransformerDto;
import mochegov.transformer.enums.ParameterKind;

public interface TestData {
    default RequestDto buildRequestDto() {
        return RequestDto.builder()
            .items(List.of(
                RequestItemDto.builder()
                    .originalValue("Абракадабра")
                    .transforms(List.of(
                            TransformerDto.builder()
                                .groupId(1)
                                .transformerId(1)
                                .parameters(List.of(
                                    ParameterDto.builder()
                                        .name(ParameterKind.REGEX)
                                        .value("ра")
                                        .build()
                                )).build(),
                            TransformerDto.builder()
                                .groupId(1)
                                .transformerId(2)
                                .parameters(List.of(
                                    ParameterDto.builder()
                                        .name(ParameterKind.REGEX)
                                        .value("кад")
                                        .build(),
                                    ParameterDto.builder()
                                        .name(ParameterKind.REPLACEMENT)
                                        .value("XXX")
                                        .build()
                                )).build()
                        )
                    ).build(),

                RequestItemDto.builder()
                    .originalValue("Hello, Дмитрий")
                    .transforms(List.of(
                            TransformerDto.builder()
                                .groupId(1)
                                .transformerId(2)
                                .parameters(List.of(
                                    ParameterDto.builder()
                                        .name(ParameterKind.REGEX)
                                        .value("Hello")
                                        .build(),
                                    ParameterDto.builder()
                                        .name(ParameterKind.REPLACEMENT)
                                        .value("Dobar dan")
                                        .build()
                                )).build(),
                        TransformerDto.builder()
                            .groupId(1)
                            .transformerId(3)
                            .parameters(List.of())
                            .build()
                        )
                    ).build()
            )).build();
    }
}
