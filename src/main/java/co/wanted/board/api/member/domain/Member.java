package co.wanted.board.api.member.domain;

import co.wanted.board.global.model.CreatedEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SecondaryTable(name = "member_password", pkJoinColumns = @PrimaryKeyJoinColumn(name = "member_id"))
public class Member extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    @Embedded
    private Password password = new Password();
    private String username;

    @Builder
    private Member(Long id, String email, Password password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static Member create(String email, Password encodedPassword, String username) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .username(username)
                .build();
    }

    public boolean isCorrectPassword(Password inputPassword) {
        return this.password.equals(inputPassword);
    }

    public byte[] getSalt() {
        return this.password.getSalt();
    }

    public String getEncryptedPassword() {
        return this.password.getHash();
    }
}
