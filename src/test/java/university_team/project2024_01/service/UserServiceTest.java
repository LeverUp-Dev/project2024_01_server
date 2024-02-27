package university_team.project2024_01.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import university_team.project2024_01.domain.User;
import university_team.project2024_01.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입 () throws Exception {
        //given
        User user = new User();
        user.setUsername("song");

        //when
        Long saveId = userService.signUp(user);

        //then
        Assertions.assertEquals(user, userRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외 () throws Exception {
        //given
        User user1 = new User();
        user1.setUsername("song1");

        User user2 = new User();
        user2.setUsername("song1");

        //when
        userService.signUp(user1);

        try {
            userService.signUp(user2); //예외 발생
        } catch (IllegalStateException is){
            return;
        }

        //then
        Assertions.fail("예외가 발생해야 합니다.");
    }
}