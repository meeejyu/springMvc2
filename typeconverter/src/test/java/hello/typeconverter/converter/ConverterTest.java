package hello.typeconverter.converter;

import org.junit.jupiter.api.Test;

import hello.typeconverter.type.IpPort;

import static org.assertj.core.api.Assertions.*;

class ConverterTest {

    @Test 
    void stringToInteger() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToString() {
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(10);
        assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.1:8080";
        IpPort result = converter.convert(source);
        assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

    @Test
    void ipPortToString() {
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort source = new IpPort("127.0.0.1", 8080);
        String result = converter.convert(source);
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }
}

/*
 * 스프링의 다양한 방식의 타입 컨버터 제공
 * 
 * Converter : 기본 타입 컨버터
 * ConverterFactory : 전체 클래스 계층 구조가 필요할 떄
 * GenericConverter : 정교한 구현, 대상 필드이 애노텡션 정보 사용 가능
 * ConditionalGenericConverter : 특정 조건이 참인 경우에만 실행
 */
