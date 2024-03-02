package university_team.project2024_01.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import university_team.project2024_01.dto.JoinDTO;
import university_team.project2024_01.service.JoinService;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody @Valid JoinDTO joinDTO) {

        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
