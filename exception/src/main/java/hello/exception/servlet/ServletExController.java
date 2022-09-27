package hello.exception.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx() { 
        throw new RuntimeException("예외 발생!"); 
    }

    /*
     *  response.sendError 
     *  이것을 호출한다고 당장 예외가 발생하는 것은 아니지만, 서블릿 컨테이너에게 오류가 발생했다는 점과 
        HTTP 상태코드와 오류 메시지도 추가할 수 있다
        
        sendError 흐름
        컨트롤러 -> 인터셉터 -> 서블릿 -> 필터 -> WAS(sendError 호출 기록 확인)
        response.sendError을 호출하면 response 내부에는 오류가 발생했다는 상태를 저장해둔다.
        그리고 서블릿 컨테이너는 고객에게 응답 전에 response에 sendError()이 호출되었는지 확인한다
        그리고 호출되었다면 설정한 오류 코드에 맞추어 기본 오류 페이지를 보여준다

     */
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!"); // errorPage404를 호출
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500); // errorPage500 호출
    }



    
}
