package com.project.payment.service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.project.common.config.TossPaymentConfig;
import com.project.order.domain.Order;
import com.project.order.domain.OrderItem;
import com.project.order.repository.OrderItemRepository;
import com.project.order.repository.OrderRepository;
import com.project.payment.domain.CanclePayment;
import com.project.payment.domain.Payment;
import com.project.payment.domain.PaymentStatus;
import com.project.payment.dto.request.PaymentRequestDto;
import com.project.payment.dto.response.CanclePaymentDto;
import com.project.payment.dto.response.PaymentResponseDto;
import com.project.payment.dto.response.PaymentSuccessDto;
import com.project.payment.exception.OrderNotFoundException;
import com.project.payment.exception.PaymentAmountException;
import com.project.payment.repository.CanclePaymentRepository;
import com.project.payment.repository.PaymentRepository;
import com.project.user.domain.User;
import com.project.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
	private final UserRepository userRepository;
	private final PaymentRepository paymentRepository;
	private final TossPaymentConfig tossPaymentConfig;
	private final OrderRepository orderRepository;
	private final CanclePaymentRepository canclePaymentRepository;
	private final OrderItemRepository orderItemRepository;

	//토스 결제 요청
	public PaymentResponseDto requestTossPayment(Long userId, PaymentRequestDto paymentRequestDto) {
		User user = userRepository.getById(userId);
		Payment payment = Payment.of(user, paymentRequestDto.getPaymentType(), paymentRequestDto.getAmount(),
			paymentRequestDto.getOrderId(), paymentRequestDto.getOrderName(), false);
		payment.setUser(user);
		paymentRepository.save(payment);

		String successUrl = paymentRequestDto.getSuccessUrl() == null ? tossPaymentConfig.getSuccessfulUrl() :
			paymentRequestDto.getSuccessUrl();
		String failUrl = paymentRequestDto.getFailUrl() == null ? tossPaymentConfig.getFailUrl() :
			paymentRequestDto.getFailUrl();

		PaymentResponseDto paymentResponseDto = PaymentResponseDto.of(payment, successUrl, failUrl);
		return paymentResponseDto;
	}

	//토스 결제 성공
	public ResponseEntity<PaymentSuccessDto> tossPaymentSuccess(String paymentKey, String orderId, Long amount) throws
		JSONException {
		Payment payment = verifyPayment(orderId, amount);
		ResponseEntity<PaymentSuccessDto> result = requestPaymentAccept(paymentKey, orderId, amount);
		Order order = orderRepository.findById(payment.getOrderId());
		OrderItem orderItem = orderItemRepository.findByOrder_Id(orderId);

		payment.setPaymentKey(paymentKey); // 결제 성공시 redirect로 받는 paymentKey 저장
		payment.setPaySuccessYN(true); // 결제 성공 -> PaySuccess값 true로 설정
		Order.updatePaymentStatus(order, PaymentStatus.PAY_COMPLETE);
		paymentRepository.save(payment);

		orderItem.getProduct().getStock().increase(orderItem.getQuantity());
		return result;
	}

	// 결제 요청이 유효한지 체크
	public Payment verifyPayment(String orderId, Long amount) {
		Payment payment = paymentRepository.findByOrderId(orderId)
			.orElseThrow(OrderNotFoundException::new);
		//주문 금액과 결제할 때 금엑이 일치하는지 확인
		if (!payment.getAmount().equals(amount)) {
			throw new PaymentAmountException();
		}
		return payment;
	}

	public ResponseEntity<PaymentSuccessDto> requestPaymentAccept(String paymentKey, String orderId, Long amount) throws
		JSONException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject params = new JSONObject();
		params.put("orderId", orderId);
		params.put("amount", amount);

		String requestJson = params.toString();

		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

		//토스 url로 success response 응답 받음
		ResponseEntity<PaymentSuccessDto> response = restTemplate.exchange(
			TossPaymentConfig.baseUrl + paymentKey,
			HttpMethod.POST,
			entity,
			PaymentSuccessDto.class
		);

		return response;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		//tossSecretKey Base64 인코딩, {시크릿key+":"}로 인코딩
		String encodedAuthKey = new String(
			Base64.getEncoder()
				.encode((tossPaymentConfig.getTestSecretApiKey() + ":").getBytes(StandardCharsets.UTF_8)));
		headers.setBasicAuth(encodedAuthKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	//toss 결제 취소
	public CanclePaymentDto cancelPayment(CanclePayment canclePayment, String paymentKey, String cancelReason)
		throws JSONException {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(TossPaymentConfig.baseUrl + paymentKey + "/cancel");
		HttpHeaders headers = getHeaders();

		JSONObject param = new JSONObject();
		param.put("cancelReason", cancelReason);

		String requestJson = param.toString();

		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

		ResponseEntity<CanclePaymentDto> response = restTemplate.exchange(
			uri,
			HttpMethod.POST,
			entity,
			CanclePaymentDto.class
		);

		return CanclePaymentDto.of(canclePayment);
	}

	public CanclePaymentDto tossPaymentCancle(Long userId, String paymentKey, String cancleReason)
		throws JSONException {
		User user = userRepository.getById(userId);
		Payment payment = paymentRepository.findByPaymentKey(paymentKey);
		Order order = orderRepository.findById(payment.getOrderId());
		CanclePayment canclePayment = CanclePayment.of(user, paymentKey, payment.getAmount(),
			order.getId(), order.getOrderName(),
			cancleReason);
		canclePaymentRepository.save(canclePayment);
		paymentRepository.delete(payment);
		CanclePaymentDto result = cancelPayment(canclePayment, paymentKey, cancleReason);
		Order.updatePaymentStatus(order, PaymentStatus.PAY_CANCEL);

		return result;
	}

}

