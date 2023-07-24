package UnderTheC.DeepSea.controller;

import UnderTheC.DeepSea.Entity.User;
import UnderTheC.DeepSea.dto.LoginRequest;
import UnderTheC.DeepSea.dto.LoginResponse;
import UnderTheC.DeepSea.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Tag(name = "index", description = "메인화면 API")
@RequestMapping("/")
public class IndexController {
    private UserRepository userRepository;

    IndexController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    @Operation(summary = "Hello!", description = "처음으로 만든 API", responses = {
            @ApiResponse(responseCode = "200", description = "야호! 성공!!!")
    })
    public String index() {
        return "Hello Guys!\n" +
                "Welcome to the Deep C!";
    }

    @PostMapping("login")
    @Operation(summary = "로그인", description = "로그인 API", responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    })
    public LoginResponse login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequest json) {
        /* 로그인 상태 확인 */
        if (request.getSession(false) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 로그인 되어 있습니다.");
        }

        /* json 데이터로 유저 정보 확인 */
        String id = json.getId();
        String password = json.getPassword();

        Optional<User> result = this.userRepository.findById(id);
        User loginUser = result.orElse(null);

        /* 아이디와 패스워드 확인 */
        if (loginUser == null || !loginUser.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디 또는 패스워드가 틀렸습니다.");
        }

        /* 로그인 성공 처리 신규 세션을 생성 */
        HttpSession session = request.getSession();
        /* 세션에 로그인 회원 정보 보관 */
        session.setAttribute("loginUser", loginUser);
        /* 세션 id는 쿠키에 JSESSIONID 으로 저장됨 */
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", session.getId())
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(1800)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return new LoginResponse("success", "로그인 성공", loginUser);
    }

    @GetMapping("logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API", responses = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    public LoginResponse logout(HttpServletRequest request) {
        /* 로그인 상태 확인 */
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 되어 있지 않습니다.");
        }
        User loginUser = (User) session.getAttribute("loginUser");

        /* 세션 제거해서 로그아웃 */
        session.invalidate();
        return new LoginResponse("success", "로그아웃 성공", loginUser);
    }
}
