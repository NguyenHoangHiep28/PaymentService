package com.example.paymentservice.entity.seeder;

import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.repository.WalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class WalletSeeder implements CommandLineRunner {
    private final WalletRepository walletRepository;

    public WalletSeeder(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Wallet shopWallet = new Wallet();
        shopWallet.setUserId("ec082bbe-f7e9-46d1-b9c9-9ed0c568bff2");
        shopWallet.setName("MY SHOP");
        shopWallet.setBalance(new BigDecimal(1000000));

        Wallet userWallet = new Wallet();
        userWallet.setUserId("43645114-7394-4080-8abe-fa24adcc90e2");
        userWallet.setName("NGUYEN VAN A");
        userWallet.setBalance(new BigDecimal(1000000));

        List<Wallet> wallets = new ArrayList<>();
        wallets.add(shopWallet);
        wallets.add(userWallet);
        walletRepository.saveAll(wallets);
    }
}
