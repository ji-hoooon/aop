package shop.mtcoding.metamall.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.seller WHERE p.id = :productId")
    Optional<Product> findById(@Param("productId") Long productId);
//    @Query("SELECT p FROM Product p JOIN FETCH p.seller WHERE p.id = :id")
//    Optional<Product> findById(Long id); //일치하면 Param 생략가능

    @EntityGraph(attributePaths = "seller")
    Page<Product> findAll(Pageable pageable);
}
