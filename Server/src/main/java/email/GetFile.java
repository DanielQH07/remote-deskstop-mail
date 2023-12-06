package email;

import javax.mail.MessagingException;
import java.io.IOException;

public class GetFile {

    private static GetFile instance = new GetFile();
    public static GetFile getInstance() {
        return instance;
    }
    
    public static void main(String[] args) throws IOException, MessagingException {
        String Filename = "log.txt";
        SendMail obj = new SendMail();
        obj.Send(null,null,Filename);
    }
}
