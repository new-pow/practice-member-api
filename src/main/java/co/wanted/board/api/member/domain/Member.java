package co.wanted.board.api.member.domain;

import co.wanted.board.config.model.CreatedEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CreatedEntity {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private MemberId id;
    private String email;
    private String password;
    private String name;

    @Builder
    private Member(MemberId id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Member create(String email, String password, String name) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }

    public boolean isCorrectPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Embeddable @Getter
    @NoArgsConstructor
    public static class MemberId implements Serializable {

        private Long id;

        public MemberId(Long id) {
            this.id = id;
        }
    }
}
