package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {
    
    // 검증 오류 코드로 메시지 코드들을 생성한다.
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    /**
     * DefaultMessageCodesResolver의 기본 메시지 생성 규칙
     * 
     * 객체 오류
     * 1. code + "." + object name
     * 2. code
     * 
     * ex)
     * 오류 코드 : required, object name: item
     * 1. : required.item
     * 2. : required
     * 
     * 필드 오류
     * 1. code + "." + object anme + "." + field
     * 2. code + "." +field
     * 3. code + "." + field type
     * 4. code
     * 
     * ex)
     * 오류 코드 : typeMisMatch, object name "user", field "age", field type: int
     * 1. typeMisMatch.user.age
     * 2. typeMisMatch.age
     * 3. typeMisMatch.int
     * 4. typeMisMatch
     */
    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodeResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }

}
