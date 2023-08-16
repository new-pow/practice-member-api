package co.wanted.board.api.post.presentation;

import co.wanted.board.api.auth.presentation.dto.Logined;
import co.wanted.board.api.post.presentation.dto.PostPage;
import co.wanted.board.api.post.presentation.dto.PostSelect;
import co.wanted.board.api.post.presentation.dto.PostUpdate;
import co.wanted.board.api.post.presentation.dto.PostWrite;
import co.wanted.board.global.model.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RequestMapping("/api/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostFacade postFacade;

    @GetMapping
    public BasicResponse<PostPage.Response> getPostPage(PostPage.Request request) {
        PostPage.Response postPage = postFacade.getPostPage(request);
        return BasicResponse.send("글 목록이 조회되었습니다.", postPage);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BasicResponse<PostWrite.Response> writePost(@RequestAttribute Logined logined, @RequestBody PostWrite.Request request) throws AccessDeniedException {
        checkLogin(logined);
        PostWrite.Response response = postFacade.writePost(logined, request);
        return BasicResponse.send("글이 작성되었습니다.", response);
    }

    @GetMapping("/{id}")
    public BasicResponse<PostSelect.Response> getPost (@PathVariable long id) {
        PostSelect.Response post = postFacade.getPost(id);
        return BasicResponse.send("글을 조회했습니다.", post);
    }

    @PutMapping("/{id}")
    public BasicResponse<PostUpdate.Response> updatePost (@PathVariable long id, @RequestAttribute Logined logined, @RequestBody PostUpdate.Request request) throws AccessDeniedException {
        checkLogin(logined);
        PostUpdate.Response response = postFacade.updatePost(logined, id, request);
        return BasicResponse.send("글을 수정했습니다.", response);
    }

    @DeleteMapping("/{id}")
    public BasicResponse deletePost (@PathVariable long id, @RequestAttribute Logined logined) throws AccessDeniedException {
        checkLogin(logined);
        postFacade.deletePost(logined, id);
        return BasicResponse.send("글을 삭제 완료 했습니다.", id);
    }

    private void checkLogin(Logined logined) throws AccessDeniedException {
        if (logined.isEmpty()) {
            throw new AccessDeniedException("로그인이 필요한 기능입니다.");
        }
    }
}
