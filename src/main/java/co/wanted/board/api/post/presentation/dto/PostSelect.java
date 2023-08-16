package co.wanted.board.api.post.presentation.dto;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

public class PostSelect {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response{

        private Long id;
        private AuthorSummary author;
        private String title;
        private String content;
        private Instant createdAt;

        @Builder
        protected Response(Long id, Long authorId, String authorname, String title, String content, Instant createdAt) {
            this.id = id;
            this.author = new AuthorSummary(authorId, authorname);
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
        }

        public static Response of(Post post, Member member) {
            return Response.builder()
                    .id(post.getId())
                    .authorId(post.getAuthorId())
                    .authorname(member.getUsername())
                    .title(post.getTitle())
                    .content(post.getPostContent().getContent())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}
