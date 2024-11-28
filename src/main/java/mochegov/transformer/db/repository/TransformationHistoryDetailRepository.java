package mochegov.transformer.db.repository;

import static mochegov.transformer.jooq.domain.tables.TransformationHistoryDetail.TRANSFORMATION_HISTORY_DETAIL;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mochegov.transformer.db.model.TransformationHistoryDetail;
import mochegov.transformer.errors.ErrorType;
import mochegov.transformer.errors.FieldValidationException;
import mochegov.transformer.jooq.domain.tables.records.TransformationHistoryDetailRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.springframework.stereotype.Repository;

@Repository
public class TransformationHistoryDetailRepository extends BaseJooqRepository<TransformationHistoryDetail> {
    public TransformationHistoryDetailRepository(DSLContext dslContext) {
        super(dslContext);
    }

    @Override
    protected RecordMapper<Record, TransformationHistoryDetail> getRecordMapper() {
        return rec -> TransformationHistoryDetail.builder()
            .id(rec.get(TRANSFORMATION_HISTORY_DETAIL.ID))
            .historyId(rec.get(TRANSFORMATION_HISTORY_DETAIL.HISTORY_ID))
            .originalValue(rec.get(TRANSFORMATION_HISTORY_DETAIL.ORIGINAL_VALUE))
            .processedValue(rec.get(TRANSFORMATION_HISTORY_DETAIL.PROCESSED_VALUE))
            .build();
    }

    @Override
    protected Table<?> getTable() {
        return TRANSFORMATION_HISTORY_DETAIL;
    }

    @Override
    protected TableRecord<?> buildRecordForBatchInsert(TransformationHistoryDetail transformationHistoryDetail) {
        Optional.ofNullable(transformationHistoryDetail.getHistoryId()).orElseThrow(() ->
            new FieldValidationException("It is not allowed to insert records for which the history id is null",
                ErrorType.VALIDATION_ERRORS));

        TransformationHistoryDetailRecord record = new TransformationHistoryDetailRecord();
        record.setHistoryId(transformationHistoryDetail.getHistoryId());
        record.setOriginalValue(transformationHistoryDetail.getOriginalValue());
        record.setProcessedValue(transformationHistoryDetail.getProcessedValue());
        return record;
    }

    public List<TransformationHistoryDetail> findBy(UUID historyId) {
        return getList(TRANSFORMATION_HISTORY_DETAIL.HISTORY_ID.eq(historyId));
    }
}
