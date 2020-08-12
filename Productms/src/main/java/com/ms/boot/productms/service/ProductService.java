package com.ms.boot.productms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ms.boot.productms.model.DiscountRequest;
import com.ms.boot.productms.model.DiscountResponse;
import com.ms.boot.productms.model.Product;
import com.ms.boot.productms.model.ProductDTO;
import com.ms.boot.productms.repo.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class ProductService {

	@Autowired
	ProductRepository repo;
	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	LoadBalancerClient lbClient;

	@Autowired
	RestTemplate lbRestTemplate;

	@Autowired
	DiscountFeignProxy feignProxy;

	@Bean
	@LoadBalanced
	RestTemplate loadBalancedrestTemplate() {
		return new RestTemplate();
	}

	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	public Product getProductById(Integer id) {
		Optional<Product> op = repo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			return null;
	}

	public ProductDTO applyDiscountV1(Product p) {

		List<ServiceInstance> instances = discoveryClient.getInstances("discountms");
		for (ServiceInstance inst : instances) {
			System.out.println(inst.getHost() + ":" + inst.getPort());
		}
		ServiceInstance instance = instances.get(0);
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://" + host + ":" + port + "/caldisc";
		RestTemplate restTemplate = new RestTemplate();
		DiscountRequest dRequest = createDiscountRequest(p);
		// HttpEntity<DiscountRequest> drHttpEntity = new
		// HttpEntity<DiscountRequest>(dRequest);
		HttpEntity<DiscountResponse> dResponseEntity = restTemplate.postForEntity(url, dRequest,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	public ProductDTO applyDiscountV2(Product p) {

		ServiceInstance instance = lbClient.choose("discountms");
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://" + host + ":" + port + "/caldisc";
		RestTemplate restTemplate = new RestTemplate();
		DiscountRequest dRequest = createDiscountRequest(p);
		// HttpEntity<DiscountRequest> drHttpEntity = new
		// HttpEntity<DiscountRequest>(dRequest);
		HttpEntity<DiscountResponse> dResponseEntity = restTemplate.postForEntity(url, dRequest,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	public ProductDTO applyDiscountV3(Product p) {

		String url = "http://discountms/caldisc";
		DiscountRequest dRequest = createDiscountRequest(p);
		HttpEntity<DiscountResponse> dResponseEntity = lbRestTemplate.postForEntity(url, dRequest,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	@HystrixCommand(commandKey = "BasicCB", fallbackMethod = "applyDiscountfallback")
	public ProductDTO applyDiscountV4(Product p) {

		String url = "http://discountms/caldisc";
		DiscountRequest dRequest = createDiscountRequest(p);
		HttpEntity<DiscountResponse> dResponseEntity = lbRestTemplate.postForEntity(url, dRequest,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	public ProductDTO applyDiscountV5(Product p) {

		DiscountRequest dRequest = createDiscountRequest(p);
		DiscountResponse dResponse = feignProxy.calculateDiscount(dRequest);
		return ceateProductResponseDTO(dResponse, p);

	}

	public ProductDTO applyDiscountfallback(Product p) {

		DiscountResponse dResponse = new DiscountResponse(p.getCategory(), p.getMrp(), p.getMrp(), 0.0, 0.0);
		return ceateProductResponseDTO(dResponse, p);

	}

	private DiscountRequest createDiscountRequest(Product p) {
		return new DiscountRequest(p.getCategory(), p.getMrp());
	}

	private ProductDTO ceateProductResponseDTO(DiscountResponse discountResponse, Product p) {
		ProductDTO pdto = new ProductDTO();
		pdto.setCategory(p.getCategory());
		pdto.setDrp(discountResponse.getDrp());
		pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
		pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
		pdto.setId(p.getId());
		pdto.setMrp(p.getMrp());
		pdto.setName(p.getName());
		pdto.setShortDescription(p.getShortDescription());
		pdto.setTags(p.getTags());

		return pdto;
	}

}
