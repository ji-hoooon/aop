package shop.mtcoding.metamall.dto.product;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.metamall.model.product.Product;
import shop.mtcoding.metamall.model.user.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ProductRequestDTO {
    @Getter
    @Setter
    public static class SaveReqDTO {
        //상품 등록을 위한 DTO
        @NotEmpty
        private String name; // 상품 이름

        @Digits(fraction = 0, integer = 9)
        @NotNull
        private Integer price; // 상품 가격
        @Digits(fraction = 0, integer = 9)
        @NotNull
        private Integer qty; // 상품 재고

        //DTO를 이용해 엔티티를 만드는 메서드
        public Product toEntity(User user) {
            return Product.builder()
                    .seller(user)
                    .name(name)
                    .price(price)
                    .qty(qty)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateReqDTO {
        //상품 이름, 상품 가격, 상품 재고 변경을 위한 DTO
        @NotEmpty
        private String name;

        @Digits(fraction = 0, integer = 9)
        @NotNull
        private Integer price;

        @Digits(fraction = 0, integer = 9)
        @NotNull
        private Integer qty;

    }


}
