package com.example.payment_service.service;


import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IamportService {

    private final IamportClient iamportClient;

    @Value("${IAMPORT_API_KEY}")
    private String apiKey;

    @Value("${IAMPORT_API_SECRET}")
    private String apiSecret;

    // 생성자에서 IamportClient 초기화
    public IamportService(@Value("${IAMPORT_API_KEY}") String apiKey,
                          @Value("${IAMPORT_API_SECRET}") String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.iamportClient = new IamportClient(apiKey, apiSecret);  // IamportClient 초기화
    }

    public void someMethod() {
        // API key와 Secret 사용 예시
        System.out.println("API Key: " + apiKey);
        System.out.println("API Secret: " + apiSecret);
    }

    /**
     * 결제 검증: Iamport에서 결제 정보를 가져와 확인
     */
    public IamportResponse<Payment> validatePayment(String impUid) throws IOException, IamportResponseException {
        return iamportClient.paymentByImpUid(impUid);
    }

    /**
     * 결제 취소
     */
    public IamportResponse<Payment> cancelPayment(CancelData impUid) throws IOException, IamportResponseException {
        return iamportClient.cancelPaymentByImpUid(impUid);
    }
}
