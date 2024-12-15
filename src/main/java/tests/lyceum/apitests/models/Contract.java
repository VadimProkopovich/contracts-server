package tests.lyceum.apitests.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tests.lyceum.apitests.models.dto.ContractDTO;
import tests.lyceum.apitests.models.enums.ContractStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long leadId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContractStatus status = ContractStatus.DRAFT;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 500)
    private String description;

    @Column(nullable = true)
    private String documentLink;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public Contract(ContractDTO contractDTO) {
        this.leadId = contractDTO.getLeadId();
        this.status = contractDTO.getStatus();
        this.amount = contractDTO.getAmount();
        this.name = contractDTO.getName();
        this.description = contractDTO.getDescription();
        this.documentLink = contractDTO.getDocumentLink();
        this.createdAt = contractDTO.getCreatedAt();
        this.updatedAt = contractDTO.getUpdatedAt();
    }
}
