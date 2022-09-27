package hello.exception.exhandler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    //     /**
    //  * @ExceptionHandler
    //  * 해당 에노테이션을 선언하고, 해당 컨트롤러에서 처리하고 싶은 예외를 지정해주면 된다.
    //  * 햐당 컨트롤러에서 에외가 발생하면 이 메서드가 호출된다
    //  * 여러 예외를 한번에 처리할 수 있다
    //  * ex)
    //  * @ExceptionHandler({에외1, 예외2})
    //  */
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(IllegalArgumentException.class)
    // public ErrorResult illegalEcHandle(IllegalArgumentException e) {
    //     log.error("[exceptionHandle] ex", e);
    //     return new ErrorResult("BAD", e.getMessage());
    // }

    // @ExceptionHandler // 파라미터에 예외를 넣으면 생략할 수 있다
    // public ResponseEntity<ErrorResult> userExHandle(UserException e) {
    //     log.error("[exceptionHandle] ex", e);
    //     ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
    //     return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    // }

    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // @ExceptionHandler // 해당 컨트롤러에서 발생한 예외를 해결해줌
    // public ErrorResult exHandle(Exception e) {
    //     log.error("[exceptionHandle] ex", e);
    //     return new ErrorResult("EX", "내부 오류");
    // }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        
        if(id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if(id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }
        
        return new MemberDto(id, "hello " +id);
    }

    @Data
    @AllArgsConstructor // 생성자 생성
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
