package com.project.order.dto.request;

import java.util.List;

import com.project.order.domain.BaseOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OrderRequestWrapper extends BaseOrder {

	@Schema(description = "장바구니 리스트")
	private List<CartOrderRequest> cartOrderRequestList;

}
