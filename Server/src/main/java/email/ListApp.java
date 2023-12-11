package email;
import java.io.*;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.util.regex.Pattern;

public class ListApp {

    private static ListApp instance = new ListApp();

    public static ListApp getInstance() {
        return instance;
    }
    private static boolean containsSpecialCharacters(String s) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9 +\\-().]");
        return pattern.matcher(s).find();
    }
    public void list(String filename) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "powershell", "Get-ItemProperty HKLM:\\Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\* | Select-Object DisplayName");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            while ((line = reader.readLine()) != null) {

                if (!containsSpecialCharacters(line) || line.equalsIgnoreCase("")) {
                    line = line.trim(); // Remove leading and trailing whitespaces
                    if (!line.isEmpty()) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
            writer.close();
            reader.close();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("List of installed applications retrieved successfully.");
            } else {
                System.out.println("Failed to retrieve the list of installed applications.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]){
        ListApp.getInstance().list("ListApp.txt");
    }
}
