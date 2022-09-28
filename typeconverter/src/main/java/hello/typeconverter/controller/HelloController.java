package hello.typeconverter.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data");
        Integer intValue = Integer.valueOf(data);
        System.out.println("intValue = "+ intValue);
        return "ok";
        // HTTP 파라미터로 넘어온것은 모두 문자 처리가 됨, 그래서 따로 형변환을 시켜줘야함
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }
    /*
     * 스프링 타입 변환 적용
     * @RequestParam, @ModelAttribute, @PathVariable 자동으로 변환해줌
     */


}
