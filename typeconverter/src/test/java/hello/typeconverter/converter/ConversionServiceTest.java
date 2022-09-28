package hello.typeconverter.converter;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import hello.typeconverter.type.IpPort;

import static org.assertj.core.api.Assertions.*;

/*
 * 스프링은 개별 컨터버를 모아두고 그것들을 묶어서 편리하게 사용할 수 있는 기능을 제공하는데, 이것이 바로 컨버전 서비스다.
 */
public class ConversionServiceTest {
    
    @Test
    void conversionService() {

        // 등록해주면 알아서 클래스 찾아서 변경됨
        // 컨버전 서비스의 인터페이스를 구현한 구현체
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 사용
        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        assertThat(conversionService.convert(10, String.class)).isEqualTo("10");

        IpPort ipPort =conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        String ipPortString = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(ipPortString).isEqualTo("127.0.0.1:8080");

    }

    /*
     * 인터페이스 분리 원칙 ISP (Interface Segregation Principal)
     * DefaultConversionService는 다음 두 인터페이스를 구현했다
     *      ConversionService : 컨버터 사용에 초점
     *      ConverterRegistry : 컨버터 등록에 초점
     * 이렇게 인터페이스를 분리하면 컨버터를 사용하는 클라이언트와 컨버터를 등록하고 관리하는 클라이언트의 관심사를 명확하게 분리할 수 있다
     * 이것을 인터페이스 분리 원칙이라고 한다
     * 이 외에도 다른 여러곳에서 이 원칙을 적용하여 사용하고 있다
     */
}
