package email;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test{
    public static int getFile( String path) {
        try {
            Path file = Path.of(path);

            if (!Files.exists(file)) {
                SendMail.getInstance().Send("res/File doesn't exist, try listing the directory!", null, "");
            } else {
                if (Files.isRegularFile(file)) {
                    SendMail.getInstance().Send("res/", null, path);
                } else {
                    SendMail.getInstance().Send("res/You requested a directory type", null, "");
                }
            }
            return 1;
        } catch (IOException | MessagingException e) {
            System.out.println("can not get file");
            e.printStackTrace();
            return 0;
        }
    }
    public static void main(String args[]){
        test get = new test();;
        get.getFile("C:\\Users\\ASUS\\Downloads\\N1.txt");

    }
}




