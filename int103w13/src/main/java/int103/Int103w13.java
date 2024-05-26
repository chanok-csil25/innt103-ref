package int103;

import java.io.*;
import java.util.*;

public class Int103w13 {

    private static final String PATH = "C:\\Users\\Student\\Downloads\\INT103\\";
    
    public static void main(String[] args) {
        System.out.println("#### Java IO ####");
        //demoFileInputOutputStream();
        //demoByteArrayInputOutputStream();
        //SequenceInputStream
        //PipedInputOutpuStream
        //demoBufferedInputOutputStream();
        //demoDataInputOutputStream(); 
        //demoObjectInputOutputSream();
    }
    public static void demoObjectInputOutputSream() {
        System.out.println("## demoObjectInputOutputSream ##");
        String filename = PATH + "objects.dat";
        Person p1 = new Person(99, "First");
        p1.setLuckNumber(111);
        Person p2 = new Person(888, "Second");
        p2.setLuckNumber(222);
        Person p3 = new Person(7777, "Third");
        p3.setLuckNumber(333);
        p1.addFriend(p3);
        p1.addFriend(p2);
        p2.addFriend(p3);
        p3.addFriend(p1);
        System.out.println("Person1: " + p1.toString());
        System.out.println("Person2: " + p2.toString());
        System.out.println("Person3: " + p3.toString());
        try (FileOutputStream fo = new FileOutputStream(filename);
             BufferedOutputStream bo = new BufferedOutputStream(fo,1000);
             ObjectOutputStream oos = new ObjectOutputStream(bo)) {
            byte[] bytes = {67,68,69,70,70,70};
            oos.write(bytes.length);
            oos.write(bytes);
            oos.writeUTF("This is a string.");
            oos.writeBoolean(true);
            oos.writeDouble(20.99);
            oos.writeObject(p1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileInputStream fi = new FileInputStream(filename);
             BufferedInputStream bi = new BufferedInputStream(fi,1000);
             ObjectInputStream ois = new ObjectInputStream(bi)) {
            byte[] out = ois.readNBytes(ois.read());
            System.out.println("byte[]: " + Arrays.toString(out));
            System.out.println("String: " + ois.readUTF());
            System.out.println("boolean: " + ois.readBoolean());
            System.out.println("double: " + ois.readDouble());
            Person p = (Person) ois.readObject();
            System.out.println("Person1: " + p);
            for (var it = p.getFriends();  it.hasNext(); ) {
                System.out.println("friend: " + it.next());
            }
            System.out.println("Done reading.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void demoDataInputOutputStream() {
        System.out.println("## demoDataInputOutputStream ##");
        String filename = PATH + "primitives.dat";
        try (FileOutputStream fo = new FileOutputStream(filename);
             BufferedOutputStream bo = new BufferedOutputStream(fo,1000);
             DataOutputStream dos = new DataOutputStream(bo)) {
            byte[] bytes = {67,68,69,70,70,70};
            dos.write(bytes.length);
            dos.write(bytes);
            dos.writeUTF("This is a string.");
            dos.writeBoolean(true);
            dos.writeDouble(20.99);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileInputStream fi = new FileInputStream(filename);
             BufferedInputStream bi = new BufferedInputStream(fi,1000);
             DataInputStream di = new DataInputStream(bi)) {
            byte[] out = di.readNBytes(di.read());
            System.out.println("byte[]: " + Arrays.toString(out));
            System.out.println("String: " + di.readUTF());
            System.out.println("boolean: " + di.readBoolean());
            System.out.println("double: " + di.readDouble());
            System.out.println("Done reading.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void demoBufferedInputOutputStream() {
        System.out.println("## demoBufferedInputOutputStream ##");
        String filename = PATH + "buffered.dat";
        try (FileOutputStream fo = new FileOutputStream(filename);
             BufferedOutputStream bo = new BufferedOutputStream(fo,1000)) {
            bo.write((byte) 50);
            bo.write(new byte[] {67,68,69,70,70,70});
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileInputStream fi = new FileInputStream(filename);
             BufferedInputStream bi = new BufferedInputStream(fi,1000)) {
            int len = bi.available();
            System.out.println("available: " + len);
            byte[] out = fi.readNBytes(len);
            System.out.println("Content: " + Arrays.toString(out));
            System.out.println("Done reading.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void demoFileInputOutputStream() {
        System.out.println("## demoFileInputOutputStream ##");
        String filename = PATH + "byte.dat";
        try (FileOutputStream fo = new FileOutputStream(filename)) {
            fo.write((byte) 50);
            fo.write(new byte[] {67,68,69,70,70,70});
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileInputStream fi = new FileInputStream(filename)) {
            int len = fi.available();
            System.out.println("available: " + len);
            byte[] out = fi.readNBytes(len);
            System.out.println("Content: " + Arrays.toString(out));
            System.out.println("Done reading.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileInputStream fi = new FileInputStream(filename)) {
            int b;
            while ((b = fi.read()) != -1) {
                System.out.print(" " + (byte) b);
            }
            System.out.println();
            System.out.println("Done second reading.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void demoByteArrayInputOutputStream() {
        System.out.println("## demoByteArrayInputOutputStream ##");
        String filename = PATH + "byteArray.dat";
        try (ByteArrayOutputStream bo = new ByteArrayOutputStream()) {
            bo.write((byte) 50);
            bo.write((byte) 96);
            byte[] out = bo.toByteArray();
            System.out.println("Writing: " + Arrays.toString(out));
            bo.write((byte) 97);
            bo.reset();
            bo.write(new byte[] {67,68,69,70,70,70});
            out = bo.toByteArray();
            System.out.println("Writing: " + Arrays.toString(out));
            // also write output to file
            try (FileOutputStream fo = new FileOutputStream(filename)) {
                bo.writeTo(fo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Reading from an array of bytes.");
        byte[] input = {70,71,72,73,74,75,76,77,77,77};
        try (ByteArrayInputStream bi = new ByteArrayInputStream(input)) {
            int len = bi.available();
            System.out.println("available: " + len);
            byte[] out = bi.readNBytes(len);
            System.out.println("Content: " + Arrays.toString(out));
            System.out.println("Done reading and try it all over again.");
            bi.reset();
            int b;
            while ((b = bi.read()) != -1) {
                System.out.print(" " + (byte) b);
            }
            System.out.println();
            System.out.println("Done second reading.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
