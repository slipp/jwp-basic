package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class MyUserService {
    @Inject
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
