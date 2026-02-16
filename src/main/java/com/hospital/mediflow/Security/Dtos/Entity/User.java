package com.hospital.mediflow.Security.Dtos.Entity;

import com.hospital.mediflow.Security.Roles.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users",schema = "mediflow_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User{
    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "mediflow_schema.id_generator_seq",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    private Long id;

    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private Long resourceId;


    @Transient
    @PrePersist
    public void encryptPassword(){
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
         this.setPassword(encoder.encode(this.getPassword()));
    }
}
