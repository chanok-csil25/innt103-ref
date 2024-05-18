package charfilter;

import java.io.*;

public class ShiftWriter extends FilterWriter {

   private Shifter shifter;

   public ShiftWriter(Writer out, int shift) {
      super(out);
      this.shifter = new Shifter(shift);
   }

   @Override
   public void write(String str, int off, int len) throws IOException {
      super.write(shifter.shift(str.toCharArray(),off,off+len),off,len);
   }

   @Override
   public void write(char[] cbuf, int off, int len) throws IOException {
      super.write(shifter.shift(cbuf,off,off+len),off,len);
   }

   @Override
   public void write(int c) throws IOException {
      super.write(shifter.shift((char) c));
   }
}
