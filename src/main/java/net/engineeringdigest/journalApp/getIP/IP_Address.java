package net.engineeringdigest.journalApp.getIP;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IP_Address {
    public static void main(String[] args) {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            System.out.println("IP of my system is: " + ipAddress.getHostAddress());
            System.out.println("Hostname of my system is: " + ipAddress.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
