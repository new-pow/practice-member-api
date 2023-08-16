package co.wanted.board.api.member.presentation;

import co.wanted.board.api.member.presentation.dto.Signin;
import co.wanted.board.api.member.presentation.dto.Signup;
import co.wanted.board.global.model.BasicResponse;
import co.wanted.board.global.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "member", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberFacade memberFacade;

    @Operation(summary = "회원가입", description = "사용자는 회원가입을 할 수 있습니다.",
        responses = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일/회원 이름이 있습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "입력 형식이 옳지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BasicResponse<Signup.Summary> signup(@Valid @RequestBody Signup.Request request) {
        Signup.Summary result = memberFacade.signNewMember(request);

        return BasicResponse.send("회원가입에 성공하였습니다.", result);
    }

    @Operation(summary = "로그인", description = "사용자는 로그인을 할 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "401", description = "해당하는 회원이 없습니다.\n혹은 비밀번호가 옳지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            })
    @PostMapping("/signin")
    public BasicResponse<Signin.Response> signin(@Valid @RequestBody Signin.Request request, HttpServletResponse response) throws IOException {
        Signin.Response result = memberFacade.memberAuth(request, response);
        return BasicResponse.send("로그인에 성공하였습니다.", result);
    }
}
