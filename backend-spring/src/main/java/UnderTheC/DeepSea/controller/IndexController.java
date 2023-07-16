package UnderTheC.DeepSea.controller;

import UnderTheC.DeepSea.Entity.User;
import UnderTheC.DeepSea.dto.JsonResponse;
import UnderTheC.DeepSea.dto.LoginRequest;
import UnderTheC.DeepSea.dto.LoginResponse;
import UnderTheC.DeepSea.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

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
    public JsonResponse login(HttpServletRequest request, @RequestBody LoginRequest json) {
        /* 로그인 상태 확인 */
        if (request.getSession(false) != null) {
            return new JsonResponse("fail", "이미 로그인 되어 있습니다.");
        }

        /* json 데이터로 유저 정보 확인 */
        String id = json.getId();
        String password = json.getPassword();

        Optional<User> result = this.userRepository.findById(id);
        User loginUser = result.orElse(null);

        /* 아이디와 패스워드 확인 */
        if (loginUser == null || !loginUser.getPassword().equals(password)) {
            return new JsonResponse("fail", "아이디 또는 패스워드가 틀렸습니다.");
        }

        /* 로그인 성공 처리 신규 세션을 생성 */
        HttpSession session = request.getSession();
        /* 세션에 로그인 회원 정보 보관 */
        session.setAttribute("loginUser", loginUser);
        /* 세션 id는 쿠키에 JSESSIONID 값으로 저장됨 */

        return new LoginResponse("success", "로그인 성공", loginUser);
    }

    @GetMapping("logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API", responses = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    public JsonResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new JsonResponse("fail", "로그인 되어 있지 않습니다.");
        }
        session.invalidate();
        return new JsonResponse("success", "로그아웃 성공");
    }
}
