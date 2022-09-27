package hello.exception.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ErrorPageController {

    // RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception"; // 예외
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type"; // 예외 타입 
    public static final String ERROR_MESSAGE = "javax.servlet.error.message"; // 오류 메시지
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri"; // 클라이언트 요청 URI
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name"; // 오류가 발생한 서블릿 이름
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code"; // HTTP 상태 코드

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_EXCEPTION_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE)); 
        log.info("ERROR_EXCEPTION_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_EXCEPTION_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_EXCEPTION_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }

    /**
     * DispatcherType
     * 실제 고객이 요청한 것이닞, 서버가 내부에서 오류 페이지를 요청하는 것인지 구분할 수 있다
     * REQUEST 클라이언트 요청
     * ERROR 오류 요청
     */
}
