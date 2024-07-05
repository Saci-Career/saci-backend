package saci.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import saci.domain.model.Level;
import saci.domain.service.LevelService;
import saci.domain.service.exceptions.CoefficientOverlapException;

@Slf4j
@RestController
@RequestMapping("/api/levels")
@RequiredArgsConstructor
public class LevelController {

    private final LevelService levelService;

    @GetMapping("/{levelId}")
    public ResponseEntity<Level> getLevelById(@PathVariable Long levelId) {
        Level level = levelService.getLevelById(levelId);
        return ResponseEntity.ok(level);
    }

    @Operation(summary = "Delete Level by ID", description = "Deletes a Level based on its ID")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Level deleted successfully"),
                @ApiResponse(responseCode = "404", description = "Level not found")
            })
    @DeleteMapping("/{levelId}")
    public ResponseEntity<Void> deleteLevelById(@PathVariable long levelId) {
        levelService.deleteLevelById(levelId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Create a Level")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "The Level was created",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Level.class))
                        }),
                @ApiResponse(responseCode = "400", description = "Coefficient Overlap")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Level> createLevel(@RequestBody Level level) {
        try {
            levelService.createLevel(level);
            return ResponseEntity.ok().build();
        } catch (CoefficientOverlapException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Edit a level",
            description = "Edit an existing level with the provided level ID")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Level edited successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid input data"),
                @ApiResponse(responseCode = "404", description = "Level not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PutMapping("/{levelId}")
    public ResponseEntity<Level> editLevel(
            @PathVariable Long levelId, @Valid @RequestBody Level updatedLevel) {
        Level editedLevel = levelService.editLevel(levelId, updatedLevel);
        return ResponseEntity.ok(editedLevel);
    }

    @Operation(summary = "Get the sorted Levels")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Levels sorted",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Level.class))
                        }),
                @ApiResponse(responseCode = "404", description = "Level not found")
            })
    @GetMapping("/sorted/{roleId}")
    public ResponseEntity<List<Level>> getSortedLevels(@PathVariable Long roleId) {
        List<Level> levels = levelService.getSortedLevelsByRoleIdAsc(roleId);
        return ResponseEntity.ok(levels);
    }
}
