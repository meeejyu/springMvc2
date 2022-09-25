package hello.login.web.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;

// 공통 작업이 필요할때 사용하면 좋음
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver{
    
    // @Login 애노테이션이 있으면서 Member 타입이면 해당 ArgumentResolver가 사용된다
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        
        log.info("supportsParameter 실행");

        // 파라미터 정보에서 login 어노테이션이 있는지 확인
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        
        // Member 타입인지 확인
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    // 컨트롤러 호출 직전에 호출되어서 필요한 파라미터 정보를 생성해준다, Member 객체를 반환해준다
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        
        log.info("resolverArgument 실행");
        
        // request 가져오기
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        
        HttpSession session = request.getSession(false);
        if(session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
    
}
