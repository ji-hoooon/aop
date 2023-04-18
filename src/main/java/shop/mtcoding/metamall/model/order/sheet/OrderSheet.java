package shop.mtcoding.metamall.model.order.sheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.metamall.model.order.product.OrderProduct;
import shop.mtcoding.metamall.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter // DTO 만들면 삭제해야됨
@Getter
@Table(name = "order_sheet_tb")
@Entity
public class OrderSheet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user; // 주문자

    //checkpoint - > 양방향 매핑시 무한참조 방지 필요 : @JsonIgnore -> 단방향 매핑 + fetch join / Entity Graph
    //: JsonIgnoreProperties로 무한참조 방지
    //양방향 매핑
    // 주문서 (양방향 매핑을 이용해 주문서 입장에서 주문 조회, 주문 수정, 주문 삭제 가능)
    //: 연관관계의 모든 영속 객체의 flush가 동시에 발생하도록 -> cascade 설정한다. -> PERSIST 조회만 가능, ALL 모두 가능 (REMOVE, DETACH 포함)
    //: 연관관계의 객체가 고아객체가 되면 자동 삭제 설정, orphanRemoval = true
    @JsonIgnoreProperties({"orderSheet"})
    @OneToMany(mappedBy = "orderSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProductList = new ArrayList<>(); // 총 주문 상품 리스트

    @Column(nullable = false)
    private Integer totalPrice; // 총 주문 금액 (총 주문 상품 리스트의 orderPrice 합)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // 연관관계 메서드 구현 필요
    public void addOrderProduct(OrderProduct orderProduct){
        orderProductList.add(orderProduct);
        //주문 상품의 추가
        orderProduct.syncOrderSheet(this);
        //주문 상품 만들기전에 id 없는 상태에서는 주문서가 존재하지 않으므로, 모든 주문 상품 생성 후 주문서를 할당한다.

        //(1) 주문서를 만들고 주문 상품을 추가한다. - 객체 지향 관점
        //(2) 주문 상품을 만들고 주문서에 넣는다. - 데이터베이스 관점
    }
    public void removeOrderProduct(OrderProduct orderProduct){
        orderProductList.remove(orderProduct);
        orderProduct.syncOrderSheet(null);
    }

    @Builder
    public OrderSheet(Long id, User user, Integer totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
