package co.wanted.board.api.post.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostContents {

    @Column(table = "post_contents")
    private String content;

    protected PostContents(String content) {
        this.content = content;
    }

    public static PostContents PostContents (String content) {
        return new PostContents(content);
    }
}
