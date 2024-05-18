package domain;

import java.io.Serializable;

public class Account implements Serializable {
   private final String code;
   private final String ownerId;
   private double balance;
   
   public Account(String code, String ownerId, double balance) {
      this.code = code;
      this.ownerId = ownerId;
      this.balance = balance;
   }
   @Override 
   public String toString() {
      return String.format("Account(%s,%s,%f)",code,ownerId,balance);
   }
   public String getCode() { return code; }
   public String getOwnerId() { return ownerId; }
   public double getBalance() { return balance; }
   public boolean deposit(double amount) {
      if (amount <= 0.0) return false;
      balance += amount;
      return true;
   }
   public boolean withdraw(double amount) {
      if (amount <= 0.0 || amount > balance) return false;
      balance -= amount;
      return true;
   }
}
