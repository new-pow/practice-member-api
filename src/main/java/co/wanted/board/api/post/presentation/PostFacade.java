package co.wanted.board.api.post.presentation;

import co.wanted.board.api.auth.presentation.dto.Logined;
import co.wanted.board.api.member.application.SearchService;
import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.post.application.PostService;
import co.wanted.board.api.post.application.exception.PostException;
import co.wanted.board.api.post.domain.Post;
import co.wanted.board.api.post.presentation.dto.PostPage;
import co.wanted.board.api.post.presentation.dto.PostSelect;
import co.wanted.board.api.post.presentation.dto.PostUpdate;
import co.wanted.board.api.post.presentation.dto.PostWrite;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static co.wanted.board.api.member.application.exception.ErrorCode.NO_SUCH_MEMBER;
import static co.wanted.board.api.post.application.exception.ErrorCode.ANOTHER_AUTHOR;

@Component
@RequiredArgsConstructor
public class PostFacade {

    private final SearchService searchService;
    private final PostService postService;

    public PostWrite.Response writePost(Logined logined, PostWrite.Request request) {
        if (!searchService.isExistMember(logined.getMemberId())) {
            throw new MemberException(NO_SUCH_MEMBER);
        }
        Post savedPost = postService.write(logined.getMemberId(), request.getTitle(), request.getContents());
        return PostWrite.Response.of(savedPost);
    }

    public PostSelect.Response getPost(long id) {
        Post post = postService.getPost(id);
        Member author = searchService.getMemberById(post.getAuthorId());
        return PostSelect.Response.of(post, author);
    }

    public PostUpdate.Response updatePost(Logined logined, long id, PostUpdate.Request request) {
        Post post = postService.getPost(id);

        if (!post.isAuthor(logined.getMemberId())) {
            throw new PostException(ANOTHER_AUTHOR);
        }

        Post updatedPost = postService.updatePost(post, request.getTitle(), request.getContents());
        return PostUpdate.Response.of(updatedPost);
    }

    public void deletePost(Logined logined, long id) {
        Post post = postService.getPost(id);

        if (!post.isAuthor(logined.getMemberId())) {
            throw new PostException(ANOTHER_AUTHOR);
        }

        postService.deletePost(post);
    }

    public PostPage.Response getPostPage(PostPage.Request request) {
        Page<Post> postPage = postService.getPostPage(request.getLast());
        List<Post> posts = postPage.getContent();

        List<PostPage.PostSummary> allMembersByPosts = getAllMembersByPosts(posts);
        return PostPage.Response.of(postPage, allMembersByPosts);
    }

    private List<PostPage.PostSummary> getAllMembersByPosts(List<Post> posts) {
        Map<Long, Post> postMap = new HashMap<>();
        posts.forEach(e -> postMap.put(e.getId(), e));

        Set<Long> authorIds = postMap.values().stream().map(Post::getAuthorId)
                .collect(Collectors.toSet());

        Map<Long, Member> memberMap = new HashMap<>();
        searchService.getAllMembersByIds(authorIds)
                .forEach(e -> memberMap.put(e.getId(), e));

        return posts.stream()
                .map(e -> PostPage.PostSummary.fromDomain(postMap.get(e.getId()), memberMap.get(e.getAuthorId())))
                .collect(Collectors.toList());
    }
}
