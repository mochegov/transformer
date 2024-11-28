package mochegov.transformer.db.repository;

import static mochegov.transformer.jooq.domain.tables.Transformer.TRANSFORMER_;

import java.util.Optional;
import mochegov.transformer.db.model.Transformer;
import mochegov.transformer.enums.TransformerKind;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.springframework.stereotype.Repository;

@Repository
public class TransformerRepository extends BaseJooqRepository<Transformer> {
    public TransformerRepository(DSLContext dslContext) {
        super(dslContext);
    }

    @Override
    protected RecordMapper<Record, Transformer> getRecordMapper() {
        return rec -> Transformer.builder()
            .id(rec.get(TRANSFORMER_.ID))
            .groupId(rec.get(TRANSFORMER_.GROUP_ID))
            .transformerId(rec.get(TRANSFORMER_.TRANSFORMER_ID))
            .name(rec.get(TRANSFORMER_.TRANSFORMER_NAME))
            .kind(TransformerKind.valueOf(rec.get(TRANSFORMER_.TRANSFORMER_CODE)))
            .active(rec.get(TRANSFORMER_.IS_ACTIVE))
            .createdAt(rec.get(TRANSFORMER_.CREATED_AT))
            .updatedAt(rec.get(TRANSFORMER_.UPDATED_AT))
            .build();
    }

    @Override
    protected Table<?> getTable() {
        return TRANSFORMER_;
    }

    @Override
    protected TableRecord<?> buildRecordForBatchInsert(Transformer transformer) {
        return null;
    }

    public Optional<Transformer> findBy(Integer groupId, Integer transformerId) {
        return getOne(TRANSFORMER_.GROUP_ID.eq(groupId)
            .and(TRANSFORMER_.TRANSFORMER_ID.eq(transformerId)));
    }
}
