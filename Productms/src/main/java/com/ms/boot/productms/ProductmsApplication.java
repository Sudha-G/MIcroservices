package com.ms.boot.productms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ms.boot.productms.model.Product;
import com.ms.boot.productms.model.ProductCategory;
import com.ms.boot.productms.model.ProductTag;
import com.ms.boot.productms.repo.ProductRepository;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableFeignClients
public class ProductmsApplication {

	@Autowired
	ProductRepository prepo;

	public static void main(String[] args) {
		SpringApplication.run(ProductmsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			Product p = new Product(1, "Kitchen Chimney", "6x4. Non-exhaust", ProductCategory.KITCHENELECTRONIC,
					200.87);
			p.getTags().add(new ProductTag(1, "kitchen"));

			prepo.save(p);
			p = new Product(2, "Persian Carpet", "9x9. Handwoven", ProductCategory.FURNISHING, 1000.45);
			p.getTags().add(new ProductTag(2, "wool"));

			prepo.save(p);
			p = new Product(3, "Space Craft Lego", "580 pieces", ProductCategory.TOY, 776.00);
			p.getTags().add(new ProductTag(3, "plastic"));

			prepo.save(p);

		};
	}

}
