package hello.exception.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {
    
    private final ObjectMapper objectMapper = new ObjectMapper();


    /*
     * HTTP 요청 헤더의 ACCEPT 값이 application/json 이면 JSON으로 오류를 내려주고, 그 외 경우에는 
     * error/500에 있는 HTML 오류 페이지를 보여준다.
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        
        try {
            if(ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // WAS에 안가고 바로 끝내버림
                if("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView(); // 빈값을 넘김
                }
                else {
                    // text/html 뷰 페이지 전달
                    return new ModelAndView("error/500");
                }
            }   
        } 
        catch (IOException e) {
            log.error("resolver ex", e);
        }
        
        return null;
    }

}

/*
 * ExceptionResolver를 사용하면 컨트롤러에서 예외가 발생해도 ExceptionResolver에서 예외를 처리해버린다.
 * 따라서 예외가 발생해도 서블릿 컨테이너까지 에외가 전달되지 않고, 스프링 mvc에서 예외 처리는 끝이 난다
 * 결과적으로 was 입장에서는 정상 처리된 것이다
 */
