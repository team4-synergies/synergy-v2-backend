package com.synergies.synergyv2.model.entity;

import com.synergies.synergyv2.auth.CustomUserDetails;
import com.synergies.synergyv2.auth.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="USER")
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String kakaoId;

    @Getter
    private String name;

    private String email;

    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    private String profileImage;

    public CustomUserDetails toCustomUserDetails(){
        return new CustomUserDetails(id, kakaoId, name, email, role, profileImage);
    }
    public void update(String name, String email, String profileImage){
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }
}
