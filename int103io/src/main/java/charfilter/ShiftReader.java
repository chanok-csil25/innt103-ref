package charfilter;

import java.io.*;

public class ShiftReader extends FilterReader {

   private final Shifter shifter;

   public ShiftReader(Reader in, int shift) {
      super(in);
      this.shifter = new Shifter(-shift);
   }

   @Override
   public int read(char[] cbuf, int off, int len) throws IOException {
      len = super.read(cbuf, off, len);
      shifter.shift(cbuf, off, len);
      return len;
   }

   @Override
   public int read() throws IOException {
      int i = super.read();
      return (i == -1) ? i : shifter.shift((char) i);
   }

}
