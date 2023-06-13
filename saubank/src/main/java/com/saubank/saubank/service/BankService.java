package com.saubank.saubank.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.saubank.saubank.model.Bank;
import com.saubank.saubank.model.Card;
import com.saubank.saubank.model.User;
import com.saubank.saubank.request.BankRequest;
import com.saubank.saubank.request.EncryptedPaymentRequest;
import com.saubank.saubank.response.BankResponse;
import com.saubank.saubank.respository.BankRepository;
import com.saubank.saubank.utils.AndroidBackendCommuication;
import com.saubank.saubank.utils.BackendBackendCommunication;
import com.saubank.saubank.utils.EncryptionUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.saubank.saubank.utils.Constant.SECRET_KEY_BACKEND;

@Service
public class BankService {

    private final UserService userService;

    private final CardService cardService;
    private final BankRepository bankRepository;
    private final EncryptionUtil encryptionUtil;
    private final AndroidBackendCommuication androidBackendCommuication;
    private final BackendBackendCommunication backendBackendCommunication;

    private final ObjectMapper objectMapper;


    public BankService(UserService userService, CardService cardService, BankRepository bankRepository, EncryptionUtil encryptionUtil, AndroidBackendCommuication androidBackendCommuication, BackendBackendCommunication backendBackendCommunication, ObjectMapper objectMapper) {
        this.userService = userService;
        this.cardService = cardService;
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

            Card card = cardService.getCard(bankRequest.getCardNumber());

            User user = userService.getUser(card.getUser().getId());

            if(user.getUserBalance().compareTo(bankRequest.getAmount()) < 0){
                throw new RuntimeException("Yetersiz bakiye");
            }
            user.setUserBalance(user.getUserBalance().subtract(bankRequest.getAmount()));
            userService.updateUser(user);

            BankResponse bankResponse = new BankResponse();
            bankResponse.setSuccess(true);
            return bankResponse;

        }catch (Exception e){
            throw new RuntimeException("Beklenmedik bir hata oluştu.");
        }
    }

    public BigDecimal getBank(){
        return userService.getUser("9aae5862-3601-4bcf-b5af-cae44e6cd705").getUserBalance();
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
