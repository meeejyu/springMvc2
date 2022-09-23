package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	// 아래의 로직을 통해 글로벌 설정을 할 수 있다, 모든 컨트롤러에 다 적용, 기존 컨트롤러에 InitBinder를 제거해도 정상동작한다
	// @Override
	// public Validator getValidator() {
	// 	return new ItemValidator();
	// }
}
