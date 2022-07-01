package com.example.paymentservice.service;

import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final IAuthenticationFacade authenticationFacade;

    public WalletService(WalletRepository walletRepository, IAuthenticationFacade authenticationFacade) {
        this.walletRepository = walletRepository;
        this.authenticationFacade = authenticationFacade;
    }

    public Wallet getUserWallet() {
        String userId = authenticationFacade.getAuthenticatedUserId();
        Optional<Wallet> wallet = walletRepository.findById(userId);
        return wallet.orElse(null);
    }
}
