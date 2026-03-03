package com.zapier.worker.acl.impl;

import com.zapier.worker.acl.SolanaServiceACL;
import com.zapier.worker.config.SolanaRPCErrorHandler;
import com.zapier.worker.domain.action.ActionResult;
import com.zapier.worker.domain.action.SendSolanaAction;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Base58;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.util.Arrays;

@Slf4j
@Component
public class SolanaServiceImpl implements SolanaServiceACL {

    @Value("${solana.rpc.url:https//api.mainnet-beta.solana.com}")
    private String rpcUrl;

    @Value("${solana.private.key}")
    private String privateKeyBase58;

    private static final long LAMPORTS_PER_SOL= 1_000_000_000L;
    private static final int  MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    private final RestTemplate restTemplate;

    public SolanaServiceImpl(){
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new SolanaRPCErrorHandler());
    }


    @Override
    public ActionResult sendSolana(SendSolanaAction action) {
         try{
            log.info("Intiating Solana transfer : {} SOL to {}", action.getAmount(), action.getRecipientAddress());

            validateInputs(action);

            long lamports = convertSolToLamports(action.getAmount());

            log.debug("Converted {}sol to {}lamports",action.getAmount(),lamports);

            byte[] privateKeyBytes = decodeBase58PrivateKey(privateKeyBase58);
            KeyPair keyPair = createKeyPairFromPrivateKey(privateKeyBytes);



         }catch()
    }

    private void validateInputs(SendSolanaAction action){
        if(action.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        if(action.getAmount().compareTo(new BigDecimal(1000)) > 0){
           log.warn("Large transaction detected: {}", action.getAmount());
        }

        if(action.getRecipientAddress() == null || action.getRecipientAddress().isEmpty()){
            throw new IllegalArgumentException("Recipient Address cannot be empty");
        }

        if(!action.getRecipientAddress().matches("^[1-9A-HJ-NP-Za-km-z]{32,44}$")){
            throw new IllegalArgumentException("Recipient Address is invalid");
        }
    }

    private long convertSolToLamports(BigDecimal solAmount){
        BigDecimal lamports = solAmount.multiply(BigDecimal.valueOf(LAMPORTS_PER_SOL));
        if(lamports.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) > 0){
            throw new IllegalArgumentException("Amount too Large");
        }
        return lamports.longValue();
    }

    private byte[] decodeBase58PrivateKey(String base58Key){
        try{
            if(base58Key == null || base58Key.isEmpty()){
                throw new IllegalArgumentException("Private Key not configured");
            }

            byte[] decoded = Base58.decode(base58Key);

            if(decoded.length != 64){
                throw new IllegalArgumentException(
                        String.format("Invalid private key length : %d (expected 64 bytes)", decoded.length)
                );
            }
            return decoded;
        }catch(Exception e){
            log.error("Failed to decode Base58 private Key");
            throw new IllegalArgumentException("Invalid private key format", e);
        }
    }

    private KeyPair createKeyPairFromPrivateKey(byte[] privateKeyBytes) throws Exception{
        try{
            byte[] secretKey = Arrays.copyOfRange(privateKeyBytes,0,32);
            byte[] publicKey = Arrays.copyOfRange(privateKeyBytes,32, 64);

            KeyPair keyPair = new KeyPair(secretKey,publicKey);

            log.debug("Created keypair with public key: {}", Base58.encode(publicKey));
            return keyPair;
        }catch (Exception e){

        }
    }

    private static class KeyPair {
        private final byte[] secretKey;
        private final byte[] publicKey;

        public KeyPair(byte[] secretKey, byte[] publicKey) {
            this.secretKey = secretKey;
            this.publicKey = publicKey;
        }

        public byte[] getPublicKey() {
            return publicKey;
        }

        public byte[] getSecretKey() {
            return secretKey;
        }

        public byte[] sign(byte[] message) throws Exception {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message);

            byte[] signature = new byte[64];
            System.arraycopy(hash, 0, signature, 0, 32);
            System.arraycopy(secretKey, 0, signature, 32, 32);

            return signature;
        }
    }


}
