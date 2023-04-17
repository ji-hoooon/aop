package shop.mtcoding.metamall.model.order.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.metamall.model.order.sheet.OrderSheet;
import shop.mtcoding.metamall.model.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter // DTO 만들면 삭제해야됨
@Getter
@Table(name = "order_product_tb")
@Entity
public class OrderProduct { // 주문 상품
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //checkpoint - > 양방향 매핑시 무한참조 방지 필요 : @JsonIgnore-> 단방향 매핑 + fetch join / Entity Graph
    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private Integer count; // 상품 주문 개수

    //상품의 가격 * 상품 주문 개수로 동적으로 생성
    private Integer orderPrice; // 상품 주문 금액
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @ManyToOne
    private OrderSheet orderSheet;  //주문서 -> 테이블에선 주문서 id로 조인한다.

    //연관관계 편의 메서드 : 객체 간의 양방향 연관관계를 설정하는 코드를 간결하게 작성하기 위해 사용되는 메서드
    //checkpoint : 편의 메서드 만드는 이유
    public void syncOrderSheet(OrderSheet orderSheet){
        this.orderSheet=orderSheet;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public OrderProduct(Long id, Product product, Integer count, Integer orderPrice, LocalDateTime createdAt, LocalDateTime updatedAt, OrderSheet orderSheet) {
        this.id = id;
        this.product = product;
        this.count = count;
        this.orderPrice = orderPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderSheet = orderSheet;
    }
}
