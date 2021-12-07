package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //ORDINAL은 enum을 순서를 기반으로 접근하는 것이기 떄문에 나중에 enum에 뭔가 추가됐을 때 치명적이기 때문에 절대 사용 X
    private DeliveryStatus status; //READY, COMP
}
