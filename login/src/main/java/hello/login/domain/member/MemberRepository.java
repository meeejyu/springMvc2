package hello.login.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 * 원래는 인터페이스로 만드는게 좋음
 */
@Slf4j
@Repository
public class MemberRepository {
    
    private static Map<Long, Member> store = new HashMap<>(); // static 사용

    private static long sequence = 0L; // static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream() // list를 stream으로 바꿔가져옴
                .filter(m -> m.getLoginId().equals(loginId)) // 루프를 돌아서 조건을 만족하는 것만 다음 단계로 넘어감
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // 키 빼고 value값만 반환
    }

    public void clearStore() {
        store.clear();
    }

}
