package email;

import java.io.IOException;

public class Sender {
    public static void main(String[] args) throws IOException{
        String Filename = "C://Users//ASUS//Downloads//1.png";
        SendMail obj = new SendMail();
        obj.Send(Filename);
    }
}
