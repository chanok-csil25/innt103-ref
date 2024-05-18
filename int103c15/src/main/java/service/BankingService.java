package service;

import domain.Account;
import domain.Customer;
import java.util.*;

public class BankingService {
   private final AccountRepository accounts;
   private final CustomerRepository customers;
   
   public BankingService(AccountRepository a, CustomerRepository c) {
      accounts = a; customers = c;
   }

   public Customer registerCustomer(String customerName) {
      if (customerName == null) return null;
      return customers.addCustomer(customerName);
   }

   public Customer renameCustomer(String customerId, String name) {
      if (customerId == null || name == null) return null;
      Customer c = customers.findCustomer(customerId);
      if (c == null) return null;
      c.setName(name);
      return customers.updateCustomer(c);
   }

   public Customer findCustomer(String customerId) {
      if (customerId == null) return null;
      return customers.findCustomer(customerId);
   }
   
   public Collection<Customer> allCustomers() {
      return customers.allCustomers();
   }

   public Account addAccount(String ownerId) {
      if (ownerId == null || findCustomer(ownerId) == null) return null;
      return accounts.addAccount(ownerId);
   }

   public Account findAccount(String accountCode) {
      if (accountCode == null) return null;
      return accounts.findAccount(accountCode);
   }

   public Account deposit(String accountCode, double amount) {
      Account acc = findAccount(accountCode);
      if (!acc.deposit(amount)) return null;
      return accounts.updateAccount(acc);
   }

   public Account withdraw(String accountCode, double amount) {
      Account acc = findAccount(accountCode);
      if (!acc.withdraw(amount)) return null;
      return accounts.updateAccount(acc);
   }

   public Collection<Account> allAccounts() {
      return accounts.allAccounts();
   }

   public Collection<Account> allAccountsOwnedBy(String ownerId) {
      if (ownerId == null || findCustomer(ownerId) == null) return null;
      return accounts.allAccountsOwnedBy(ownerId);
   }
   
}