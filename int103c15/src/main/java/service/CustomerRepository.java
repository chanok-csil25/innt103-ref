package service;

import domain.Customer;
import java.util.*;

public interface CustomerRepository {
   public Customer addCustomer(String customerName);
   public Customer updateCustomer(Customer cust);
   public Customer findCustomer(String customerId);
   public Collection<Customer> allCustomers();
}
