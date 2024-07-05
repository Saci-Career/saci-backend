package saci.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import saci.domain.model.Knowledge;
import saci.domain.service.KnowledgeService;
import saci.domain.service.exceptions.AlreadyExistsException;
import saci.domain.service.exceptions.NotFoundException;

@RestController
@RequestMapping("/api/knowledges")
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @Operation(summary = "Create a knowledge")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "The knowledge was created",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Knowledge.class))
                        })
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Knowledge createKnowledge(@RequestBody Knowledge knowledge) {
        return knowledgeService.createKnowledge(knowledge);
    }

    @Operation(summary = "Get all of the knowledges")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "List of all knowledges",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    array =
                                            @ArraySchema(
                                                    schema =
                                                            @Schema(
                                                                    implementation =
                                                                            Knowledge.class)))
                        })
            })
    @GetMapping
    public ResponseEntity<List<Knowledge>> getKnowledges() {
        List<Knowledge> knowledges = knowledgeService.getKnowledges();

        return knowledges.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(knowledges);
    }

    @Operation(
            summary = "Delete knowledge by ID",
            description = "Deletes a knowledge based on its ID")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Knowledge deleted successfully"),
                @ApiResponse(responseCode = "404", description = "Knowledge not found")
            })
    @DeleteMapping("/{knowledgeId}")
    public ResponseEntity<Void> deleteKnowledgeById(@PathVariable long knowledgeId) {
        try {
            knowledgeService.deleteKnowledgeById(knowledgeId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Edit an existing knowledge entry")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Knowledge updated successfully"),
                @ApiResponse(responseCode = "404", description = "Knowledge not found"),
                @ApiResponse(responseCode = "409", description = "Knowledge name already exists")
            })
    @PutMapping("/{knowledgeId}")
    public ResponseEntity<Void> editKnowledge(
            @PathVariable Long knowledgeId, @Valid @RequestBody Knowledge updatedKnowledge) {
        try {
            knowledgeService.editKnowledge(knowledgeId, updatedKnowledge);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (AlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
