package br.com.picpay.domain.user;

import br.com.picpay.dtos.user.UserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserRequestDTO userRequestDTO) {
        this.fullName = userRequestDTO.fullName();
        this.document = userRequestDTO.document();
        this.email = userRequestDTO.email();
        this.password = userRequestDTO.password();
        this.balance = userRequestDTO.balance();
        this.userType = userRequestDTO.userType();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userType == UserType.COMMON) return List.of(new SimpleGrantedAuthority("ROLE_COMMON"), new SimpleGrantedAuthority("ROLE_MERCHANT"));
        else return List.of(new SimpleGrantedAuthority("ROLE_MERCHANT"));
    }

    @Override
    public String getUsername() {
        return email;
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
