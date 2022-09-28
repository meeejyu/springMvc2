package hello.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
/*
 * equals, hashCode 자동 생성
equals :  두 객체의 내용이 같은지, 동등성(equality) 를 비교하는 연산자
hashCode : 두 객체가 같은 객체인지, 동일성(identity) 를 비교하는 연산자
자바 bean에서 동등성 비교를 위해 equals와 hashcode 메소드를 오버라이딩해서 사용하는데,
@EqualsAndHashCode어노테이션을 사용하면 자동으로 이 메소드를 생성할 수 있다.
 */
public class IpPort {
    
    private String ip;
    private int port;

    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    

}
