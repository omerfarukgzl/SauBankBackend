package com.saubank.saubank.controller;


import com.saubank.saubank.request.BankRequest;
import com.saubank.saubank.request.EncryptedPaymentRequest;
import com.saubank.saubank.response.BankInfoResponse;
import com.saubank.saubank.response.BankResponse;
import com.saubank.saubank.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController()
@RequestMapping("/v1/saubank")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @GetMapping("/payment2")
    public String payment(){
        return bankService.payment();
    }

    @PostMapping("/payment")
    public ResponseEntity<BankResponse> payment(HttpServletRequest request, @RequestBody EncryptedPaymentRequest encryptedPaymentRequest){
        String signature = request.getHeader("x-signature");
        String randomKey = request.getHeader("x-rnd-key");
        return ResponseEntity.ok( bankService.payment(encryptedPaymentRequest,signature,randomKey));

    }
    @GetMapping("/getBank")
    public ResponseEntity<BankInfoResponse> getBank(){
        return ResponseEntity.ok(bankService.getBank());
    }


}
