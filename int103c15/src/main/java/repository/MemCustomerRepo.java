package repository;

import domain.Customer;
import java.util.*;
import service.CustomerRepository;

public class MemCustomerRepo implements CustomerRepository {
   
   private static long nextId = 0;
   private final Map<String, Customer> repo = new HashMap<>();

   @Override
   public Customer addCustomer(String customerName) {
      String customerId = "C" + ++nextId;
      Customer c = new Customer(customerId,customerName);
      if (repo.putIfAbsent(customerId, c) == null) return c;
      return null;
   }

   @Override
   public Customer updateCustomer(Customer customer) {
      try {
         repo.replace(customer.getId(), customer);
      } catch (Exception e) {
         return null;
      }
      return customer;
   }

   @Override
   public Customer findCustomer(String customerId) {
      return repo.get(customerId);
   }

   @Override
   public Collection<Customer> allCustomers() {
      return repo.values();
   }   
}
