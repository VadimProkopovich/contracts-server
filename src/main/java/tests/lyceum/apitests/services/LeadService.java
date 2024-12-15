package tests.lyceum.apitests.services;

import org.springframework.stereotype.Service;
import tests.lyceum.apitests.generics.BaseService;
import tests.lyceum.apitests.models.Lead;
import tests.lyceum.apitests.models.dto.LeadDTO;
import tests.lyceum.apitests.repositories.LeadRepository;

@Service
public class LeadService extends BaseService<Lead, Long, LeadDTO> {
    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        super(leadRepository);
        this.leadRepository = leadRepository;
    }

    @Override
    protected LeadDTO convertToDTO(Lead entity) {
        return new LeadDTO(entity);
    }
}
