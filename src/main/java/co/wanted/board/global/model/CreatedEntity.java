package co.wanted.board.global.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class CreatedEntity {

    @CreatedDate
    private Instant createdAt;

    protected CreatedEntity(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
