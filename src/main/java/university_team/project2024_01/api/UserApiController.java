package university_team.project2024_01.api;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import university_team.project2024_01.domain.User;
import university_team.project2024_01.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("api/v1/users")
    public CreateUserResponse saveUserV1(@RequestBody @Valid User user) {
        Long id = userService.signUp(user);
        return new CreateUserResponse(id);
    }

    @Data
    static class CreateUserResponse {
        private Long id;

        public CreateUserResponse(Long id) {
            this.id = id;
        }
    }
}
