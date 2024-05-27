package br.com.picpay.domain.user;

import br.com.picpay.dtos.user.UserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

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
}
