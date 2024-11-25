package mochegov.transformer.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mochegov.transformer.dto.RequestDto;
import mochegov.transformer.dto.ResponseDto;
import mochegov.transformer.service.TransformationHistoryService;
import mochegov.transformer.service.TransformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class MainController implements MainControllerApi {
    private final TransformationService transformationService;
    private final TransformationHistoryService transformationHistoryService;

    @Override
    public ResponseEntity<ResponseDto> transform(UUID requestId, RequestDto requestDto) {
        var response = transformationService.transform(requestId, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
            .header(CORRELATION_ID_HEADER, requestId.toString())
            .body(response);
    }

    @Override
    public ResponseEntity<ResponseDto> getTransformationHistoryByRequestId(String requestId) {
       var response = transformationHistoryService.getHistoryByRequestId(requestId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}