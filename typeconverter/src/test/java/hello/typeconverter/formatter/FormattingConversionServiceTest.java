package hello.typeconverter.formatter;

import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;

import static org.assertj.core.api.Assertions.*;

public class FormattingConversionServiceTest {
    
    @Test
    void formattingConversionServiceTest() {

        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        // 컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        // 포맷터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        // 컨버터 사용
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);
    }

    /*
     * DefaultFormattingConversionService
     * 
     * formattingConversionService는 포맷터를 지원하는 컨버전 서비스이다
     * 기본적인 통화, 숫자 관련 몇가지 기본 포맷터를 추가해서 제공한다
     * 
     * formattingConversionService는 ConversionService 관련 기능을 상속받기 때문에 결과적으로 컨버터도 포맷터도 모두 등록할 수 있다
     * 그리고 사용할떄는 ConversionService가 제공하는 convert를 사용하면 된다
     * 
     */
}
