package hello.typeconverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.typeconverter.converter.IntegerToStringConverter;
import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIntegerConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.formatter.MyNumberFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addFormatters(FormatterRegistry registry) {

        // registry.addConverter(new StringToIntegerConverter());
        // registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        // 추가
        registry.addFormatter(new MyNumberFormatter());
    }
    /*
     * 스프링은 내부에서 ConversionService를 제공한다.
     * 우리는 WebMvcConfigurer가 제공하는 addFormatters()를 사용해서 추가하고 싶은 컨버터를 등록하면 된다.
     * 이렇게 하면 스프링은 내부에서 사용하는 ConversionService에 컨버터를 추가해준다.
     * 컨버터를 추가하면 추가한 컨버터가 기본 컨버터보다 높은 우선순위를 가진다
     */
    
}
