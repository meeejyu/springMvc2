package hello.login.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    // @GetMapping("/")
    public String home() {
        return "home";
    }

    // @GetMapping("/")
    //required = false 로그인 안한 사용자도 들어와야됨
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if(memberId == null) {
            return "home";
        }

        // 로그인
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    // @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);


        // 로그인
        if(member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    // 세션은 메모리를 사용하는 것이기 떄문에 반드시 필요할때만 생성
    // @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 세션이 없으면 home
        // 처음 들어온 사용자를 세션 만들어줄 필요가 없기 때문에 false
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 home
        if(loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }


        /*
         * @SessionAttribute
         * 이미 로그인 된 사용자를 찾을때는 아래와 같이 사용하면 된다.
         * 참고로 이 기능은 세션을 생성하지 않는다
         */
        @GetMapping("/")
        public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)
            Member loginMember, Model model) {
    
            // 세션에 회원 데이터가 없으면 home
            if(loginMember == null) {
                return "home";
            }
    
            // 세션이 유지되면 로그인으로 이동
            model.addAttribute("member", loginMember);
            return "loginHome";
        }

}