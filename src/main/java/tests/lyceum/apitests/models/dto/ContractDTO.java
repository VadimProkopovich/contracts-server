package tests.lyceum.apitests.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import tests.lyceum.apitests.models.Contract;
import tests.lyceum.apitests.models.enums.ContractStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ContractDTO {
    public ContractDTO(Contract contract) {
        this.id = contract.getId();
        this.leadId = contract.getLeadId();
        this.status = contract.getStatus();
        this.amount = contract.getAmount();
        this.name = contract.getName();
        this.description = contract.getDescription();
        this.documentLink = contract.getDocumentLink();
        this.createdAt = contract.getCreatedAt();
        this.updatedAt = contract.getUpdatedAt();
    }

    private Long id;
    private Long leadId;
    private ContractStatus status;
    private Integer amount;
    private String name;
    private String description;
    private String documentLink;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
