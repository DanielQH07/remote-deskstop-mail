package email;

public class test {
    public static void main(String args[]){
        String header = "take/1";
        String[] parts = header.split("/");
        System.out.print(parts[0]);
    }
}
