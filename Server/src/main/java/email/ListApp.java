package email;

import java.io.*;
import java.util.*;

public class ListApp {
    private static ListApp instance = new ListApp();
    public static ListApp getInstance() {
        return instance;
    }
    private static String[] programFilesPaths = { "C:\\Program Files", "C:\\Program Files (x86)" };

    public static void main(String[] args) {
        ListApp.getInstance().listFoldersAndExes("ListApp.txt");
    }

    public static void listFoldersAndExes(String outputPath) {
        Map<String, List<String>> folderExeMap = new TreeMap<>();

        for (String programFilesPath : programFilesPaths) {
            File programFilesFolder = new File(programFilesPath);
            if (programFilesFolder.exists() && programFilesFolder.isDirectory()) {
                findExesInFolders(programFilesFolder, folderExeMap);
            }
        }

        writeFoldersAndExes(outputPath, folderExeMap);
    }

    public static void findExesInFolders(File directory, Map<String, List<String>> folderExeMap) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    List<String> exePaths = new ArrayList<>();
                    findExes(file, exePaths);
                    if (!exePaths.isEmpty()) {
                        folderExeMap.put(file.getName(), exePaths);
                    }
                }
            }
        }
    }

    public static void findExes(File directory, List<String> exePaths) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".exe")) {
                    exePaths.add(file.getAbsolutePath());
                }
            }
        }
    }

    public static void writeFoldersAndExes(String outputPath, Map<String, List<String>> folderExeMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (Map.Entry<String, List<String>> entry : folderExeMap.entrySet()) {
                writer.write("App Name: " + entry.getKey());
                writer.newLine();
                writer.write("Executable Paths:");
                writer.newLine();
                for (String exePath : entry.getValue()) {
                    writer.write(exePath);
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




