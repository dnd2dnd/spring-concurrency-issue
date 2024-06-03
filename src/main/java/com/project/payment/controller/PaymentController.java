package com.project.payment.controller;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.payment.dto.PaymentRequestDto;
import com.project.payment.dto.PaymentResponseDto;
import com.project.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
	private final PaymentService paymentService;

	@PostMapping("/toss")
	public ResponseEntity<PaymentResponseDto> requestTossPayment(@RequestParam Long memberId,
		@RequestBody @Valid PaymentRequestDto paymentReqDto) {
		PaymentResponseDto paymentResDto = paymentService.requestTossPayment(memberId, paymentReqDto);
		return ResponseEntity.ok(paymentResDto);
	}

	//toss 결제 성공
	@GetMapping("/toss/success")
	public String tossPaymentSuccess(@RequestParam String paymentKey,
		@RequestParam String orderId,
		@RequestParam Long amount
	) throws JSONException {
		log.info("toss payment 성공");
		String paymentSuccessDto = paymentService.tossPaymentSuccess(paymentKey, orderId, amount);
		return paymentSuccessDto;
	}

	//toss 결제 취소
	@PostMapping("/cancle")
	public String canclePayment(@RequestParam Long memberId,
		@RequestParam String paymentKey,
		@RequestParam String cancleReason) throws JSONException {
		String cancle = paymentService.cancelPayment(memberId, paymentKey, cancleReason);
		return cancle;
	}
}
