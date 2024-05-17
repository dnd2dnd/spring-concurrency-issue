package com.project.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConstant {

	public static final String NAME_NOT_NULL = "상품명은 빈 값일 수 없습니다.";

	public static final String PRICE_IS_POSITIVE = "수량은 양수나 0만 가능합니다.";

	public static final String STOCK_IS_POSITIVE = "수량은 양수나 0만 가능합니다.";
	public static final String STOCK_INIT_IS_10000 = "최초 재고 수량은 최소 10000이상만 가능합니다.";

}
