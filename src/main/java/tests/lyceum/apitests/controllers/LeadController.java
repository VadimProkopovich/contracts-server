package tests.lyceum.apitests.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tests.lyceum.apitests.models.Lead;
import tests.lyceum.apitests.models.dto.LeadDTO;
import tests.lyceum.apitests.services.LeadService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/leads")
@AllArgsConstructor
@Tag(name = "Lead", description = "Operations pertaining to leads in Lead Management System")
public class LeadController {

    private final LeadService leadService;

    @GetMapping
    @Operation(summary = "Get all leads", description = "Get a list of all leads")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LeadDTO.class)))})
    public ResponseEntity<List<LeadDTO>> getAllLeads() {
        List<LeadDTO> leads = leadService.getAllDTO();
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a lead by ID", description = "Get a specific lead by their ID")
    @ApiResponse(responseCode = "200", description = "Lead found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LeadDTO.class))})
    @ApiResponse(responseCode = "404", description = "Lead not found")
    public ResponseEntity<LeadDTO> getLeadById(@PathVariable Long id) {
        Optional<LeadDTO> lead = leadService.getDTOById(id);
        return lead.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new lead", description = "Create a new lead")
    @ApiResponse(responseCode = "201", description = "Lead created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LeadDTO.class))})
    public ResponseEntity<Void> createLead(@RequestBody LeadDTO leadDTO) {
        leadService.create(new Lead(leadDTO));
        return ResponseEntity.status(201).body(null);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a lead", description = "Update an existing lead")
    @ApiResponse(responseCode = "200", description = "Lead updated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LeadDTO.class))})
    @ApiResponse(responseCode = "404", description = "Lead not found")
    public ResponseEntity<LeadDTO> updateLead(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        LeadDTO updatedLead = leadService.update(id, updates);
        return ResponseEntity.ok(updatedLead);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lead", description = "Delete a lead by their ID")
    @ApiResponse(responseCode = "204", description = "Lead deleted")
    @ApiResponse(responseCode = "404", description = "Lead not found")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}