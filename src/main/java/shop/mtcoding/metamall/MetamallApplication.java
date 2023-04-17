package shop.mtcoding.metamall;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import shop.mtcoding.metamall.model.order.product.OrderProductRepository;
import shop.mtcoding.metamall.model.order.sheet.OrderSheetRepository;
import shop.mtcoding.metamall.model.product.ProductRepository;
import shop.mtcoding.metamall.model.user.User;
import shop.mtcoding.metamall.model.user.UserRepository;

import java.util.Arrays;

@SpringBootApplication
public class MetamallApplication {

	@Bean
	CommandLineRunner initData(UserRepository userRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository, OrderSheetRepository orderSheetRepository){
		return (args)->{
			// 여기에서 save 하면 됨.
			// bulk Collector는 saveAll 하면 됨.
			User ssar = User.builder().username("ssar").password("1234").email("ssar@nate.com").role("USER").status(true).build();
			User seller = User.builder().username("ssar").password("1234").email("ssar@nate.com").role("SELLER").status(true).build();
			User admin = User.builder().username("ssar").password("1234").email("ssar@nate.com").role("ADMIN").status(true).build();

			//벌크 컬렉터로 세이브: JPA에서 여러 개의 엔티티를 한번에 조회하는 기능
			// 이 기능은 단일 쿼리로 대량의 데이터를 한 번에 가져올 수 있기 때문에, 여러 번의 쿼리 실행과 그에 따른 DB 부하를 줄일 수 있다.
			// 벌크 셀렉터는 일반적으로 JPQL의 SELECT 절에 사용한다.
//			userRepository.save(ssar);
			userRepository.saveAll(Arrays.asList(ssar, seller, admin));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(MetamallApplication.class, args);
	}

}
