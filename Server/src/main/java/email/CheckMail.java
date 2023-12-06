package email;

import javax.mail.*;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class CheckMail {

    private String username, password;
    private Store store;
    public int count;
    private static CheckMail instance = new CheckMail();

    public static CheckMail getInstance() {
        return instance;
    }

    private CheckMail() {
        try {
            getMail();
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3.host", "pop.gmail.com");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getInstance(properties);
            this.store = emailSession.getStore("pop3s");
            store.connect("pop.gmail.com", username, password);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMail() {
        try (BufferedReader br = new BufferedReader(new FileReader("mail.txt"))) {
            this.username = br.readLine().trim();
            this.password = br.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean processRequest(String header){
        String[] parts = header.split("/");
        System.out.println(Arrays.toString(parts));
        return true;
    }
    public boolean checkStopLog(){
        final boolean[] flag = new boolean[1];
        new Thread() {
            @Override
            public void run() {
                flag[0] = false;
                while(!flag[0]) {
                    try {
                        Thread.sleep(500);
                        Folder emailFolder = null;
                        emailFolder = store.getFolder("INBOX");
                        emailFolder.open(Folder.READ_WRITE);
                        Message[] ls = emailFolder.getMessages();
//                        boolean first = true;
//                        for (int i = ls.length - 1; i >= 0; i--) {
//                            var m = ls[i];
//                            String subject = m.getSubject();
//                            if (subject.startsWith("req") && first) {
//                                //boolean kq = Main.processRequest(subject);
//                                boolean kq = processRequest(subject);
//                                if (kq) {
//                                    System.out.println("Resolved " + subject);
//                                } else {
//                                    System.out.println("Rejected " + subject);
//                                }
//                                first = false;
//                            }
//                            m.setFlag(Flags.Flag.DELETED, true);
                        if (ls.length != 0) {
                            Message newEmail = ls[0];
                            String subject = newEmail.getSubject();
                            System.out.println("Nothing");
                            String[] parts = subject.split("/");
                            if (parts[2].trim().equalsIgnoreCase("keylog")) {
                                String res = parts[3];
                                if (res.equalsIgnoreCase("End")) {
                                    flag[0] = true;
                                    System.out.println("Hey");
                                }
                                newEmail.setFlag(Flags.Flag.DELETED, true);
                            }
                        }
                        emailFolder.close(true);
                    } catch (InterruptedException e) {
                        System.out.println("Can't connect to mail");
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
                };
        }.start();
        return flag[0];
    }

    public void listen() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        Folder emailFolder = null;
                        emailFolder = store.getFolder("INBOX");
                        emailFolder.open(Folder.READ_WRITE);
                        Message[] ls = emailFolder.getMessages();
//                        boolean first = true;
//                        for (int i = ls.length - 1; i >= 0; i--) {
//                            var m = ls[i];
//                            String subject = m.getSubject();
//                            if (subject.startsWith("req") && first) {
//                                //boolean kq = Main.processRequest(subject);
//                                boolean kq = processRequest(subject);
//                                if (kq) {
//                                    System.out.println("Resolved " + subject);
//                                } else {
//                                    System.out.println("Rejected " + subject);
//                                }
//                                first = false;
//                            }
//                            m.setFlag(Flags.Flag.DELETED, true);
                        if (ls.length != 0)
                        {
                            Message newEmail = ls[0];
                            String subject = newEmail.getSubject();
                            int kq = Main.processRequest(subject);
                            if (kq == 1) {
                                  System.out.println("Resolved " + subject);
                              } else if (kq == 0){
                                    System.out.println("Rejected " + subject);
                              }else if(kq==2){
                                System.out.println("Bye...");
                                break;
                            }
                            newEmail.setFlag(Flags.Flag.DELETED, true);
                        }
                        emailFolder.close(true);
                    } catch (InterruptedException e) {
                        System.out.println("Can't connect to mail");
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }
        public static void main(String[] args) throws Exception {
            CheckMail check = CheckMail.getInstance();
            check.listen();
        }
    }
