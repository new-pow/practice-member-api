package co.wanted.board.api.post.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostContents {

    @Column(table = "post_contents")
    @Basic(fetch = FetchType.LAZY)
    private String content;

    protected PostContents(String content) {
        this.content = content;
    }

    public static PostContents of (String content) {
        return new PostContents(content);
    }

    public void update(String contents) {
        this.content = contents;
    }
}
