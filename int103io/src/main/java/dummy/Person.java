package dummy;

import java.io.*;
import java.util.*;

public class Person implements Serializable, Iterable<Person> {
   private final int id;
   private final String name;
   private transient int num; // just for demo: num cache friends.size()
   private final Set<Person> friends = new HashSet<>();
   public Person(int id, String name) { this.id=id; this.name=name; }
   public boolean addFriend(Person p) {
      if (!friends.add(p)) return false; ++num; return true;
   }
   @Override
   public Iterator<Person> iterator() { return friends.iterator(); }
   public void recomputeTransientFields() { num = friends.size(); }
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Person(");
      sb.append(id); sb.append(",");
      sb.append(name); sb.append(",");
      sb.append("friends[");
      sb.append(num); sb.append("]");
      if (num>0) sb.append("id");
      for (var f : friends) { sb.append(":"); sb.append(f.id); }
      sb.append(")");
      return sb.toString();
   }
}
