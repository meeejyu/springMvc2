package hello.upload.Controller;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ItemForm {

    private Long itemId;
    private String itemName;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}



// @Data
// public class ItemForm {
    
//     private Long itemId;
//     private String itemName;
//     private List<MultipartFile> imageFiles; // 이미지 다중 업로드를 하기 위해 MultipartFile를 사용
//     private MultipartFile attachFile; // 멀티파트는 @ModelAttribute에서 사용할 수 있다

// }
