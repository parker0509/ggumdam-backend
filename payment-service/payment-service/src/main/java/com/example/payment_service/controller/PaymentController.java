package com.example.payment_service.controller;

import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.PaymentResponse;
import com.example.payment_service.service.IamportService;
import com.example.payment_service.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    private final IamportService iamportService;
    private final PaymentService paymentService;

    public PaymentController(IamportService iamportService, PaymentService paymentService) {
        this.iamportService = iamportService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> requestPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 결제 검증 API
     */
    @GetMapping("/validate/{impUid}")
    public IamportResponse<Payment> validatePayment(@PathVariable String impUid) throws IOException, IamportResponseException {
        return iamportService.validatePayment(impUid);
    }

    /**
     * 결제 취소 API
     */
    @PostMapping("/cancel")
    public IamportResponse<Payment> cancelPayment(@RequestBody CancelData cancelData)
            throws IOException, IamportResponseException {
        return iamportService.cancelPayment(cancelData);
    }


}
