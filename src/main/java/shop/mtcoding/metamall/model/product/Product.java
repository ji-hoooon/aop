package shop.mtcoding.metamall.model.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.metamall.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter // DTO 만들면 삭제해야됨
@Getter
@Table(name = "product_tb")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품을 등록한 판매자 필드 (FK)
    //checkpoint - > 양방향 매핑시 무한참조 방지 필요 : @JsonIgnore -> 단방향 매핑 + fetch join / Entity Graph
    @ManyToOne (fetch = FetchType.LAZY)
    private User seller;
    //EAGER일 때 JOIN 발생

    @Column(nullable = false, length = 50)
    private String name; // 상품 이름
    private Integer price; // 상품 가격
    private Integer qty; // 상품 재고
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //Product와 관련된 메서드 작성 - 객체 상태 변경

    //상품 변경 메서드 (판매자만 가능)
    public void update(String name, Integer price, Integer qty){
        this.name=name;
        this.price=price;
        this.qty=qty;
    }

    //상품 주문시 재고 변경하는 메서드 (구매자가 호출)
    public void updateQty(Integer orderCount){
        if(this.qty<orderCount){

        }
        this.qty=this.qty-orderCount;
    }

    //주문 취소 재고 변경 (구매자, 판매자)
    public void rollbackQty(Integer orderCount){
        this.qty=this.qty+orderCount;
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
    public Product(Long id, User seller, String name, Integer price, Integer qty, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
