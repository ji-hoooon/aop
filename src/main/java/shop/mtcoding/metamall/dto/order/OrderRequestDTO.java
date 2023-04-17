package shop.mtcoding.metamall.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderRequestDTO {
    //OrderReqDTO가 의존하는 객체
    private List<OrderProductDTO> orderProductDTOList;

    @Getter
    @Setter
    public static class OrderProductSaveDTO{


    }
    @Getter
    @Setter
    public static class OrderProductDTO{
        //주문 상품을 요청을 위한 DTO
        //: 상품 번호와 개수
        private Long productId;
        private Integer count;
    }
}
