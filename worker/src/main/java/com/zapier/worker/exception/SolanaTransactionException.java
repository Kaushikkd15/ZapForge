package com.zapier.worker.exception;

import lombok.Getter;

@Getter
public class SolanaTransactionException extends RuntimeException{
    private final String transactionsSignature;
    private final Object details;

    public SolanaTransactionException(String message){
        super(message);
        this.transactionsSignature = null;
        this.details = null;
    }

    public SolanaTransactionException(String message, Throwable cause){
        super(message, cause);
        this.transactionsSignature = null;
        this.details = null;
    }

    public SolanaTransactionException(String message,String transactionsSignature, Object details){
        super(message);
        this.transactionsSignature = transactionsSignature;
        this.details = details;
    }
}
