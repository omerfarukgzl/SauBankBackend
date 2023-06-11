package com.saubank.saubank.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.saubank.saubank.request.BankRequest;
import com.saubank.saubank.request.EncryptedPaymentRequest;
import com.saubank.saubank.response.BankResponse;
import com.saubank.saubank.respository.BankRepository;
import com.saubank.saubank.utils.AndroidBackendCommuication;
import com.saubank.saubank.utils.BackendBackendCommunication;
import com.saubank.saubank.utils.EncryptionUtil;
import org.springframework.stereotype.Service;

import static com.saubank.saubank.utils.Constant.SECRET_KEY_BACKEND;

@Service
public class BankService {
    private final BankRepository bankRepository;
    private final EncryptionUtil encryptionUtil;
    private final AndroidBackendCommuication androidBackendCommuication;
    private final BackendBackendCommunication backendBackendCommunication;

    private final ObjectMapper objectMapper;


    public BankService(BankRepository bankRepository, EncryptionUtil encryptionUtil, AndroidBackendCommuication androidBackendCommuication, BackendBackendCommunication backendBackendCommunication, ObjectMapper objectMapper) {
        this.bankRepository = bankRepository;
        this.encryptionUtil = encryptionUtil;
        this.androidBackendCommuication = androidBackendCommuication;
        this.backendBackendCommunication = backendBackendCommunication;
        this.objectMapper = objectMapper;
    }
    public String payment(){
        return "token_id";
    }

    public BankResponse payment(EncryptedPaymentRequest encryptedPaymentRequest, String signature, String randomKey){

        System.out.println("Encrypted Request -Backend: " + encryptedPaymentRequest );


        String decryptedData = backendBackendCommunication.getBackendToBackendEncryptedAndSignatureDataTransaction(encryptedPaymentRequest, signature, randomKey,SECRET_KEY_BACKEND);

        try {
            BankRequest bankRequest = objectMapper.readValue(decryptedData, BankRequest.class);

        }catch (Exception e){
            throw new RuntimeException("Beklenmedik bir hata oluştu.");
        }



        BankResponse bankResponse = new BankResponse();
        bankResponse.setSuccess(true);
        return bankResponse;


    }



}




/* try {
            String json = objectMapper.writeValueAsString(encryptedPaymentRequest); // Convert body to json
            System.out.println("Encrypted Request -Mobile: " + json );
            Boolean isSignatureValid = encryptionUtil.checkSignature(signature,randomKey,json,SECRET_KEY_CLIENT); // Verify signature
            System.out.println("Signature is Valid-Mobile: " + isSignatureValid );
            if(!isSignatureValid){
                throw new RuntimeException("Signature is not valid-Mobile");
            }
            String decryptedData = encryptionUtil.decrypt(encryptedPaymentRequest.getData(),SECRET_KEY_CLIENT); // When signature is valid, decrypt data
            System.out.println("Decrypted Request-Mobile: " + decryptedData );

        }catch (Exception e) {
            throw new RuntimeException("Beklenmedik bir hata oluştu.");
        }*/
