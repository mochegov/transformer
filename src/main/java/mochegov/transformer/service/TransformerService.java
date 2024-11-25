package mochegov.transformer.service;

import lombok.RequiredArgsConstructor;
import mochegov.transformer.db.model.Transformer;
import mochegov.transformer.db.repository.TransformerRepository;
import mochegov.transformer.errors.ErrorType;
import mochegov.transformer.errors.NotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransformerService {
    private final TransformerRepository transformerRepository;

    public Transformer findBy(Integer groupId, Integer transformerId) {
        return transformerRepository.findBy(groupId, transformerId)
            .orElseThrow(() -> new NotFoundException(
                "Transformer not found by group id (%s) and transformer id (%s)".formatted(groupId, transformerId),
                ErrorType.TRANSFORMER_NOT_FOUND));
    }
}
