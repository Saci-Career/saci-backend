package saci.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saci.application.model.ScoreRequest;
import saci.application.model.ScoreResponse;
import saci.domain.service.QuizService;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@Validated
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "Calculate the score for a role and return the current and next levels")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Score calculated successfully",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ScoreResponse.class))
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "Role not found",
                        content = @Content),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid request",
                        content = @Content)
            })
    @PostMapping("/evaluate-answers")
    public ResponseEntity<ScoreResponse> evaluateAnswer(@Valid @RequestBody ScoreRequest request) {
        ScoreResponse response = quizService.evaluateAnswer(request);
        return ResponseEntity.ok(response);
    }
}
