package tests.lyceum.apitests.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import tests.lyceum.apitests.models.Lead;

@Data
@NoArgsConstructor
public class LeadDTO {
    public LeadDTO(Lead lead) {
        this.id = lead.getId();
        this.name = lead.getName();
        this.email = lead.getEmail();
        this.status = lead.getStatus();
        this.source = lead.getSource();
    }

    private Long id;
    private String name;
    private String email;
    private String status;
    private String source;
}