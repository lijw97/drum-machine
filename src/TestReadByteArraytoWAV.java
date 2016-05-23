import java.io.*;

/**
 * Created by Jeffrey Li on 5/23/2016.
 */
public class TestReadByteArraytoWAV {
    public static void main(String[] args) {
        File srcFile = new File("clap.wav");
        File dstFile = new File("new.wav");
        try {
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(dstFile);
            byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch(IOException e){}
            try {
                in.close();
                out.close();
            } catch(IOException e){}

        } catch(FileNotFoundException e){System.out.println("swag");}


    }
}
