# spring_study

# DI

## 스프링 빈 등록 방법

1. 컴포넌트 스캔
2. 자바 코드를 통한 등록
3. ~~XML~~ (실무에서 사용X)

## 컴포넌트 스캔

```java
@Service
@Repository
@Controller
@Component
```

- @Component 애노테이션을 사용하면 빈으로 자동 등록
- @Service, @Repository, @Controller는 모두 @Component의 특수한 형태임

## 자바 코드로 등록

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

- 구현체를 바꿀 때 편리함
- 실무에서 사용

## DI 방법

1. 필드 주입
2. setter 주입
3. 생성자 주입

- 필드 주입의 경우 구현체를 바꾸면 직접 코드를 전부 수정해야하고 setter는 불필요한 함수 접근이 생길 수 있음(사실상 빈 등록은 컨테이너에 올라갈 때만 필요한데 어느 개발자든 setter함수를 호출 가능하기 때문에 맞지않음)

# JPA

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

## Spring data JPA

- 인터페이스를 통한 기본적인 CRUD 제공
- findByName(), findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공
- 페이징 기능 자동 제공

```java
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository { //JpaRepository를 상속 받고 Member의 키인 id의 타입 long을 명시

    //select m from Member m where m.name = ?
    //인터페이스 이름만으로도 쿼리를 할 수 있다.
    @Override
    Optional<Member> findByName(String name);
}
```

# AOP

- 공통 관심 사항(cross-cutting concern)과 핵심 관심 사항(core concern) 분리

```java
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello..hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
```

- 핵심 관심 사항을 깔끔하게 유지할 수 있음
- 변경이 필요하면 해당 로직만 변경하면 됨

# Test Code

### unit test

- 함수의 가장 작은 단위로 실행하는 것

### integration test

- 실제 DB에 접근하고 Spring을 띄우는 등 전반적으로 테스트 하는 것
- spring은 @SpringBootTest 애노테이션을 통해 컨테이너 환경에서 테스트 가능

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
3. ctrl + shift + alt + L: 라인 포메팅
4. 클래스 코드 네비게이터에 복붙하면 클래스 생성됨
5. alt + shift + insert: mulit cursor 모드 (shift 방향키로 커서 생성)
6. ctrl + D: copy line below
7. ctrl + B: 커서가 있는 객체의 클래스 정의로 이동
8. alt + insert: 생성자, getter 등
9. ctrl + alt + M: 함수화

## ToDo list

- [ ] JPA 정리 및 실습
- [ ] TDD 정리 및 실습
- [ ] IntelliJ 단축키 더 알아보기
- ✅ AOP 정리 -> https://carbon2.tistory.com/4

v4와 v3는 우열을 가리기 힘듬 (trade off)
v4는 재사용성이 없음, 성능은 아주 조금 더 좋음, 대부분의 성능은 조인에서 먹음
v3는 많은 API에서 사용 가능
repository는 가급적 순수한 entity를 조회하는데 사용
