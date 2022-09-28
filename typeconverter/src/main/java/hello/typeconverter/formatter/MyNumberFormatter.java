package hello.typeconverter.formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import lombok.extern.slf4j.Slf4j;

/*
 * Converter vs Formatter
 * Converter는 범용 (객체 -> 객체)
 * Formatter는 문자에 특화(객체 -> 문자, 문자 -> 객체) + 현지화(Locale)
 *      Converter의 특별한 버전
 */
@Slf4j
public class MyNumberFormatter implements Formatter<Number>{

    @Override
    public Number parse(String text, Locale locale) throws ParseException {

        log.info("text={}, locale={}", text, locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {

        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getInstance(locale).format(object);
    }
    
    /*
     * NumberFormat은 Locale 저보를 활용해서 나라별로 다른 숫자 포맷을 만들어준다.
     * parse()를 사용해서 문자르 숫자로 변환한다
     * print()를 사용해서 객체를 문자로 변환한다
     */

}
