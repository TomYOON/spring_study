# spring_study

## config file로 DI할 때 장점

- 다른 객체로 바꿀 때 편리함

```java
@Configuration
public class SpringConfig {

    EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }
}

```

## JPA

- JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
- JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다.
- JPA를 사용하면 개발 생산성을 크게 높일 수 있다.

### JPA 사용시 유의할 점

1. JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 함

```java
    @Transactional // JPA는 transactional이 필요함
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
```

## Test Code

### unit test

- 함수의 가장 작은 단위로 실행하는 것

### integration test

- 실제 DB에 접근하고 Spring을 띄우는 등 전반적으로 테스트 하는 것

### 유의점

- DB에 반영하지 않게 하는 법

```java
@Transactional //DB에 커밋되지않음
class MemberServiceIntegrationTest {

@Commit //이 어노테이션을 쓰면 커밋됨
```

## IntelliJ 유용한 단축키

1. ctrl + alt + v 변수명 만들기
2. ctrl + shift + enter 끝에 ; 삽입하면서 끝으로 이동

## ToDo list

- [ ] JPA 정리 및 실습
- [ ] TDD 정리 및 실습
- [ ] IntelliJ 단축키 더 알아보기
