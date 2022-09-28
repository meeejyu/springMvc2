package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}



// @Data
// public class UploadFile {
    
//     private String uploadFileName; // 실제 업로드 파일명
//     private String storeFileName; // 서버에 저장된 파일명


//     public UploadFile(String uploadFileName, String storeFileName) {
//         this.uploadFileName = uploadFileName;
//         this.storeFileName = storeFileName;
//     }

// }
