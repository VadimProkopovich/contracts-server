package tests.lyceum.apitests.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tests.lyceum.apitests.models.dto.LeadDTO;

@Data
@NoArgsConstructor
@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    private String source;

    public Lead(LeadDTO leadDTO) {
        this.name = leadDTO.getName();
        this.email = leadDTO.getEmail();
        this.status = leadDTO.getStatus();
        this.source = leadDTO.getSource();
    }
}
