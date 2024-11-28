package mochegov.transformer.db.repository;

import static mochegov.transformer.jooq.domain.tables.TransformationHistory.TRANSFORMATION_HISTORY;

import java.util.Map;
import java.util.Optional;
import mochegov.transformer.db.model.TransformationHistory;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.springframework.stereotype.Repository;

@Repository
public class TransformationHistoryJooqRepository extends BaseJooqRepository<TransformationHistory> {
    public TransformationHistoryJooqRepository(DSLContext dslContext) {
        super(dslContext);
    }

    @Override
    protected RecordMapper<Record, TransformationHistory> getRecordMapper() {
        return rec -> TransformationHistory.builder()
            .id(rec.get(TRANSFORMATION_HISTORY.ID))
            .requestId(rec.get(TRANSFORMATION_HISTORY.REQUEST_ID))
            .processedAt(rec.get(TRANSFORMATION_HISTORY.PROCESSED_AT))
            .build();
    }

    @Override
    protected Table<?> getTable() {
        return TRANSFORMATION_HISTORY;
    }

    @Override
    protected TableRecord<?> buildRecordForBatchInsert(TransformationHistory transformationHistory) {
        return null;
    }

    public Optional<TransformationHistory> add(TransformationHistory transformationHistory) {
        return insert(Map.of(TRANSFORMATION_HISTORY.REQUEST_ID, transformationHistory.getRequestId()));
    }

    public Optional<TransformationHistory> findBy(String requestId) {
        return getOne(TRANSFORMATION_HISTORY.REQUEST_ID.eq(requestId));
    }
}
