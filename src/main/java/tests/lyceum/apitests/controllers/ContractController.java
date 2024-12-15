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
import tests.lyceum.apitests.models.Contract;
import tests.lyceum.apitests.models.dto.ContractDTO;
import tests.lyceum.apitests.services.ContractService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
@AllArgsConstructor
@Tag(name = "Contract", description = "Operations pertaining to contracts in Lead Management System")
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    @Operation(summary = "Get all contracts", description = "Get a list of all contracts")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ContractDTO.class)))})
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        List<ContractDTO> contracts = contractService.getAllDTO();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a contract by ID", description = "Get a specific contract by its ID")
    @ApiResponse(responseCode = "200", description = "Contract found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ContractDTO.class))})
    @ApiResponse(responseCode = "404", description = "Contract not found")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Long id) {
        Optional<ContractDTO> contract = contractService.getDTOById(id);
        return contract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new contract", description = "Create a new contract")
    @ApiResponse(responseCode = "201", description = "Contract created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ContractDTO.class))})
    public ResponseEntity<Void> createContract(@RequestBody ContractDTO contractDTO) {
        contractService.create(new Contract(contractDTO));
        return ResponseEntity.status(201).body(null);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a contract", description = "Update an existing contract")
    @ApiResponse(responseCode = "200", description = "Contract updated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ContractDTO.class))})
    @ApiResponse(responseCode = "404", description = "Contract not found")
    public ResponseEntity<ContractDTO> updateContract(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ContractDTO updatedContract = contractService.update(id, updates);
        return ResponseEntity.ok(updatedContract);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a contract", description = "Delete a contract by its ID")
    @ApiResponse(responseCode = "204", description = "Contract deleted")
    @ApiResponse(responseCode = "404", description = "Contract not found")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.delete(id);
        return ResponseEntity.noContent().build();
    }
}