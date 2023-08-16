package co.wanted.board.api.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(table = "member_password")
    private String hash;
    @Column(columnDefinition = "BINARY(16)", table = "member_password")
    private byte[] salt;

    @Override
    public boolean equals(Object obj) {
        if (Password.class == obj.getClass()) {
            Password other = (Password) obj;
            return this.hash.equals(other.hash) && this.salt.equals(other.salt);
        }
        return false;
    }

    private Password(String hash, byte[] salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public static Password of (String hash, byte[] salt) {
        return new Password(hash, salt);
    }
}
