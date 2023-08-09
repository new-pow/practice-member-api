package co.wanted.board.api.member.presentation;

import co.wanted.board.api.member.application.MemberFacade;
import co.wanted.board.api.member.presentation.dto.Signup;
import co.wanted.board.global.model.BasicResponse;
import co.wanted.board.global.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "member", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberFacade memberFacade;

    @Operation(summary = "회원가입", description = "사용자는 회원가입을 할 수 있습니다.",
    responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일/회원 이름이 있습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "입력 형식이 옳지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<BasicResponse<Signup.Summary>> signup(@Valid @RequestBody Signup.Request request) {
        Signup.Summary result = memberFacade.signup(request);

        return ResponseEntity.ok()
                .body(BasicResponse.send("회원가입에 성공하였습니다.", result));
    }


    //TODO 로그인
}
