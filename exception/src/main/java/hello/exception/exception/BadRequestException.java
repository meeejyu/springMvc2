package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 개발자가 직접 변경할 수 없는 예외는 적용할수없음
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException{ // 400에러로 보냄
    
}

/**
 * ResponseStatusExceptionResolver
 * 예외에 따라 HTTP 상태 코드를 지정해주는 역할
 * @ResponseStatus 가 달려있는 예외
 * ResponseStatusException 예외
 */
