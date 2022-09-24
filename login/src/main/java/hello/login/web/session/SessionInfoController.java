package hello.login.web.session;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SessionInfoController {
    
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session == null) {
            return "세션이 없습니다.";
        }

        // 세션 데이터 출력
        session.getAttributeNames().asIterator()
            .forEachRemaining(name -> 
                log.info("session name = {}, value= {}", name, session.getAttribute(name)));

        /**
         * sessionId : 세션 id, jessionId의 값
         * maxInactiveInterval : 세션의 유효 시간
         * creationTime : 세션 생성일시
         * lstAccessedTime : 세션과 연결된 사용자가 최근에 서버에 접근한 시간
         * isNew : 새로 생성된 세션인지, 아니면 이미 과거에 만들어 졌고, 클라이언트에서 서버로 sessionId를 요청해서
         *      조회된 세션인지 여부
         */
        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

        return "세션 출력";
    }

    /**
     * 세션 시간 무한정일 경우
     * 
     * 세션과 관련된 쿠키를 탈취 됐을경우 해당 쿠키로 악의적인 요청을 할 수있다
     * 세션은 기본적으로 메모리에 생성된다. 그래서 필요한 경우에만 생성해야 한다
     * 
     * 세션 종료 시점
     * 사용자가 서버에 최근에 요펑한 시간을 기준으로 30분 정도를 유지해주는 것이다.
     * 
     * 세션 타임아웃 설정
     * 스프링부트로 글로벌 설정은 application.properties
     * server.servlet.session.timeout=60
     * 글로벌 설정은 분 단위로 설정해야한다.
     * 
     * 특정 세션 단위로 시간 설정
     * session.setMaxInactiveInterval(1800)
     */

}
