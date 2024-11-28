package mochegov.transformer.db.repository;

import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.springframework.stereotype.Repository;

/**
 * Abstract repository for CRUD operations with jOOQ.
 *
 * @param <M> model type
 */
@Slf4j
@Repository
public abstract class BaseJooqRepository<M> {
    protected final DSLContext dslContext;

    protected Table<?> table;
    protected RecordMapper<Record, M> recordMapper;

    protected BaseJooqRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
        this.table = getTable();
        this.recordMapper = getRecordMapper();
    }

    protected abstract RecordMapper<Record, M> getRecordMapper();

    protected abstract Table<?> getTable();

    /**
     * Retrieving a single record by a given condition.
     *
     * @param conditions conditions for fetching one record
     * @return model class instance
     */
    public Optional<M> getOne(@NonNull Condition... conditions) {
        return Optional.ofNullable(dslContext.select().from(table).where(conditions).fetchOne())
            .map(rec -> rec.map(recordMapper));
    }

    /**
     * Retrieving records by a given condition.
     *
     * @param condition condition for fetching records
     * @return list of model class instance
     */
    public List<M> getList(@NonNull Condition condition) {
        return dslContext.select().from(table).where(condition).fetch().map(recordMapper);
    }

    /**
     * Inserting row into table.
     *
     * @param fieldValueMap map of inserted fields and fields values
     * @return model class instance with primary key
     */
    protected Optional<M> insert(@NonNull @NotEmpty Map<Field<?>, Object> fieldValueMap) {
        return Optional.ofNullable(dslContext.insertInto(table)
            .set(fieldValueMap)
            .returning()
            .fetchOne()).map(rec -> rec.map(recordMapper));
    }

    /**
     * Table row update.
     *
     * @param condition     condition for fetching rows to update
     * @param jooqRecord object with updated fields
     * @return model class instance with primary a key
     */
    protected Optional<M> update(@NonNull Condition condition, @NonNull Record jooqRecord) {
        return Optional.ofNullable(dslContext.update(table)
            .set(jooqRecord)
            .where(condition)
            .returning()
            .fetchOne()).map(rec -> rec.map(recordMapper));
    }

    /**
     * Table rows delete.
     *
     * @param condition condition for fetching rows to update
     * @return number of deleted records
     */
    protected int delete(@NonNull Condition condition) {
        return dslContext.delete(table)
            .where(condition)
            .execute();
    }

    /**
     * Batch inserting.
     *
     * @param entities list of instances of the model class
     */
    public void batchInsert(List<M> entities) {
        List<TableRecord<?>> records = new ArrayList<>();
        entities.forEach(entity -> {
            TableRecord<?> record = buildRecordForBatchInsert(entity);
            records.add(record);
        });

        records.forEach(r -> Arrays.stream(r.fields())
            .filter(field -> Objects.isNull(field.getDataType().defaultValue()))
            .filter(field -> !r.changed(field))
            .forEach(field -> r.changed(field, true)));

        dslContext.batchInsert(records).execute();
    }

    protected abstract TableRecord<?> buildRecordForBatchInsert(M entity);
}
