package charfilter;

public class Shifter {
   private static final int MIN = 32; // (space)
   private static final int MAX = 126; // (~)
   private static final int LEN = MAX - MIN + 1;
   private final int shift;
   public Shifter(int shift) {
      shift %= LEN;
      this.shift = shift < 0 ? shift+LEN : shift;
   }

   public char shift(char c) { return shift(new char[] {c},0,1)[0]; }
   public char[] shift(char[] c) { return shift(c, 0,c.length); }

   public char[] shift(char[] c, int begin, int end) {
      if (c == null || begin < 0 || begin > end || end > c.length)
         return null;
      for (int i = begin; i < end; i++) {
         if (c[i] >= MIN && c[i] <= MAX)
            c[i] = (char) ((c[i] - MIN + shift) % LEN + MIN);
      }
      return c;
   }
}
