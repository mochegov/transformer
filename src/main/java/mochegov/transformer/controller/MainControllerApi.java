package mochegov.transformer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import mochegov.transformer.dto.RequestDto;
import mochegov.transformer.dto.ResponseDto;
import mochegov.transformer.errors.ErrorMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Validated
@RequestMapping("/transformer/api")
@Tag(name = "Transformation operations", description = "String List Transformation Operations")
public interface MainControllerApi {
    String REQUEST_ID_HEADER = "requestId";
    String CORRELATION_ID_HEADER = "correlationId";


    /**
     * POST /v1/transform : Transforming a list of strings.
     *
     * @param requestId  Request identifier. Needed to link the request with the response. (required)
     * @param requestDto RequestDto (required)
     * @return Ok (status code 200)
     * or Bad request (status code 400)
     * or Not Found - error message when data not found (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "transform",
        summary = "Transforming a list of strings",
        tags = {"Transformation operations"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found - error message when data not found", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))
            })
        }
    )
    @PostMapping({"/v1/transform"})
    ResponseEntity<ResponseDto> transform(
        @NotNull
        @Parameter(
            name = REQUEST_ID_HEADER,
            description = "Request identifier. Needed to link the request with the response.",
            required = true,
            in = ParameterIn.HEADER)
        @RequestHeader(value = REQUEST_ID_HEADER)
        UUID requestId,

        @Valid
        @RequestBody
        @Parameter(
            name = "RequestDto",
            description = "List of strings to process",
            required = true)
        RequestDto requestDto
    );


    /**
     * GET /v1/transformation-history/{requestId} : Get transformation data by original requestId.
     *
     * @param requestId          original requestId. (required)
     * @return OK (status code 200)
     *      or Bad request - (status code 400)
     *      or Not Found - (status code 404)
     *      or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "getTransformationHistoryByRequestId",
        summary = "Get transformation history by original requestId",
        tags = {"Transformation operations"},
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found - error message when data not found", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
            })
        }
    )
    @GetMapping("/v1/transformation-history/{requestId}")
    ResponseEntity<ResponseDto> getTransformationHistoryByRequestId(
        @Size(min = 36, max = 36)
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
        @Parameter(
            name = "requestId",
            description = "original requestId",
            required = true,
            in = ParameterIn.PATH)
        @PathVariable("requestId")
        String requestId
    );
}
