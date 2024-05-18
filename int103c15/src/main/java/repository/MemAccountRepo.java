package repository;

import domain.Account;
import java.util.*;
import service.AccountRepository;

public class MemAccountRepo implements AccountRepository {
      
   private static long nextCode = 0;
   private final Map<String, Account> repo = new HashMap<>();

   @Override
   public Account addAccount(String ownerId) {
      String accountCode = "A" + ++nextCode;
      Account acc = new Account(accountCode,ownerId,0.0);
      if (repo.putIfAbsent(ownerId, acc) == null) return acc;
      return null;
   }

   @Override
   public Account updateAccount(Account acc) {
      try {
         repo.replace(acc.getCode(), acc);
      } catch (Exception e) {
         return null;
      }
      return acc;
   }

   @Override
   public Account findAccount(String accountCode) {
      return repo.get(accountCode);
   }

   @Override
   public Collection<Account> allAccounts() {
      return repo.values();
   }

   @Override
   public Collection<Account> allAccountsOwnedBy(String ownerId) {
      return repo.values()
         .stream()
         .filter(a -> a.getOwnerId().equals(ownerId))
         .toList();
   }
   
}
