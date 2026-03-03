package com.zapier.worker.exception;

import com.zapier.worker.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponseDto> handleRestClientException(RestClientException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorTime(LocalDateTime.now())
                .errorCode(HttpStatus.BAD_GATEWAY)
                .errorMessage(exception.getMessage())
                .apiPath(webRequest.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponseDto);
    }

    @ExceptionHandler(SolanaTransactionException.class)
    public ResponseEntity<ErrorResponseDto> handleSolanaTranscationException(SolanaTransactionException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorTime(LocalDateTime.now())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage(exception.getMessage())
                .apiPath(webRequest.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(SlackApiException.class)
    public ResponseEntity<ErrorResponseDto> handleSlackApiException(SlackApiException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorTime(LocalDateTime.now())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage(exception.getMessage())
                .apiPath(webRequest.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }
}
