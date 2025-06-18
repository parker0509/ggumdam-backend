package com.example.payment_service.service;

import com.example.payment_service.entity.PaymentRecord;
import com.example.payment_service.repository.PaymentRecordRepository;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FundingService {

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    public void savePaymentRecord(Payment payment) {
        PaymentRecord record = PaymentRecord.builder()
                .impUid(payment.getImpUid())
                .amount(payment.getAmount().longValue())
                .status(payment.getStatus())
                .payMethod(payment.getPayMethod())
                .buyerName(payment.getBuyerName())
                .buyerEmail(payment.getBuyerEmail())
                .build();

        paymentRecordRepository.save(record);
    }



}
