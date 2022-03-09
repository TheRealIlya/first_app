package by.academy.jee.model.auth;

import by.academy.jee.model.person.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private final Person person;
    private final Collection<SimpleGrantedAuthority> authorities;

    public UserPrincipal(Person person) {
        this.person = person;
        authorities = new ArrayList<>();
        authorities.addAll(Stream.of(person.getRole())
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return person.getLogin();
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
