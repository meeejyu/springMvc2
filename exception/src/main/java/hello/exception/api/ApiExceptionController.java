package hello.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiExceptionController {
    
    @GetMapping("/api/members/{id}")
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

    /*
     * @ResponseStatus는 통해 바로 오류 코드를 변경함
     */
    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    /*
     * ResponseStatusException
     * @ResponseStatus는 조건에 따라 동적으로 변경하기 어렵기 때문에 그때 ResponseStatusException를 사용해준다.
     */
    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    /*
     * DefaultHandlerExceptionResolver는 스프링 내부에서 발생하는 스프링 예외를 해결한다
     * 바인딩 시점에 타입이 맞지않으면 500에러가 발생하는데 이것을 400으로 변경해준다
     */
    @GetMapping("/api/response-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }

    @Data
    @AllArgsConstructor // 생성자 생성
    static class MemberDto {
        private String memberId;
        private String name;
    }
}

/*
 * 스프링부트가 기본으로 제공하는 ExceptionResolver
 * HandlerExceptionResolverComposite에 다음 순서로 등록
 * 1. ExceptionHandlerExceptionResolver // @ExceptionHandler을 처리한다, api 예외 처리는 대부분 이 기능으로 해결
 * 2. ResponseStatusException // HTTP 상태 코드를 지정해준다
 * 3. DefaultHandlerExceptionResolver는 // 스프링 내부 기본 예외를 처리한다, 우선순위가 가장 낮음
 */
