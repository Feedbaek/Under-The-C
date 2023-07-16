package UnderTheC.DeepSea.controller;

import UnderTheC.DeepSea.Entity.User;
import UnderTheC.DeepSea.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "user API", description = "유저 정보 API")
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/id")
    @Operation(summary = "유저 정보 보기", description = "user 테이블에 저장된 ID로 유저 정보 반환", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    public User findById(@RequestBody HashMap<String, Object> json) {
        /* json 데이터로 유저 정보 확인 */
        String id = (String) json.get("id");

        Optional<User> user = userRepository.findById(id);

        /* 유저 테이블에 유저의 정보가 존재하면 유저 정보 반환, 아니면 400 오류 메시지 반환 */
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 아이디입니다.");
        }
    }

    /* 이메일 유효성 검사 메소드 */
    private void isEmailValidate(String email) {
        if (!(email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다.");
        }
    }

    /* 아이디 중복 검사 메소드 */
    private void isUserNotExist(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원가입 실패. 중복회원입니다.");
        }
    }

    @PostMapping("/add")
    @Operation(summary = "유저 추가", description = "user 테이블에 지정된 ID로 유저 추가", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 완료"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })

    public User addById(@RequestBody HashMap<String, Object> json) {
        /* json 데이터로 유저 정보 확인 */
        String id = (String) json.get("id");
        String password = (String) json.get("password");
        String email = (String) json.get("email");

        /* 아이디 중복 검사 */
        isUserNotExist(id);
        /* 이메일 유효성 검사 */
        isEmailValidate(email);

        /* 아이디가 중복되지 않고 이메일이 유효할 경우, 회원가입 진행 */
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    /* 유저 테이블 내 아이디의 존재 유무 검사 메소드 */
    private User isUserExist(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!(existingUser.isPresent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 아이디입니다.");
        }
        return existingUser.get();
    }

    @PatchMapping("/update")
    @Operation(summary = "유저 정보 수정", description = "user 테이블에 지정된 ID로 유저 정보 수정", responses = {
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "400", description = "수정 실패")
    })
    public User updateById(@RequestBody HashMap<String, Object> json) {
        /* json 데이터로 유저 정보 확인 */
        String id = (String) json.get("id");
        String password = (String) json.get("password");
        String email = (String) json.get("email");

        /* 유저 테이블 내 아이디의 존재 유무 검사 */
        User beforeUser = isUserExist(id);
        /* 이메일 유효성 검사 */
        isEmailValidate(email);

        /* 유저 테이블 내에 아이디가 존재하고 이메일이 유효할 경우, 회원 정보 수정 진행 */
        User afterUser = beforeUser;
        afterUser.setPassword(password);
        afterUser.setEmail(email);
        userRepository.save(afterUser);
        return afterUser;
    }

    @DeleteMapping("/delete")
    @Operation(summary = "유저 삭제", description = "user 테이블에 지정된 ID로 유저 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 완료"),
            @ApiResponse(responseCode = "400", description = "회원탈퇴 실패")
    })
    public User deleteById(@RequestBody HashMap<String, Object> json) {
        /* json 데이터로 유저 정보 확인 */
        String id = (String) json.get("id");
        String password = (String) json.get("password");

        Optional<User> user = userRepository.findById(id);

        /* 유저 테이블 내에 아이디가 존재하고 비밀번호가 일치하면 회원탈퇴 진행, 아니면 400 오류 메시지 반환  */
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            userRepository.deleteById(id);
            return user.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디나 비밀번호가 틀렸습니다.");
        }
    }
}