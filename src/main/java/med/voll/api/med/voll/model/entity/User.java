package med.voll.api.med.voll.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
//It's mandatory to implement interface UserDetails and its methods to allow all spring authentication methods and process to run correctly
//All return from methods were changed, check it.
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    //All passwords are saved in DB using a encryption method (here using BCrypt) and a @Bean configuration is needed in class SecurityConfiguration.
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Role of user in the system.. eg. ROLE_ADMIN, ROLE_USER. This is set as constant for the tests but this info needs to come from DB
        //A new class is needed to control all those role profiles
//        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN")); //To test Always use prefix "ROLE_" before all roles, eg: ROLE_ADMIN or ROLE_USER
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /*
        All set to true to tests and if there's no need to control those params.
     */
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
