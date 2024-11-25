package mochegov.transformer.errors;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMessage implements Serializable {
    private Integer errorCode;
    private String errorDescription;
}
