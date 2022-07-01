package com.example.paymentservice.api;

import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/wallet")
public class WalletApi {
    private final WalletService walletService;

    public WalletApi(WalletService walletService) {
        this.walletService = walletService;
    }

    @RolesAllowed("user")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Wallet> getUserWallet() {
        Wallet wallet = walletService.getUserWallet();
        if (wallet != null) {
            return ResponseEntity.ok(wallet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
