package university_team.project2024_01.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import university_team.project2024_01.dto.CustomUserDetails;
import university_team.project2024_01.entity.Member;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 불러옴
        String authorization = request.getHeader("Authorization");

        //authorization에 token이 비어있거나 인증방식이 잘못 되었으면 -> 메소드 종료(Authorization 헤더 검증 로직)
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 숭수 토큰만 추출
        String token = authorization.split(" ")[1];

        //token 소멸 시간 검증 로직
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //token에서 username과 role 추출
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //Member를 생성하여 값을 매핑
        Member member = new Member();
        member.setUsername(username);
        // password의 경우 token에 담겨있지 않기에 임의값 매핑으로 db조회로 인한 성능문제를 해결
        member.setPassword("temppassword");
        member.setRole(role);

        //userDetails에 회원 정보 객체를 넣어준다
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //filterChain.doFilter(request, response);
    }
}
