package hello.upload.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {
    
    @Value("${file.dir}")
    private String fileDir; // 프로퍼티 값 주입

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        
        log.info("request={}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts={}", parts);

        for(Part part : parts) {

            log.info("====== PART ====");
            log.info("name={}", part.getName());

            /*
             * part도 헤더와 바디, 편의메소드를 제공함
             */
            Collection<String> headerNames = part.getHeaderNames();
            for(String headerName : headerNames) {
                log.info("header {}: {}", headerName, part.getHeader(headerName));
            }
            
            // 편의 메소드
            // contetn-disposition; filename
            log.info("submittedFileName={} ", part.getSubmittedFileName());
            log.info("size={}", part.getSize()); // part body size

            // 데이터 읽기
            InputStream inputStream = part.getInputStream(); // part의 전송 데이터를 읽을 수 있다
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body={}", body);

            // 파일에 저장하기
            if(StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName(); // 클라이언트가 전달한 파일명
                log.info("파일 저장 fullPath={}", fullPath);
                part.write(fullPath); // 경로 넣어주면 데이터를 저장할 수 있다
            }

        }

        return "upload-form";
    }
}
