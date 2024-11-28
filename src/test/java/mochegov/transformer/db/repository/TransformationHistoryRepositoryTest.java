package mochegov.transformer.db.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Optional;
import java.util.UUID;
import mochegov.transformer.db.model.TransformationHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TransformationHistoryRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TransformationHistoryJooqRepository transformationHistoryRepository;

    @Test
    void add_history_ok() {
        String requestId = UUID.randomUUID().toString();

        TransformationHistory transformationHistoryExpected = TransformationHistory.builder()
            .requestId(requestId)
            .build();

        assertDoesNotThrow(() -> transformationHistoryRepository.add(transformationHistoryExpected));

        Optional<TransformationHistory> optionalTransformationHistory = transformationHistoryRepository.findBy(requestId);
        assertThat(optionalTransformationHistory.isPresent()).isTrue();
        TransformationHistory transformationHistory = optionalTransformationHistory.get();

        assertAll(
            () -> assertThat(transformationHistory.getRequestId()).isEqualTo(requestId)
        );
    }
}