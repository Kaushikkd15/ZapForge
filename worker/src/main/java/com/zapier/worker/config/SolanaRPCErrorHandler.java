package com.zapier.worker.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URI;

public class SolanaRPCErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }



    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
       String body =  new String(response.getBody().readAllBytes());
       throw new RestClientException(
               String.format("RPC Error: %s - %s", response.getStatusCode(), body)
       );
    }
}
