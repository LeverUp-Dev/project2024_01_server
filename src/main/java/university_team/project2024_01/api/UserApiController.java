package university_team.project2024_01.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import university_team.project2024_01.domain.User;
import university_team.project2024_01.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("api/v2/users")
    public Result userV2() {
        List<User> findUsers = userService.findUsers();
        List<UserDTO> collect = findUsers.stream()
                .map(u -> new UserDTO(u.getUsername()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class UserDTO {
        private String nickname;
    }

    /**
     * 회원등록 API 엔티티를 직접적으로 노출시키는 방식이므로 사용을 지양해 주세요.
     * @param user
     * @return
     */
    @PostMapping("api/v1/users")
    public CreateUserResponse saveUserV1(@RequestBody @Valid User user) {
        Long id = userService.signUp(user);
        return new CreateUserResponse(id);
    }

    /**
     * 회원등록 API nickname, userId, password 입력받고 User의 id를 반환합니다.
     * @param request
     * @return
     */
    @PostMapping("api/v2/users")
    public CreateUserResponse saveUserV2(@RequestBody @Valid CreateUserRequest request) {

        User user = new User();
        user.setUsername(request.getNickname());
        user.setUserId(request.getUserId());
        user.setPassword(request.getPassword());

        Long id = userService.signUp(user);
        return new CreateUserResponse(id);
    }

    @Data
    static class CreateUserRequest {
        @NotEmpty
        private String nickname;
        @NotEmpty
        private String userId;
        @NotEmpty
        private String password;
    }

    @Data
    static class CreateUserResponse {
        private Long id;

        public CreateUserResponse(Long id) {
            this.id = id;
        }
    }
}
