package co.wanted.board.api.member.domain;

import co.wanted.board.config.model.CreatedEntity;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CreatedEntity {

    @EmbeddedId
    private MemberId id;
    private String email;
    private String password;
    private String username;

    @Builder
    private Member(MemberId id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }


    @Embeddable @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberId implements Serializable {
        private Long id;

    }
}
