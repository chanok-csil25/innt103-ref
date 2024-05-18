package dummy;

import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {
   private final int code;
   private final String name;
   private transient char initial; // transient will not be serialized.
   public Company(int code, String name) {
      this.code=code;
      this.name=Objects.requireNonNull(name);
      forTransient();
   }
   public final void forTransient() {
      initial = Character.toUpperCase(name.charAt(0));
   }
   @Override
   public String toString() {
      return "Company(" + code + ',' + name + ',' + initial + ')';
   }
}
