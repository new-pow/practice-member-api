package co.wanted.board.api.member.presentation;

import co.wanted.board.api.member.application.MemberFacade;
import co.wanted.board.api.member.presentation.dto.Signup;
import co.wanted.board.global.model.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberFacade memberFacade;

    @PostMapping
    public ResponseEntity<BasicResponse> signup(@Valid @RequestBody Signup.Request request) {
        Signup.Summary result = memberFacade.signup(request);

        return ResponseEntity.ok()
                .body(BasicResponse.send("회원가입에 성공하였습니다.", result));
    }


    //TODO 로그인
}
