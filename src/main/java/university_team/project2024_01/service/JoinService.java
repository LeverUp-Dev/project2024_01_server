package university_team.project2024_01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import university_team.project2024_01.dto.JoinDTO;
import university_team.project2024_01.entity.Member;
import university_team.project2024_01.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String nickname = joinDTO.getNickname();
        String email = joinDTO.getEmail();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {

            return;
        }

        Member data = new Member();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setNickname(nickname);
        data.setEmail(email);
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
