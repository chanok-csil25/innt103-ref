package domain;

import java.io.Serializable;

public class Customer implements Serializable {
   private final String id;
   private String name;
   public Customer(String id, String name) {
      this.id = id; this.name = name;
   }
   public String getId() { return id; }
   public String getName() { return name; }
   public void setName(String name) { this.name = name; }
   @Override public String toString() { 
      return String.format("Customer(%s,%s)",id,name);
   }
   
}
