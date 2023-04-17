package shop.mtcoding.metamall.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.metamall.core.annotation.MySessionStore;
import shop.mtcoding.metamall.core.exception.Exception400;
import shop.mtcoding.metamall.core.session.SessionUser;
import shop.mtcoding.metamall.dto.ResponseDTO;
import shop.mtcoding.metamall.dto.product.ProductRequestDTO;
import shop.mtcoding.metamall.model.product.Product;
import shop.mtcoding.metamall.model.product.ProductRepository;
import shop.mtcoding.metamall.model.user.User;
import shop.mtcoding.metamall.model.user.UserRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 상품등록, 상품목록보기, 상품상세보기, 상품수정하기, 상품삭제하기
 */
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final HttpSession session;

    //필터가 인증체크, 인터셉터가 판매자인지 권한체크 수행 -> 핸들러 메서드에 도착한 요청들은 모두 인증체크, 권한체크를 완료한 요청들
    //1. 로그인 유저인지 인증 체크
    //2. 판매자 권한 체크
    //3. 유효성 검사 통과
    //4. 판매자 존재 여부 확인
    //5. 상품 등록
    //6. 응답

    @PostMapping("/seller/products")
    public ResponseEntity<?> save(@RequestBody @Valid ProductRequestDTO.SaveReqDTO saveReqDTO, Errors errors, @MySessionStore SessionUser sessionUser) {
        //권한처리를 위해 로그인 유저 정보를 세션에 저장한다. -> AOP를 이용해 구현
        //: JWT 필터에서 검증 성공시 -> MySessionArgumentResolver가 세션에 저장

        // 1. 판매자 찾기
        User sellerPS = userRepository.findById(sessionUser.getId())
                .orElseThrow(
                        () -> new Exception400("id", "판매자를 찾을 수 없습니다")
                );

        // 2. 상품 등록하기
//        Product product =saveReqDTO.toEntity(sellerPS); //비영속 객체
//        Product productPS =productRepository.save(product); //영속 객체
        //영속성 컨텍스트 안에 유저만 들어있고, 상품이 유저를 가지고 있다. - 상품은 비영속 상태, 유저는 영속 상태
        //: 영속화 시킨다. -> 둘 다 영속 상태로 바뀜
        //실제 쿼리는 안날라가고 영속상태이므로, 캐싱해서 가져온다.

        Product productPS = productRepository.save(saveReqDTO.toEntity(sellerPS));
        ResponseDTO<?> responseDto = new ResponseDTO<>().data(productPS);
        return ResponseEntity.ok().body(responseDto);
        //메시지 컨버터가 getter

    }


    //상품 목록 보기
    // http://localhost:8080/products?page=1
    @GetMapping("/products")
    //JPA에서의 페이징
    //: @PageableDefault과 Pageable로 간단하게 페이징 구현
    public ResponseEntity<?> findAll(@PageableDefault(size = 10, page = 0, direction = Sort.Direction.DESC) Pageable pageable) {
        // 1. 상품 찾기
        Page<Product> productPagePS = productRepository.findAll(pageable);

        // 2. 응답하기
        ResponseDTO<?> responseDto = new ResponseDTO<>().data(productPagePS);
        return ResponseEntity.ok().body(responseDto);
    }

    //인증만 되면 접속 가능한 페이지
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        // 1. 상품 찾기
        Product productPS = productRepository.findById(id).orElseThrow(() -> new Exception400("id", "해당 상품을 찾을 수 없습니다"));

        // 2. 응답하기
        ResponseDTO<?> responseDto = new ResponseDTO<>().data(productPS);
        return ResponseEntity.ok().body(responseDto);
    }

//    @Transactional // 더티체킹 하고 싶다면 붙이기!!
//    @PutMapping("/seller/products/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO.UpdateReqDTO updateReqDTO, Errors errors){
//        // 1. 상품 찾기
//        Product productPS = productRepository.findById(id).orElseThrow(()-> new Exception400("id", "해당 상품을 찾을 수 없습니다"));
//
//        // 2. Update 더티체킹
//        productPS.update(updateReqDTO.getName(), updateReqDTO.getPrice(), updateReqDTO.getQty());
//
//        // 3. 응답하기
//        ResponseDTO<?> responseDto = new ResponseDTO<>().data(productPS);
//        return ResponseEntity.ok().body(responseDto);
//    }

    @DeleteMapping("/seller/products/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Product productPS = productRepository.findById(id).orElseThrow(() -> new Exception400("id", "해당 상품을 찾을 수 없습니다"));
        productRepository.delete(productPS);
        ResponseDTO<?> responseDto = new ResponseDTO<>();
        return ResponseEntity.ok().body(responseDto);
    }
}