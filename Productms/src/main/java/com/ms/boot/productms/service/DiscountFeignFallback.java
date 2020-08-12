/**
 * 
 */
package com.ms.boot.productms.service;

import org.springframework.stereotype.Component;

import com.ms.boot.productms.model.DiscountRequest;
import com.ms.boot.productms.model.DiscountResponse;

/**
 * @author w2cluster01
 *
 */
@Component
public class DiscountFeignFallback implements DiscountFeignProxy {

	@Override
	public DiscountResponse calculateDiscount(DiscountRequest request) {
		return new DiscountResponse(request.getCategory(), request.getMrp(), request.getMrp(), 0.0, 0.0);
	}

}
