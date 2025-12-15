package net.engineeringdigest.journalapp.getIP;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Slf4j
public class IPAddress {
    public static void main(String[] args) {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            System.out.println("IP of my system is: " + ipAddress.getHostAddress());
            System.out.println("Hostname of my system is: " + ipAddress.getHostName());
        } catch (UnknownHostException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
