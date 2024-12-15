package tests.lyceum.apitests.services;

import org.springframework.stereotype.Service;
import tests.lyceum.apitests.generics.BaseService;
import tests.lyceum.apitests.models.User;
import tests.lyceum.apitests.models.dto.UserDTO;
import tests.lyceum.apitests.repositories.UserRepository;

@Service
public class UserService extends BaseService<User, Long, UserDTO> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    protected UserDTO convertToDTO(User entity) {
        return new UserDTO(entity);
    }

    public User authenticate(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
