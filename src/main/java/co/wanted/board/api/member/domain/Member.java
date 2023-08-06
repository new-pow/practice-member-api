package co.wanted.board.api.member.domain;

import co.wanted.board.global.model.CreatedEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;

    @Builder
    private Member(Long id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static Member create(String email, String password, String username) {
        return Member.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
    }

    public boolean isCorrectPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
