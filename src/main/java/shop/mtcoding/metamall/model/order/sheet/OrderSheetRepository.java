package shop.mtcoding.metamall.model.order.sheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {

    //주문한 목록 보기에 필요한 메서드
    @Query ("select os from OrderSheet os where os.user.id=:userId")
    List<OrderSheet> findByUserId(@Param("userId") Long userId);
}
