package mochegov.transformer.service;

import static mochegov.transformer.errors.ErrorType.DATABASE_ERROR;
import static mochegov.transformer.errors.ErrorType.HISTORY_NOT_FOUND;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mochegov.transformer.db.model.TransformationHistory;
import mochegov.transformer.db.model.TransformationHistoryDetail;
import mochegov.transformer.db.repository.TransformationHistoryDetailRepository;
import mochegov.transformer.db.repository.TransformationHistoryRepository;
import mochegov.transformer.dto.ResponseDto;
import mochegov.transformer.dto.ResponseItemDto;
import mochegov.transformer.errors.NotFoundException;
import mochegov.transformer.errors.ServerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TransformationHistoryService {
    private final TransformationHistoryRepository transformationHistoryRepository;
    private final TransformationHistoryDetailRepository transformationHistoryDetailRepository;

    @Transactional
    public void saveHistory(String requestId, ResponseDto responseDto) {
        TransformationHistory transformationHistory =
            transformationHistoryRepository.add(buildTransformationHistory(requestId))
                .orElseThrow(() -> new ServerException(
                    "An error occurred while inserting a record into the history table", DATABASE_ERROR));

        List<TransformationHistoryDetail> historyDetailList =
            buildTransformationHistoryDetailList(transformationHistory.getId(), responseDto);
        transformationHistoryDetailRepository.batchInsert(historyDetailList);
    }

    public ResponseDto getHistoryByRequestId(String requestId) {
        TransformationHistory transformationHistory = transformationHistoryRepository.findBy(requestId)
            .orElseThrow(() -> new NotFoundException("History not found by requestId", HISTORY_NOT_FOUND));

        List<TransformationHistoryDetail> historyDetailList =
            transformationHistoryDetailRepository.findBy(transformationHistory.getId());

        List<ResponseItemDto> responseItemDtoList = historyDetailList.stream()
            .map(transformationHistoryDetail -> ResponseItemDto.builder()
                .originalValue(transformationHistoryDetail.getOriginalValue())
                .processedValue(transformationHistoryDetail.getProcessedValue())
                .build())
            .toList();

        return ResponseDto.builder()
            .items(responseItemDtoList)
            .build();
    }

    private TransformationHistory buildTransformationHistory(String requestId) {
        return TransformationHistory.builder()
            .requestId(requestId)
            .build();
    }

    private List<TransformationHistoryDetail> buildTransformationHistoryDetailList(UUID historyId, ResponseDto responseDto) {
        return responseDto.getItems().stream()
            .map(responseItemDto -> TransformationHistoryDetail.builder()
                .historyId(historyId)
                .originalValue(responseItemDto.getOriginalValue())
                .processedValue(responseItemDto.getProcessedValue())
                .build())
            .toList();
    }
}
