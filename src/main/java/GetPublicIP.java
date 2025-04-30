import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetPublicIP {
    public static void main(String[] args) {
        try {
            // URL to fetch the public IP
            URL url = new URL("https://api.ipify.org");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response
            Scanner scanner = new Scanner(connection.getInputStream());
            String publicIP = scanner.nextLine();
            scanner.close();

            System.out.println("Your public IP address is: " + publicIP);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
