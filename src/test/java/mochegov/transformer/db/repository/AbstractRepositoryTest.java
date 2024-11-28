package mochegov.transformer.db.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@JooqTest
@Import({
    TransformerJooqRepository.class,
    TransformationHistoryJooqRepository.class
})
public class AbstractRepositoryTest {
    @Autowired
    protected DSLContext dsl;
}
