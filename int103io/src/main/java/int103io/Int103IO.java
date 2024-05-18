package int103io;

import charfilter.*;
import dummy.*;
import java.io.*;
import java.util.*;

public class Int103IO {

   static final String PATH = "C:\\Users\\KriengkraiPorkaew\\Documents\\";

   public static void main(String[] args) {
      
      showMethods();
      showClassHierarchy();

      // IOStream from/to file
      demo011FileOutputStream();
      demo012FileInputStream();

      // IOStream from/to byte[]
      demo021ByteArrayOutputStream();
      demo022ByteArrayInputStream();

      // Concatenation of 2 InputStreams
      demo030SequenceInputStream();

      // Pipe connecting from OutputStream to InputStream
      demo040PipedInputOutputStream();

      // IO Stream Decolator buffering bytes
      demo051BufferedOutputStream();
      demo052BufferedInputStream();

      // IO Stream Decolator mapping bytes from/to primitives/objects
      demo060DataInputOutputStream();
      demo070ObjectInputOutputStream();
      demo071CircularObjectInputOutputStream();

      // IO Stream Decolator printing every types out as formatted text string
      demo080PrintStream();

      // =======================================
      // Reader is similar to InputStream and Writer is similar to OutputStream

      // similar to FileOutputStream and FileInputStream
      demo111FileWriter(); // write chars to a file
      demo112FileReader(); // read chars from a file

      // similar to ByteArrayOutputStream and ByteArrayInputStream
      demo121CharArrayWriter(); // write chars/String to char[]
      demo122CharArrayReader(); // read chars from char[]

      // similar to CharArrayWriter and CharArrayReader 
      // String as source/destination instead of CharArray
      demo131StringWriter(); // write chars/String to String
      demo132StringReader(); // read chars from String

      // PipedWriter similar to PipedOutputStream 
      // PipedReader similar to PipedInputStream
      demo140PipedReaderWriter();
      
      demo151BufferedWriter(); // similar to BufferedOutputStream
      demo152BufferedReader(); // similar to BufferedInputStream
      
      demo161FilterWriter(); // demo how to extend FilterWriter
      demo162FilterReader(); // demo how to extedn FilterReader

      demo170PrintWriter(); // similar to PrintStream
   }
   public static void showMethods() {
      System.out.println("""
         ##### Java IO Class Hierarchy #####
            OutputStream: abstract
               OutputStream.nullOutputStream()
               .write(byte)
               .write(byte[])
               .flush()
               .close()
            InputStream: abstract
               InputStream.nullInputStream()
               .available() -> int
               .read() -> int
               .read(byte[]) -> int
               .readAllBytes() -> byte[]
               .readNBytes(int len) -> byte[]
               .skip(long) -> long
               .markSupported() -> boolean
               .mark(int limit)
               .reset()
               .close()
               .transferTo(OutputStream) -> long
            Writer: abstract
               Write.nullWriter()
               .write(char)
               .write(char[])
               .write(String)
               .append(char)
               .append(CharSequence)
               .flush()
               .close()
            Reader: abstract
               Reader.nullReader()
               .read() -> int
               .read(char[]) -> int
               .ready() -> boolean
               .skip(long) -> long
               .markSupported() -> boolean
               .mark(int limit)
               .reset()
               .close()
               .transferTo(Writer) -> long
         End of some methods in InputStream/OutputStream/Reader/Writer Class""");
   }
   public static void showClassHierarchy() {
      System.out.println("""
         ##### Java IO Class Hierarchy #####
            InputStream (abstract)
               FileInputStream (read bytes from a file)
               ByteArrayInputStream (read bytes from byte[])
               PipedInputStream (read bytes from PipedOutputStream)
               SequenceInputStream (concat two InputStreams)
               FilterInputStream (decolator of InputStream)
                  BufferedInputStream (buffer byte-based InputStream)
                  PushbackInputStream (allow to push bytes back to InputStream)
                  DataInputStream (read InputStream from bytes to primitives)
               ObjectInputStream (read InputStream from bytes to objects)
                (decolator of InputStream)
            OutputStream (abstract)
               FileOutputStream (write bytes to a file)
               ByteArrayOutputStream (write bytes to byte[])
               PipedOutputStream (write bytes to PipedInputStream)
               FilterOutputStream (decolator of OutputStream)
                  PrintStream (write formatted text to an OutputStream/file)
                   (decolator of OutputStream/FileOutputStream)
                  BufferedOutputStream (buffer byte-based OutputStream)
                  DataOutputStream (write primitives to bytes in OutputStream)
               ObjectOutputStream (write objects to bytes in OutputStream)
                (decolator of OutputStream)
            Reader (abstract)
               InputStreamReader (read chars from InputStream) (wrap around InputStream)
                  FileReader (read chars from a file) (wrap around FileInputStream)
               CharArrayReader (read chars from char[])
               StringReader (read chars from String)
               PipedReader (read chars from PipedWriter)
               BufferedReader (buffer char reader) (decolator of Reader)
                  LineNumberReader
               FilterReader (abstract) (decolator of Reader)
                  PushbackReader (allow to push chars back to Reader)
            Writer (abstract)
               OutputStreamWriter (write chars to OutputStream) (wrap around OutputStream)
                  FileWriter (write chars to a file) (wrap around FileOutputStream)
               CharArrayWriter (write chars to char[])
               StringWriter (write chars to String)
               PipedWriter (write chars to PipedReader)
               BufferedWriter (buffer char writer) (decolator of Writer)
               FilterWriter (abstract) (decolator of Writer)
               PrintWriter (write formatted text to a Writer/OutputStream/file)
                (decolator of Writer/BufferedWriter)
         End of InputStream/OutputStream/Reader/Writer Class Hierarchy""");
   }
   // =============================
   public static void demo011FileOutputStream() {
      System.out.println("### demo011FileOutputStream ###");
      //*** FileOutputStream writes bytes to a file. ***
      /*
      FileOutputStream extends OutputStream
         constructor(String filename)
         constructor(String filename, boolean append)
         .write(byte)
         .write(byte[])
         .flush()
      */
      String filename = PATH + "JavaIO-file.dat";

      try (FileOutputStream fo = new FileOutputStream(filename)) {
         System.out.println("  Writing bytes to a file:");
         System.out.println("    " + filename);
         // write bytes into the file output stream
         fo.write(69);
         fo.write(new byte[] {69, 70, 71, 72, 72});
         fo.write(254); //?
         fo.flush(); // force everything to go into the output stream
         fo.write(-5); //?
         fo.write(32);
         System.out.println("  Done writing to the file.");
      } catch (IOException e) {
         System.out.println("  Exception: FileOutputStream");
         e.printStackTrace();
      }
      // re-open the file for appending bytes
      try (FileOutputStream fo = new FileOutputStream(filename, true)) {
         System.out.println("  Appending bytes to the file.");
         // true = append at the end of the file
         fo.write(new byte[] {106,104,103,102,101,100});
         System.out.println("  Done appending to the file.");
      } catch (IOException e) {
         System.out.println("  Exception: FileOutputStream");
         e.printStackTrace();
      }
   }
   public static void demo012FileInputStream() {
      System.out.println("### demo012FileInputStream ###");
      //*** FileInputStream reads bytes from a file. ***
      /*
      FileInputStream extends InputStream
         constructor(String filename)
         .read() -> int (byte)
         .readNBytes(int len) -> byte[]
         .available() -> int
      */
      String filename = PATH + "JavaIO-file.dat";

      System.out.println("  Reading bytes from a file:");
      System.out.println("    " + filename);

      try (FileInputStream fi = new FileInputStream(filename)) {
         System.out.println("  There are " + fi.available() + " bytes available.");
         System.out.println("  Read from the file one byte at a time:");
         int i;
         while ((i = fi.read()) != -1) {
            System.out.print(" " + (byte) i);
         }
         System.out.println();
      } catch (IOException e) {
         System.out.println("  Exception: FileInputStream");
         e.printStackTrace();
      }
      // re-open the file to read from the beginning
      try (FileInputStream fi = new FileInputStream(filename)) {
         System.out.println("  Reopen the file.");
         int len = 8;
         System.out.println("  Read from the file " + len + " bytes at a time.");
         byte[] ba;
         do {
            ba = fi.readNBytes(len);
            if (ba.length>0)
               System.out.println("    out: " + Arrays.toString(ba));
         } while (ba.length == len);
         System.out.println("  Done reading");
      } catch (IOException e) {
         System.out.println("  Exception: FileInputStream");
         e.printStackTrace();
      }
   }
   public static void demo021ByteArrayOutputStream() {
      System.out.println("### demo021ByteArrayOutputStream ###");
      //*** ByteArrayOutputStream writes bytes to byte[] ***
      /*
      ByteArrayOutputStream extends OutputStream
         constructor()
         .write(byte)
         .write(byte[])
         .reset()
         .toByteArray() -> byte[]
         .writeTo(OutputStream)
      */
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      try (bo) {
         System.out.println("  Writing to ByteArrayOutputStream.");
         bo.write(69);
         bo.write(new byte[] {69, 70, 71, 72, 72});
         bo.write(254); //?
         System.out.println("  Viewing the content.");
         // get the content but does not remove it from the stream
         System.out.println("  content:" + Arrays.toString(bo.toByteArray()));
         // write more into the stream
         bo.write(-5); //?
         bo.write(32);
         // get the result again which includes the old result too
         System.out.println("  again:" + Arrays.toString(bo.toByteArray()));
         bo.write(new byte[] {100,100,100,100,101});
         System.out.println("  Reseting the stream will remove its content.");

         bo.reset(); // remove everything from the stream
         bo.write(new byte[] {106,104,103,102,101,100});
         System.out.println("  after reset and write new content:"
            + Arrays.toString(bo.toByteArray()));

         // save the current content to a file
         String filename = PATH + "JavaIO-ByteArrayOutputToFile.dat";
         try (FileOutputStream fo = new FileOutputStream(filename)) {
            //write the content in ByteArrayOutputStream to FileOutputStream
            System.out.println("  Also save the current content to a file:");
            System.out.println("    " + filename);
            bo.writeTo(fo);
         } catch (IOException e) {
            System.out.println("  Exception: ByteArrayOutputToFileOutput");
            e.printStackTrace();
         }
      } catch (IOException e) {
         System.out.println("  Exception: ByteArrayOutputStream");
         e.printStackTrace();
      }
      System.out.println("  Done Writing.");
   }
   public static void demo022ByteArrayInputStream() {
      System.out.println("### demo022ByteArrayInputStream ###");
      //*** ByteArrayInputStream reads bytes from byte[] ***
      /*
      ByteArrayInputStream extends InputStream
         constructor(byte[])
         .read() -> int (byte)
         .readNBytes(int len) -> byte[]
         .mark(int limit)
         .reset()
         .transferTo(OutputStream)
      */
      byte[] bytes = {69,69,70,71,72,72,(byte)256,-5,32,106,104,103,102,102,102};
      ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
      try (bi) {
         System.out.println("  Reading bytes from byte[]");
         System.out.println("  There are " + bi.available() + " bytes available.");
         System.out.println("  Read from the source one byte at a time:");
         int i;
         while ((i = bi.read()) != -1) {
            System.out.print(" " + (byte) i);
         }
         System.out.println();
         System.out.println("  Done Reading.");
         if (bi.markSupported()) {
            System.out.println("  ByteArrayInputStream support marking/reseting.");
         }
         System.out.println("  Reset the InputStream and read it all over again.");

         // reset to go back to read from the beginning
         bi.reset();
         int len = 8;
         System.out.println("  Read from the stream " + len + " bytes at a time.");
         byte[] ba;
         do {
            ba = bi.readNBytes(len);
            if (ba.length>0)
               System.out.println("    out: " + Arrays.toString(ba));
         } while (ba.length == len);
         System.out.println("  Done Reading the second time.");
         System.out.println("  Reset the InputStream.");

         // reset again to read from the beginning
         bi.reset();
         len = 3;
         System.out.println("  Skip " + len + " bytes");
         bi.skip(len);

         // save the remaining content to a file
         String filename = PATH + "JavaIO-ByteArrayInputToFile.dat";
         try (FileOutputStream fo = new FileOutputStream(filename)) {
            System.out.println("  Saving the remaining content to a file:");
            System.out.println("    " + filename);
            //transfer the content in ByteArrayInputStream to FileOutputStream
            bi.transferTo(fo);
         } catch (IOException e) {
            System.out.println("  Exception: ByteArrayInputToFileOutput");
            e.printStackTrace();
         }
      } catch (IOException e) {
         System.out.println("  Exception: ByteArrayInputStream");
         e.printStackTrace();
      }
   }
   public static void demo030SequenceInputStream() {
      System.out.println("### demo030SequenceInputStream ###");
      //*** SequenceInputStream reads bytes from nultiple InputStreams ***
      /*
      SequenceInputStream extends InputStream
         constructor(InputStream is1, InputStream is2)
         .read() -> int (byte)
         .readNBytes(int len) -> byte[]
         .available() -> int
      */
      byte[] bytes1 = {69,69,70,71,72,72,(byte)256,-5};
      byte[] bytes2 = {32,32,32,106,104,103,-1,-1,102,102,102};

      // concat two InputStreams
      try (ByteArrayInputStream bi1 = new ByteArrayInputStream(bytes1);
         ByteArrayInputStream bi2 = new ByteArrayInputStream(bytes2);
         SequenceInputStream si = new SequenceInputStream(bi1,bi2)) {
         int len = si.available();
         System.out.println("  There are " + len + " bytes available.");
         System.out.println("  read " + len + " bytes: "
            + Arrays.toString(si.readNBytes(len)));
         System.out.println("  try reading again; one byte at a time:");
         int i;
         while ((i = si.read()) != -1) {
            System.out.print(" " + (byte) i);
         }
         System.out.println();
         System.out.println("  Done Reading from both InputStreams.");
      } catch (IOException e) {
         System.out.println("  Exception: SequenceInputStream");
         e.printStackTrace();
      }
   }
   public static void demo040PipedInputOutputStream() {
      System.out.println("### demo040PipedInputOutputStream ###");
      //*** PipedInputStream reads bytes from PipedOutputStream ***
      //*** PipedOutputStream writes bytes to PipedInputStream ***
      /*
      PipedOutputStream extends OutputStream
         constructor()
         constructor(PipedInputStream destination)
         .connect(PipedInputStream destination)
         .write(byte)
         .write(byte[])
      PipedInputStream extends InputStream
         constructor()
         constructor(int pipeSize)
         constructor(PipedOutputStream source)
         constructor(PipedOutputStream source, int pipeSize)
         .connect(PipedOutputStream source)
         .read() -> int (byte)
         .readNBytes(int len) -> byte[]
         .available() -> int
      */
      try (PipedOutputStream po = new PipedOutputStream();
         PipedInputStream pi = new PipedInputStream(po, 1024)) {

         // write to PipedOutputStream
         po.write(50);
         po.write(new byte[] {72,72,71,71,70,69,68,67,67,67});

         // read from PipedInputStream
         int len = pi.available();
         System.out.println("  There are " + len + " bytes available.");
         System.out.println("  read " + len + " bytes: "
            + Arrays.toString(pi.readNBytes(len)));

         // write to PipedOutputStream again
         po.write(new byte[] {-1,-1,-1,0,0,0,1,2,3,4,4,4});
         len = pi.available();
         if (len > 0) {
            // read from PipedInputStream again
            System.out.println("  Try reading it again.");
            System.out.println("  read "
               + Arrays.toString(pi.readNBytes(len)));
         }

         System.out.println("  Done Writing to and Reading from Pipe");
      } catch (IOException e) {
         System.out.println("  Exception: PipedInputOutputStream");
         e.printStackTrace();
      }
   }
   public static void demo051BufferedOutputStream() {
      System.out.println("### demo051BufferedOutputStream ###");
      //*** BufferedOutputStream buffers bytes for OutputStream ***
      /*
      BufferedOutputStream extends FilterOutputStream extends OutputStream
         constructor(OutputStream destination)
         constructor(OutputStream destination, int bufferSize)
         .write(byte)
         .write(byte[])
         .flush()
      */
      String filename = PATH + "JavaIO-fileBuffered.dat";
      try (FileOutputStream fo = new FileOutputStream(filename);
         BufferedOutputStream bo = new BufferedOutputStream(fo,1024)) {
         // write bytes into the buffered output stream
         System.out.println("  Writing bytes to BufferedOutputStream");
         System.out.println("    which buffers bytes to FileOutputStream");
         System.out.println("      to file: " + filename);

         bo.write(69);
         bo.write(new byte[] {69, 70, 71, 72, 72});
         bo.flush(); // force everything to go into the actual destination (file)
         bo.write(32);
         System.out.println("  Done writing to the output stream.");
      } catch (IOException e) {
         System.out.println("  Exception: BufferedOutputStream");
         e.printStackTrace();
      }

      // re-open the file for appending
      System.out.println("  Re-open the file for appending.");
      try (FileOutputStream fo = new FileOutputStream(filename, true);
         BufferedOutputStream bo = new BufferedOutputStream(fo,1024)) {
         // true = append at the end of the file
         bo.write(new byte[] {106,104,103,102,101,100});
         bo.write(new byte[] {35,36,37,38,60,61,62,63,64,40,41,42,43});
         System.out.println("  Done appending to the file buffered.");
      } catch (IOException e) {
         System.out.println("  Exception: BufferedOutputStream");
         e.printStackTrace();
      }
   }
   public static void demo052BufferedInputStream() {
      System.out.println("### demo052BufferedInputStream ###");
      //*** BufferedInputStream buffers bytes for InputStream ***
      /*
      BufferedInputStream extends FilterInputStream extends InputStream
         constructor(InputStream source)
         constructor(InputStream source, int bufferSize)
         .read() -> int (byte)
         .readNBytes(int len) -> byte[]
         .available() -> int
         .mark(int limit)
         .reset()
      */
      String filename = PATH + "JavaIO-fileBuffered.dat";
      try (FileInputStream fi = new FileInputStream(filename);
         BufferedInputStream bi = new BufferedInputStream(fi,1024)) {
         // read bytes from the buffered input stream
         System.out.println("  Reading bytes from BufferedInputStream");
         System.out.println("    which buffers bytes from FileInputStream");
         System.out.println("      from file: " + filename);
         System.out.println("  There are " + bi.available() + " bytes available.");
         int len = 4;
         int limit = 10;
         System.out.println("  Read from the file " + len + " bytes at a time.");
         byte[] ba;
         for (int i = 0; i < 3; i++) {
            ba = bi.readNBytes(len);
            if (ba.length>0)
               System.out.println("    out: " + Arrays.toString(ba));
         }
         if (bi.markSupported()) {
            System.out.println("  BufferedInputSream supports mark/set.");
            System.out.println("  mark current position for coming back");
            System.out.println("    mark limit: " + limit + " bytes");
            bi.mark(limit); // just reserve enough space to roll back
         }
         limit -= len;
         do {
            ba = bi.readNBytes(len);
            if (ba.length>0)
               System.out.println("    out: " + Arrays.toString(ba));
            limit -= len;
         } while (ba.length == len && limit > 0); // not read beyond mark limit
         if (bi.markSupported()) {
            bi.reset();
            System.out.println("  reset to go back to the position marked");
            System.out.println("  read again; starting from the mark");
         }
         do {
            ba = bi.readNBytes(len);
            if (ba.length>0)
               System.out.println("    out: " + Arrays.toString(ba));
         } while (ba.length == len);
         if (bi.markSupported()) {
            bi.reset();
            System.out.println("  reset again to go back to the mark; and read again");
         }
         do {
            ba = bi.readNBytes(len);
            if (ba.length>0)
               System.out.println("    out: " + Arrays.toString(ba));
         } while (ba.length == len);
         System.out.println("  Done reading");
      } catch (IOException e) {
         System.out.println("Exception: BufferedInputStream");
         e.printStackTrace();
      }
   }
   public static void demo060DataInputOutputStream() {
      System.out.println("### demo060DataInputOutputStream ###");
      //*** DataOutputStream writes primitives/String to byte-based OutputStream ***
      //*** DataInputStream reads primitives/String from byte-based InputStream ***
      /*
      DataOutputStream extends FilterOutputStream extends OutputStream
         constructor(OutputStream destination)
         .write(byte)
         .write(byte[])
         .flush()
         .writeBoolean(boolean), ..., .writeDouble(double)
         .writeUTF(String)
      DataInputStream extends FilterInputStream extends InputStream
         constructor(InputStream source)
         .read() -> int (byte)
         .readNBytes(int len) -> byte[]
         .readBoolean(), ..., .readDouble() -> boolean, ..., double
         .readUTF() -> String
      */
      enum Type {BOOLEAN,BYTE,SHORT,INT,LONG,FLOAT,DOUBLE,UTF,BYTEARRAY}
      final Type[] TYPES = Type.values();

      // writing primitives/String out to DataOutputStream
      String filename = PATH + "JavaIO-primitives.dat";
      try (FileOutputStream fo = new FileOutputStream(filename);
         BufferedOutputStream bo = new BufferedOutputStream(fo,1024);
         DataOutputStream ds = new DataOutputStream(bo)) {
         // write primitives/Strings to the data output stream
         System.out.println("  Writing primitives/Strings to DataOutputStream");
         System.out.println("    output to file: " + filename);
         // to know what type of primitives are written to the output stream,
         // this demo will write a type indicator out before the actual value.

         ds.write(Type.BOOLEAN.ordinal());
         ds.writeBoolean(true);

         ds.write(Type.UTF.ordinal());
         ds.writeUTF("This is a string.");

         byte[] bytes = {69, 69, 70, 71, 72, 72};
         ds.write(Type.BYTEARRAY.ordinal());
         ds.write(bytes.length);
         ds.write(bytes);

         ds.write(Type.DOUBLE.ordinal());
         ds.writeDouble(12_345.6789);

         ds.write(Type.LONG.ordinal());
         ds.writeLong(1_234_567_890_123L);

         System.out.println("  Done writing to the output stream.");
      } catch (IOException e) {
         System.out.println("  Exception: DataOutputStream");
         e.printStackTrace();
      }

      // reading primitives/String from DataOutputStream
      try (FileInputStream fi = new FileInputStream(filename);
         BufferedInputStream bi = new BufferedInputStream(fi,1024);
         DataInputStream di = new DataInputStream(bi)) {

         // read primitives/Strings from the data output stream
         System.out.println("  Reading primitives/Strings from DataInputStream");

         // to know what type of primitives to read from the input stream,
         // this demo will read the type indicator before the actual value.

         int i;
         while ((i = di.read()) != -1) {
            switch (TYPES[i]) {
               case BOOLEAN -> System.out.println("    boolean: " + di.readBoolean());
               case BYTE ->    System.out.println("    byte: " + di.readByte());
               case SHORT ->   System.out.println("    short: " + di.readShort());
               case INT ->     System.out.println("    int: " + di.readInt());
               case LONG ->    System.out.println("    long: " + di.readLong());
               case FLOAT ->   System.out.println("    float: " + di.readFloat());
               case DOUBLE ->  System.out.println("    double: " + di.readDouble());
               case UTF ->     System.out.println("    UTF-8 String: " + di.readUTF());
               case BYTEARRAY -> System.out.println("    byte[]: "
                     + Arrays.toString(di.readNBytes(di.read())));
            }
         }
         System.out.println("  Done Reading from the input stream.");
      } catch (IOException e) {
         System.out.println("  Exception: DataInputStream");
         e.printStackTrace();
      }
   }
   public static void demo070ObjectInputOutputStream() {
      System.out.println("### demo070ObjectInputOutputStream ###");
      //*** ObjectOutputStream writes objects to byte-based OutputStream ***
      //*** ObjectInputStream reads objects from byte-based InputStream ***
      /*
      ObjectOutputStream extends OutputStream
         constructor(OutputStream destination)
         .write(byte)
         .write(byte[])
         .flush()
         .writeBoolean(boolean), ..., .writeDouble(double)
         .writeUTF(String)
         .writeObject(Object)
      ObjectInputStream extends InputStream
         constructor(InputStream source)
         .read() -> int (byte)
         .readNBytes(int len) -> byte[]
         .readBoolean(), ..., .readDouble() -> boolean, ..., double
         .readUTF() -> String
         .readObject() -> Object
      */
      enum Type {BOOLEAN,BYTE,SHORT,INT,LONG,FLOAT,DOUBLE,UTF,COMPANY}
      final Type[] TYPES = Type.values();
      String filename = PATH + "JavaIO-objects.dat";
      // writing objects to ObjectOutputStream
      try (FileOutputStream fo = new FileOutputStream(filename);
         BufferedOutputStream bo = new BufferedOutputStream(fo,1024);
         ObjectOutputStream oo = new ObjectOutputStream(bo)) {
         // write objects to the object output stream
         System.out.println("  Writing objects to ObjectOutputStream");
         System.out.println("    output to file: " + filename);
         // to know which type of objects are written to the output stream,
         // this demo will write a type indicator out before the actual value.
         oo.write(Type.BOOLEAN.ordinal());
         oo.writeBoolean(true);
         oo.write(Type.UTF.ordinal());
         oo.writeUTF("This is a string.");
         oo.write(Type.DOUBLE.ordinal());
         oo.writeDouble(12_345.6789);
         oo.write(Type.COMPANY.ordinal());
         oo.writeObject(new Company(11,"Eleven"));
         oo.write(Type.COMPANY.ordinal());
         oo.writeObject(new Company(200,"Bicentury"));
         System.out.println("  Done writing to the output stream.");
      } catch (IOException e) {
         System.out.println("  Exception: ObjectOutputStream");
         e.printStackTrace();
      }
      // reading objects from ObjectInputStream
      try (FileInputStream fi = new FileInputStream(filename);
         BufferedInputStream bi = new BufferedInputStream(fi,1024);
         ObjectInputStream oi = new ObjectInputStream(bi)) {
         // read objects from the object output stream
         System.out.println("  Reading objects from ObjectInputStream");
         // to know which type of objects to read from the input stream,
         // this demo will read the type indicator before the actual value.
         int i;
         while ((i = oi.read()) != -1) {
            switch (TYPES[i]) {
               case BOOLEAN -> System.out.println("    boolean: " + oi.readBoolean());
               case BYTE ->    System.out.println("    byte: " + oi.readByte());
               case SHORT ->   System.out.println("    short: " + oi.readShort());
               case INT ->     System.out.println("    int: " + oi.readInt());
               case LONG ->    System.out.println("    long: " + oi.readLong());
               case FLOAT ->   System.out.println("    float: " + oi.readFloat());
               case DOUBLE ->  System.out.println("    double: " + oi.readDouble());
               case UTF ->     System.out.println("    UTF-8 String: " + oi.readUTF());
               case COMPANY ->  {
                  Company c = (Company) oi.readObject();
                  System.out.println("    Company: " + c);
                  System.out.println("      after reconstructing transient fields.");
                  c.forTransient();
                  System.out.println("      Company: " + c);
               }
            }
         }
         System.out.println("  Done Reading from the input stream.");
      } catch (IOException | ClassNotFoundException e) {
         System.out.println("  Exception: ObjectInputStream/ClassNotFound");
         e.printStackTrace();
      }
   }
   public static void demo071CircularObjectInputOutputStream() {
      System.out.println("### demo071CircularObjectInputOutputStream ###");
      String filename = PATH + "JavaIO-circularObjects.dat";
      // writing objects to ObjectOutputStream
      try (FileOutputStream fo = new FileOutputStream(filename);
         BufferedOutputStream bo = new BufferedOutputStream(fo,1024);
         ObjectOutputStream oo = new ObjectOutputStream(bo)) {
         // write objects to the object output stream
         System.out.println("  Writing objects to ObjectOutputStream");
         System.out.println("    output to file: " + filename);
         Person p1 = new Person(1,"Adam Smith");
         Person p2 = new Person(22,"Evalyn Johnson");
         Person p3 = new Person(333,"Emily Brown");
         Person p4 = new Person(4444,"Noah Garcia");
         p1.addFriend(p2);
         p1.addFriend(p3);
         p3.addFriend(p1);
         p4.addFriend(p2);
         oo.writeObject(p1);
         oo.writeObject(p4);
         System.out.println("  Done writing to the output stream.");
      } catch (IOException e) {
         System.out.println("  Exception: ObjectOutputStream");
         e.printStackTrace();
      }
      // reading objects from ObjectInputStream
      try (FileInputStream fi = new FileInputStream(filename);
         BufferedInputStream bi = new BufferedInputStream(fi,1024);
         ObjectInputStream oi = new ObjectInputStream(bi)) {
         // read objects from the object output stream
         System.out.println("  Reading objects from ObjectInputStream");
         List<Person> personList = new LinkedList<>();
         try {
            while (true) {
               Person p = (Person) oi.readObject();
               personList.add(p);
               System.out.println("    Person: " + p);
            }
         } catch (EOFException e) {
            System.out.println("  No more Person to read.");
         } catch (IOException e) {
            System.out.println("  Unexpected content/problem.");
            e.printStackTrace();
         }
         System.out.println("  Done Reading from the input stream.");
         System.out.println("  List all Persons read from the stream directly or indirectly:");
         Set<Person> persons = new HashSet<>();
         while (!personList.isEmpty()) {
            Person person = personList.remove(0);
            if (persons.add(person)) for (var friend : person) personList.add(friend);
         }
         for (var person : persons) {
            person.recomputeTransientFields();
            System.out.println("    " + person);
         }
         System.out.println("  Done listing all Persons.");
      } catch (IOException | ClassNotFoundException e) {
         System.out.println("  Exception: ObjectInputStream/ClassNotFound");
         e.printStackTrace();
      }
   }
   public static void demo080PrintStream() {
      System.out.println("### demo080PrintStream ###");
      //*** PrintStream writes anything in a readable format to an OutputStream/file ***
      //*** System.out is an instance of PrintStream class ***
      /*
      PrintStream extends FilterOutputStream extends OutputStream
         constructor(OutputStream destination)
         constructor(OutputStream destination, boolean autoLineFlush)
         constructor(String filename)
         .write(byte)
         .write(byte[])
         .flush()
         .print(boolean), ..., .print(double), .print(Object)
         .println(), println(boolean), ..., .println(double), .println(Object)
         .printf(String format, Object... args)
         .format(String format, Object... args)
      */
      String filename = PATH + "JavaIO-printstream.dat";

      // writing anything in a readable format to an OutputStream/file
      try (PrintStream ps = new PrintStream(filename)) {
         // write everything
         System.out.println("  Writing everything to PrintStream");
         System.out.println("    output to file: " + filename);
         ps.print(true);
         ps.println();
         ps.println(" .. This is a string. ..");
         ps.println(12_345.6789);
         ps.println(new Asset(7,"Seven"));
         ps.println(new Asset(108,"OneOhEight"));
         ps.printf("boolean: %b%ndouble: %9.2f%nint: %,d%n%s\n\n\n%n",
            false, 98_765.4321, 2567, new Asset(1000,"Millennium"));
         System.out.println("  Done writing to the print stream.");
      } catch (IOException e) {
         System.out.println("  Exception: PrintStream");
         e.printStackTrace();
      }
      // re-open the PrintStream to append text.
      try (FileOutputStream fo = new FileOutputStream(filename, true); // append to file
         BufferedOutputStream bo = new BufferedOutputStream(fo,1024);
         PrintStream ps = new PrintStream(bo)) {
         System.out.println("  Appending to an existing PrintStream.");
         ps.println(".. Appending a string to a re-open existing stream. ..");
         ps.format("[%,10d]%n[%14.3f]%n[%10s]", 1325, 256.25,"string");
         System.out.println("  Done appending to the existing print stream.");
      } catch (IOException e) {
         System.out.println("  Exception: PrintStream appending");
         e.printStackTrace();
      }
   }
   // =============================
   public static void demo111FileWriter() {
      System.out.println("### demo111FileWriter ###");
      //*** FileWriter writes chars to a file. ***
      /*
      OutputStreamWriter extends Writer
       (OutputStreamWriter wraps around OutputStream)
         constructor(OutputStream)
         .write(char)
         .write(char[])
         .write(String)
         .flush()
      FileWriter extends OutputStreamWriter
       (FileWriter wraps around FileOutputStream)
         constructor(String filename)
         constructor(String filename, boolean append)
      */
      String filename = PATH + "JavaIO-textfile.dat";

      try (FileWriter fw = new FileWriter(filename)) {
         System.out.println("  Writing chars to a file:");
         System.out.println("    " + filename);
         // write chars into the file writer
         fw.write('A');
         fw.write(new char[] {'C','C','D','E','F','F'});
         fw.write("\nThis is a string.\n");
         fw.flush(); // force everything to go into the file
         fw.write(new char[] {'x','y','z'});
         System.out.println("  Done writing to the file.");
      } catch (IOException e) {
         System.out.println("  Exception: FileWriter");
         e.printStackTrace();
      }
      // re-open the file for appending text
      try (FileWriter fw = new FileWriter(filename, true)) {
         // true = append at the end of the file
         System.out.println("  Appending chars to the file.");
         fw.write("\n...appending to the file...\n");
         System.out.println("  Done appending to the file.");
      } catch (IOException e) {
         System.out.println("  Exception: FileWriter");
         e.printStackTrace();
      }
   }
   public static void demo112FileReader() {
      System.out.println("### demo112FileReader ###");
      //*** FileReader reads chars from a file. ***
      /*
      InputStreamReader extends Reader
       (InputStreamReader wraps around InputStream)
         constructor(InputStream)
         .read() -> int (char)
         .read(char[]) -> int
         .ready() -> boolean
      FileReader extends InputStreamReader
       (FileReader wraps around FileInoutStream)
         constructor(String filename)
      */
      String filename = PATH + "JavaIO-textfile.dat";

      try (FileReader fr = new FileReader(filename)) {
         System.out.println("  Reading chars from a file:");
         System.out.println("    " + filename);
         System.out.println("  Read from the file one char at a time:");
         int i;
         while ((i = fr.read()) != -1) {
            System.out.print("[" + (char) i + "]");
         }
         System.out.println();
      } catch (IOException e) {
         System.out.println("  Exception: FileReader");
         e.printStackTrace();
      }
      try (FileReader fr = new FileReader(filename)) {
         System.out.println("  Reopen the file.");
         int size = 4;
         int len;
         char[] ba = new char[size];
         System.out.println("  Read from the file " + size + " chars at a time.");
         do {
            len = fr.read(ba);
            if (len>0)
               System.out.println("    out: " + Arrays.toString(ba));
         } while (len == size);
         System.out.println("  Done reading");
      } catch (IOException e) {
         System.out.println("  Exception: FileReader");
         e.printStackTrace();
      }
   }
   public static void demo121CharArrayWriter() {
      System.out.println("### demo121CharArrayWriter ###");
      //*** CharArrayWriter writes chars/String to char[]/String ***
      /*
      CharArrayWriter extends Writer
         constructor()
         .write(char)
         .write(char[])
         .write(String)
         .reset()
         .flush()
         .toCharArray() -> char[]
         .toString() -> String
         .writeTo(Writer)
      */
      CharArrayWriter cw = new CharArrayWriter();
      try (cw) {
         System.out.println("  Writing to CharArrayWriter");
         cw.write('A');
         cw.write(new char[] {'C','C','D','E','F','F'});
         System.out.println("  Viewing the content.");
         // get the content but does not remove it from the stream
         System.out.println("  *** char[]: " + Arrays.toString(cw.toCharArray()));
         System.out.println("  *** String: " + cw.toString());
         // write more into the stream
         cw.write("... This is a string. ...");
         // get the result again which includes the old result too
         System.out.println("  Writing more content:" + cw.toString());

         cw.write("... This is another string. ...");
         cw.flush();
         cw.write("... This is also another string. ...");
         cw.reset(); // this will wipe out everything from the stream
         System.out.println("  Reset the content (wipe out everything)");

         cw.write("... This is the last string. ...");
         System.out.println("  Write new content: " + cw.toString());

         String filename = PATH + "JavaIO-CharArrayWriterToFile.dat";
         try (FileWriter fw = new FileWriter(filename)) {
            //write the content in CharArrayWriter to FileWriter
            System.out.println("  Also save the current content to a file:");
            System.out.println("    " + filename);
            cw.writeTo(fw);
         } catch (IOException e) {
            System.out.println("  Exception: CharArrayWriterToFileWriter");
            e.printStackTrace();
         }
      } catch (IOException e) {
         System.out.println("  Exception: CharArrayWriter");
         e.printStackTrace();
      }
      System.out.println("  Done Writing.");
   }
   public static void demo122CharArrayReader() {
      System.out.println("### demo122CharArrayReader ###");
      //*** CharArrayReader reads chars from char[] ***
      /*
      CharArrayReader extends Reader
         constructor(char[])
         .read() -> int (char)
         .read(char[]) -> int
         .mark(int limit)
         .reset()
         .transferTo(Writer) -> long
      */
      char[] chars = {'A','B','C','D','E','a','b','c','d','e'};
      CharArrayReader cr = new CharArrayReader(chars);
      try (cr) {
         System.out.println("  Reading chars from char[]");
         System.out.println("  Read from the source one char at a time:");
         int i;
         while ((i = cr.read()) != -1) {
            System.out.print(" " + (char) i);
         }
         System.out.println();
         System.out.println("  Done Reading.");
         if (cr.markSupported()) {
            System.out.println("  CharArrayReader supports marking/reseting.");
         }
         System.out.println("  Reset the Reader and read it all over again.");

         // reset to go back to read from the beginning
         cr.reset();
         int size = 4;
         int len;
         System.out.println("  Read from the stream " + size + " chars at a time.");
         char[] ca = new char[size];
         do {
            len = cr.read(ca);
            if (len>0) {
               System.out.print("    out: ");
               for (int j = 0; j < len; j++) System.out.print(ca[j]);
               System.out.println();
            }
         } while (len == size);
         System.out.println("  Done Reading the second time.");
         System.out.println("  Reset the Reader.");

         // reset again to read from the beginning
         cr.reset();
         len = 3;
         System.out.println("  Skip " + len + " chars");
         cr.skip(len);

         // save the remaining content to a file
         String filename = PATH + "JavaIO-CharArrayReaderToFile.dat";
         try (FileWriter fw = new FileWriter(filename)) {
            System.out.println("  Saving the remaining content to a file:");
            System.out.println("    " + filename);
            //transfer the content in CharArrayReader to FileWriter
            cr.transferTo(fw);
         } catch (IOException e) {
            System.out.println("  Exception: CharArrayReaderToFileWriter");
            e.printStackTrace();
         }
      } catch (IOException e) {
         System.out.println("  Exception: CharArrayReader");
         e.printStackTrace();
      }
   }
   public static void demo131StringWriter() {
      System.out.println("### demo131StringWriter ###");
      //*** StringWriter writes chars/String to String ***
      /*
      StringWriter extends Writer
         constructor()
         .write(char)
         .write(char[])
         .write(String)
         .reset()
         .flush()
         .toString() -> String
         .writeTo(Writer)
      */
      StringWriter sw = new StringWriter();
      try (sw) {
         System.out.println("  Writing to StringWriter");
         sw.write('A');
         sw.write(new char[] {'C','C','D','E','F','F'});
         sw.write("... This is a string. ...");
         System.out.println("  Viewing the content.");
         // get the content but does not remove it from the stream
         System.out.println("  String: " + sw.toString());
         // write more into the stream
         sw.write("... This is another string. ...");
         // get the result again which includes the old result too
         System.out.println("  Writing more content:" + sw.toString());
      } catch (IOException e) {
         System.out.println("  Exception: StringWriter");
         e.printStackTrace();
      }
      System.out.println("  Done Writing.");
   }
   public static void demo132StringReader() {
      System.out.println("### demo132StringReader ###");
      //*** StringReader reads chars from String ***
      /*
      StringReader extends Reader
         constructor(String)
         .read() -> int (char)
         .read(char[]) -> int
         .mark(int limit)
         .reset()
         .transferTo(Writer) -> long
      */
      String s =
         "This is a sentence. This is another sentence.\nThis is on another line.";
      StringReader sr = new StringReader(s);
      try (sr) {
         System.out.println("  Reading chars from String");
         System.out.println("  Read from the source one char at a time:");
         int i;
         System.out.print(" [");
         while ((i = sr.read()) != -1) {
            System.out.print((char) i);
         }
         System.out.println("]");
         System.out.println("  Done Reading.");
         if (sr.markSupported()) {
            System.out.println("  StringReader supports marking/reseting.");
         }
         System.out.println("  Reset the Reader and read it all over again.");

         // reset to go back to read from the beginning
         sr.reset(); // this will fail if StringReader does not support marking.

         int size = 20;
         int len;
         System.out.println("  Read from the stream " + size + " chars at a time.");
         char[] ca = new char[size];
         do {
            len = sr.read(ca);
            if (len>0) {
               System.out.print("    out: [");
               for (int j = 0; j < len; j++) System.out.print(ca[j]);
               System.out.println("]");
            }
         } while (len == size);
         System.out.println("  Done Reading the second time.");

         // reset again to read from the beginning
         System.out.println("  Reset the Reader.");
         sr.reset();
         len = 3;
         System.out.println("  Skip " + len + " chars");
         sr.skip(len);

         // save the remaining content to a file
         String filename = PATH + "JavaIO-StringReaderToFile.dat";
         try (FileWriter fw = new FileWriter(filename)) {
            System.out.println("  Saving the remaining content to a file:");
            System.out.println("    " + filename);
            //transfer the content in StringReader to FileWriter
            sr.transferTo(fw);
         } catch (IOException e) {
            System.out.println("  Exception: StringReaderToFileWriter");
            e.printStackTrace();
         }
      } catch (IOException e) {
         System.out.println("  Exception: StringReader");
         e.printStackTrace();
      }
   }
   public static void demo140PipedReaderWriter() {
      System.out.println("### demo140PipedReaderWriter ###");
      //*** PipedReader reads bytes from PipedWriter ***
      //*** PipedWriter writes bytes to PipedReader ***
      /*
      PipedWriter extends Writer
         constructor()
         constructor(PipedReader destination)
         .connect(PipedReader destination)
         .write(char)
         .write(char[])
         .write(String)
      PipedReader extends Reader
         constructor()
         constructor(int pipeSize)
         constructor(PipedWriter source)
         constructor(PipedWriter source, int pipeSize)
         .connect(PipedWriter source)
         .read() -> int (char)
         .read(char[]) -> int
      */
      try (PipedWriter pw = new PipedWriter();
         PipedReader pr = new PipedReader(pw, 1024)) {

         // write to PipedWriter
         System.out.println("  Write to PipedWriter the first time.");
         pw.write('a');
         pw.write(new char[] {'c','c','d','e','f','g','h','i','i'});
         pw.write(".ThisIsAString.");

         // read from PipedReader
         int size = 8;
         int len;
         System.out.println("  Read from PipedReader");
         char[] ca = new char[size];
         do {
            len = pr.read(ca);
            if (len>0) {
               System.out.print("    out: [");
               for (int j = 0; j < len; j++) System.out.print(ca[j]);
               System.out.println("]");
            }
         } while (len == size);
         System.out.println("  Done reading the first time.");

         // write to PipedWriter again
         System.out.println("  Write to PipedWriter the second time.");
         pw.write(new char[] {'A','A','B','C','D','E','E'});
         pw.write('G');
         pw.write(".ThisIsAnotherString.");

         // read from PipedReader again
         System.out.println("  Read from PipedReader the second time.");
         do {
            len = pr.read(ca);
            if (len>0) {
               System.out.print("    out: [");
               for (int j = 0; j < len; j++) System.out.print(ca[j]);
               System.out.println("]");
            }
         } while (len == size);
         System.out.println("  Done reading the second time.");

         System.out.println("  Done writing to and reading from the pipe");
      } catch (IOException e) {
         System.out.println("  Exception: PipedReaderWriter");
         e.printStackTrace();
      }
   }
   public static void demo151BufferedWriter() {
      System.out.println("### demo151BufferedWriter ###");
      //*** BufferedWriter buffers chars for Writer ***
      /*
      BufferedWriter extends Writer
         constructor(Writer destination)
         constructor(Writer destination, int bufferSize)
         .write(char)
         .write(char[])
         .write(String)
         .newLine()
         .flush()
      */
      String filename = PATH + "JavaIO-textfileBuffered.dat";
      try (FileWriter fw = new FileWriter(filename);
         BufferedWriter bw = new BufferedWriter(fw,1024)) {
         // write chars/String into the buffered writer
         System.out.println("  Writing chars/String to BufferedWriter");
         System.out.println("    which buffers chars to FileWriter");
         System.out.println("      to file: " + filename);

         bw.write('A');
         bw.newLine();
         bw.write(new char[] {'C','C','D','E','F','F'});
         bw.newLine();
         bw.write("This is a string.");
         bw.newLine();
         bw.flush(); // force everything to go into the file
         bw.write(new char[] {'x','y','z'});
         System.out.println("  Done writing to the file.");
      } catch (IOException e) {
         System.out.println("  Exception: BufferedWriter");
         e.printStackTrace();
      }
      // re-open the file for appending text
      try (FileWriter fw = new FileWriter(filename, true);
         BufferedWriter bw = new BufferedWriter(fw,1024)) {
         // true = append at the end of the file
         System.out.println("  Appending chars to the buffer.");
         bw.newLine();
         bw.write("...appending to the file...");
         bw.newLine();
         System.out.println("  Done appending to the buffer.");
      } catch (IOException e) {
         System.out.println("  Exception: BufferedWriter");
         e.printStackTrace();
      }
   }
   public static void demo152BufferedReader() {
      System.out.println("### demo152BufferedReader ###");
      //*** BufferedReader buffers chars for Reader ***
      /*
      BufferedReader extends Reader
         constructor(Reader source)
         constructor(Reader source, int bufferSize)
         .read() -> int (char)
         .read(char[]) -> int
         .readLine() -> String
         .lines() -> Stream<String>
         .ready() -> boolean
         .mark(int limit)
         .reset()
      */
      String filename = PATH + "JavaIO-textfileBuffered.dat";

      try (FileReader fr = new FileReader(filename);
         BufferedReader br = new BufferedReader(fr, 1024)) {
         System.out.println("  Reading chars from the buffer");
         System.out.println("    which buffers a file: " + filename);
         System.out.println("  Read from the buffer one char at a time:");
         System.out.print("    ");
         int i;
         while ((i = br.read()) != -1) {
            char c = (char) i;
            c = (c == '\n' || c == '\r') ? '*' : c;
            System.out.print("[" + c + "]");
         }
         System.out.println();
      } catch (IOException e) {
         System.out.println("  Exception: BufferedReader");
         e.printStackTrace();
      }
      try (FileReader fr = new FileReader(filename);
         BufferedReader br = new BufferedReader(fr)) {
         System.out.println("  Re-open the file and the buffer.");
         System.out.println("  Read from buffer one line at a time.");
         String s;
         while ((s = br.readLine()) != null) {
            System.out.println("    line: [" + s + "]");
         }
         System.out.println("  Done reading");
      } catch (IOException e) {
         System.out.println("  Exception: BufferedReader");
         e.printStackTrace();
      }
   }
   public static void demo161FilterWriter() {
      System.out.println("### demo161FilterWriter ###");
      //*** ShiftWriter (extends FilterWriter) shift chars for Writer ***
      /*
      ShiftWriter extends FilterWriter extends Writer
         constructor(Writer destination, int shift)
         .write(char)
         .write(char[])
         .write(String)
      */
      String filename = PATH + "JavaIO-shiftTextfile.dat";
      try (FileWriter fw = new FileWriter(filename);
         BufferedWriter bw = new BufferedWriter(fw, 1024);
         ShiftWriter sw = new ShiftWriter(bw, 1)) {
         // write chars/String into the shift writer
         System.out.println("  Writing chars/String to ShiftWriter");
         System.out.println("    which shifts chars to a file: ");
         System.out.println("      " + filename);

         sw.write('A');
         sw.write(new char[] {'C','C','D','E','F','F','\n'});
         sw.write("This is a string.\n");
         sw.flush(); // force everything to go into the file
         bw.write(new char[] {'x','y','z'});
         System.out.println("  Done writing to the file.");
      } catch (IOException e) {
         System.out.println("  Exception: ShiftWriter");
         e.printStackTrace();
      }
   }
   public static void demo162FilterReader() {
      System.out.println("### demo162FilterWriter ###");
      //*** ShiftReader (extends FilterReader) shift chars for Reader ***
      /*
      ShiftReader extends FilterReader extends Reader
         constructor(Reader source, int shift)
         .read() -> int (char)
         .read(char[]) -> int
      */
      String filename = PATH + "JavaIO-shiftTextfile.dat";

      // open the file and read one char at a time
      try (FileReader fr = new FileReader(filename);
         BufferedReader br = new BufferedReader(fr, 1024);
         ShiftReader sr = new ShiftReader(br, 1)) {
         System.out.println("  Reading chars from ShiftReader");
         System.out.println("    which shift chars from a file:");
         System.out.println("      " + filename);
         System.out.println("  Read one char at a time:");
         System.out.print("    ");
         int i;
         while ((i = sr.read()) != -1) {
            char c = (char) i;
            c = (c == '\n' || c == '\r') ? '*' : c;
            System.out.print("[" + c + "]");
         }
         System.out.println();
      } catch (IOException e) {
         System.out.println("  Exception: FilterReader");
         e.printStackTrace();
      }

      // re-open the file and read multiple chars at a time
      try (FileReader fr = new FileReader(filename);
         BufferedReader br = new BufferedReader(fr);
         ShiftReader sr = new ShiftReader(br, 1)) {
         System.out.println("  Re-open the file.");
         int size = 8;
         int len;
         char[] ba = new char[size];
         System.out.println("  Read from the file " + size + " chars at a time.");
         do {
            len = sr.read(ba);
            if (len>0)
               System.out.println("    out: " + Arrays.toString(ba));
         } while (len == size);
         System.out.println("  Done reading");
      } catch (IOException e) {
         System.out.println("  Exception: FilterReader");
         e.printStackTrace();
      }
   }
   public static void demo170PrintWriter() {
      System.out.println("### demo170PrintWriter ###");
      //*** PrintWriter writes anything in a readable format to a Writer/file ***
      /*
      PrintWriter extends Writer
         constructor(Writer destination)
         constructor(Writer destination, boolean autoLineFlush)
         constructor(String filename)
         .write(char)
         .write(char[])
         .flush()
         .print(boolean), ..., .print(double), .print(Object)
         .println(), println(boolean), ..., .println(double), .println(Object)
         .printf(String format, Object... args)
         .format(String format, Object... args)
      */
      String filename = PATH + "JavaIO-printwriter.dat";

      // writing anything in a readable format to a Writer/file
      try (PrintWriter pw = new PrintWriter(filename)) {
         // write everything
         System.out.println("  Writing everything to PrintWriter");
         System.out.println("    output to file: " + filename);
         pw.print(true);
         pw.println();
         pw.println(" .. This is a string. ..");
         pw.println(12_345.6789);
         pw.println(new Asset(7,"Seven"));
         pw.println(new Asset(108,"OneOhEight"));
         pw.printf("boolean: %b%ndouble: %9.2f%nint: %,d%n%s\n\n\n%n",
            false, 98_765.4321, 2567, new Asset(1000,"Millennium"));
         System.out.println("  Done writing to the print writer.");
      } catch (IOException e) {
         System.out.println("  Exception: PrintWriter");
         e.printStackTrace();
      }
      // re-open the PrintWriter to append text.
      try (FileWriter fw = new FileWriter(filename, true); // append to file
         BufferedWriter bw = new BufferedWriter(fw, 1024);
         PrintWriter pw = new PrintWriter(bw)) {
         System.out.println("  Appending to an existing PrintWriter.");
         pw.println(".. Appending a string to a re-open existing writer. ..");
         pw.format("[%,10d]%n[%14.3f]%n[%10s]", 1325, 256.25,"string");
         System.out.println("  Done appending to the existing print writer.");
      } catch (IOException e) {
         System.out.println("  Exception: PrintWriter appending");
         e.printStackTrace();
      }
   }
}
