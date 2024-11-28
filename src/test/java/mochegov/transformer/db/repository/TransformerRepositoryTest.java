package mochegov.transformer.db.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import mochegov.transformer.db.model.Transformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TransformerRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TransformerRepository transformerRepository;

    @Test
    void findBy_ok() {
        final int groupId = 1;
        final int transformerId = 1;

        Optional<Transformer> optionalTransformer = transformerRepository.findBy(groupId, transformerId);

        assertThat(optionalTransformer.isPresent()).isTrue();
        Transformer transformer = optionalTransformer.get();

        assertAll(
            () -> assertThat(transformer.getGroupId()).isEqualTo(groupId),
            () -> assertThat(transformer.getTransformerId()).isEqualTo(transformerId)
        );
    }
}