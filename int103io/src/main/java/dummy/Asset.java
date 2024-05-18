package dummy;

import java.util.Objects;

public class Asset {
   private final int code;
   private final String name;
   private final char initial;
   public Asset(int code, String name) {
      this.code = code;
      this.name = Objects.requireNonNull(name);
      initial = Character.toUpperCase(name.charAt(0));
   }
   @Override
   public String toString() {
      return "Asset(" + code + ',' + name + " [" + initial + "])";
   }
}
