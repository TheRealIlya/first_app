package by.academy.jee.web.service.auth;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.auth.UserPrincipal;
import by.academy.jee.model.person.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final by.academy.jee.web.service.Service service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Person person = service.getUserIfExist(username);
            return new UserPrincipal(person);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}
