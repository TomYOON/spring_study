package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue
    private  Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member") //mappedBy를 통해 order 엔티티의 member 변수에 매핑된 변수라는 것을 알려줌. 변경은 order의 member를 통해서만 해야함 (읽기전용으로됨)
    private List<Order> orders = new ArrayList<>();

}
