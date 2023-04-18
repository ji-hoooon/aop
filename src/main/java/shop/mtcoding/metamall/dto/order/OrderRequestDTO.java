package shop.mtcoding.metamall.dto.order;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.metamall.model.order.product.OrderProduct;
import shop.mtcoding.metamall.model.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderRequestDTO {
    @Getter
    @Setter
    public static class SaveReqDTO {

        //SaveReqDTO가 의존하는 객체
        private List<OrderProductDTO> orderProducts;
        @Getter
        @Setter
        public static class OrderProductDTO {
            //주문 상품을 요청을 위한 DTO
            //: 상품 번호와 개수
            private Long productId;
            private Integer count;
        }

        // 1. request 요청으로 들어온 product id만 리스트로 뽑아내기
        public List<Long> getIds() {
            return orderProducts.stream().map((orderProduct) -> orderProduct.getProductId()).collect(Collectors.toList());
        }

        // 3. 주문 상품 리스트 만들어 내기
        public List<OrderProduct> toEntity(List<Product> products) { // 2. getIds() 로 뽑아낸 번호로 상품 리스트 찾아내서 주입하기

            // 4. request 요청으로 들어온 DTO에 count 값이 필요해서 stream() 두번 돌렸음. 주문 상품 금액을 만들어 내야 해서!!
            return orderProducts.stream()
                    // 5. map은 다시 collect로 수집해야 하기 때문에, flatMap으로 평탄화 작업함.
                    //: for 문돌리면서 정규 연산자를 이용해 자신의 타입을 버리고, Product를 OrderProduct 타입으로 바꾼다.
                    .flatMap((orderProduct) -> {
                                Long productId = orderProduct.productId;
                                Integer count = orderProduct.getCount();
                                // 6. OrderProduct 객체 만들어내기 (주문한 상품만큼)
                                return products.stream()
                                        .filter((product) -> product.getId().equals(productId))
                                        .map((product) -> OrderProduct.builder()
                                                .product(product)
                                                .count(count)
                                                .orderPrice(product.getPrice() * count)
                                                .build());
                            }
                    ).collect(Collectors.toList()); // 7. 최종 수집
        }
    }
}