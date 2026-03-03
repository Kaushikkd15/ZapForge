package com.zapier.worker.domain.action;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class SendSolanaAction implements DomainAction{

    String recipientAddress;
    BigDecimal amount;
    String memo;

    @Override
    public String getActionType() {
        return "SEND_SOLANA";
    }
}
