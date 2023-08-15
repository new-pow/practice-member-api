package co.wanted.board.api.post.domain;

import co.wanted.board.api.auth.presentation.dto.Logined;
import co.wanted.board.global.model.ModifiedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SecondaryTable(name = "post_contents", pkJoinColumns = @PrimaryKeyJoinColumn(name = "post_id"))
public class Post extends ModifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long authorId;
    @Embedded
    private PostContents postContent;

    @Builder
    protected Post(Long id, String title, Long authorId, PostContents postContent) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.postContent = postContent;
    }

    public static Post of(String title, Long memberId, String contents) {
        return Post.builder()
                .title(title)
                .authorId(memberId)
                .postContent(PostContents.of(contents))
                .build();
    }

    public void update(String title, String contents) {
        this.title = title;
        this.postContent.update(contents);
    }

    public boolean isAuthor(long memberId) {
        return this.authorId == memberId;
    }
}
