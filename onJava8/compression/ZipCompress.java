import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * 两种校验和类型： Adler32 更快  CRC32 更慢但更准确
 * <p>
 * Java的Zip库不支持设置密码
 */
public class ZipCompress {

    public static void main(String[] args) {
        try (
                FileOutputStream f = new FileOutputStream("test.zip");
                CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
                ZipOutputStream zos = new ZipOutputStream(csum);
                BufferedOutputStream out = new BufferedOutputStream(zos)
        ) {
            zos.setComment("A test of Java Zipping");
            for (String arg : args) {
                System.out.println("Writing file " + arg);
                try (InputStream in = new BufferedInputStream(new FileInputStream(arg))) {
                    zos.putNextEntry(new ZipEntry(arg));
                    int c;
                    while ((c = in.read()) != -1) {
                        out.write(c);
                    }
                }
                out.flush();
            }
            System.out.println("CheckSum: " + csum.getChecksum().getValue());

//           method 1 extract the files
            System.out.println("Reading file");
            try (
                    FileInputStream fi = new FileInputStream("test.zip");
                    CheckedInputStream csumi = new CheckedInputStream(fi, new Adler32());
                    ZipInputStream in2 = new ZipInputStream(csumi);
                    BufferedInputStream bis = new BufferedInputStream(in2)
            ) {
                ZipEntry ze;
                while ((ze = in2.getNextEntry()) != null) {
                    System.out.println("Reading file " + ze);
                    int x;
                    while ((x = bis.read()) != -1) {
                        System.out.println(x);
                    }
                }
                if (args.length == 1) {
                    System.out.println("Checksum: " + csumi.getChecksum().getValue());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        method 2 extract the files
        try (ZipFile zf = new ZipFile("test.zip")) {
            Enumeration<? extends ZipEntry> e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = e.nextElement();
                System.out.println("File: " + ze2);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}