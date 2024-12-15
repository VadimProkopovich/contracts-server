package tests.lyceum.apitests.services;

import org.springframework.stereotype.Service;
import tests.lyceum.apitests.generics.BaseService;
import tests.lyceum.apitests.models.Contract;
import tests.lyceum.apitests.models.dto.ContractDTO;
import tests.lyceum.apitests.repositories.ContractRepository;

@Service
public class ContractService extends BaseService<Contract, Long, ContractDTO> {
    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        super(contractRepository);
        this.contractRepository = contractRepository;
    }

    @Override
    protected ContractDTO convertToDTO(Contract entity) {
        return new ContractDTO(entity);
    }
}