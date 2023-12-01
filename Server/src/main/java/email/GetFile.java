package email;

import java.io.IOException;

public class GetFile {

    private static GetFile instance = new GetFile();
    public static GetFile getInstance() {
        return instance;
    }
    
    public static void main(String[] args) throws IOException {
        String Filename = "log.txt";
        SendMail obj = new SendMail();
        obj.Send(Filename);
    }
}
