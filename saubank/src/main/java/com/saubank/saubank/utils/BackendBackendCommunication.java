package com.saubank.saubank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saubank.saubank.request.EncryptedPaymentRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.saubank.saubank.utils.Constant.SECRET_KEY_BACKEND;

@Component
public class BackendBackendCommunication {

    private final ObjectMapper objectMapper;
    private final EncryptionUtil encryptionUtil;

    public BackendBackendCommunication(ObjectMapper objectMapper, EncryptionUtil encryptionUtil) {
        this.objectMapper = objectMapper;
        this.encryptionUtil = encryptionUtil;
    }

    public String getBackendToBackendEncryptedAndSignatureDataTransaction(EncryptedPaymentRequest encryptedRequest, String signature, String randomKey, String secretKey)
    {
        try {
            String json = objectMapper.writeValueAsString(encryptedRequest); // Convert body to json
            System.out.println("Encrypted Request -Backend:  " + json);
            Boolean isSignatureValid = encryptionUtil.checkSignature(signature, randomKey, json, secretKey); // Verify signature
            System.out.println("Signature is Valid-Backend:  " + isSignatureValid);
            if (!isSignatureValid) {
                throw new RuntimeException("Signature is not valid");
            }
            String decryptedData = encryptionUtil.decrypt(encryptedRequest.getData(), secretKey); // When signature is valid, decrypt data
            System.out.println("Decrypted Request-Backend: " + decryptedData);

            return decryptedData;
        }
        catch (Exception e)
        {
            throw new RuntimeException("BackendToBackendEncryptedAndSignatureDataTransaction error: " + e.getMessage());
        }
    }

    public String sendBackendToBackendEncryptedDataTransaction(String token,String secretKey)
    {
        try {
            String encryptedToken = encryptionUtil.encrypt(token, secretKey); // Encrypt token
            System.out.println("Encrypted Token-Backend: " + encryptedToken);

            return encryptedToken;
        }
        catch (Exception e)
        {
            throw new RuntimeException("BackendToBackendEncryptedAndSignatureDataTransaction error: " + e.getMessage());
        }
    }

}
