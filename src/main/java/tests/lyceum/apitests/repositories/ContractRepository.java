package tests.lyceum.apitests.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tests.lyceum.apitests.models.Contract;

@RepositoryRestResource(exported = false)
public interface ContractRepository extends JpaRepository<Contract, Long> {
}