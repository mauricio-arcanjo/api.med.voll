package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.model.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
/*
    UserDetailsService interface with @Service tells spring that this class is responsible for authentication in this application. It proccess automatically
    some authentication process.
 */
public class AuthenticationServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    //Spring automatically calls this method when authentication process is started
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //Checks in DB if user exists
        return userRepository.findByLogin(login);
    }
}
