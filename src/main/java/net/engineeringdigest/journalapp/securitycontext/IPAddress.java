package net.engineeringdigest.journalapp.securitycontext;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Slf4j
public class IPAddress {
    public static void main(String[] args) {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            log.info("IP of my system is: {} ", ipAddress.getHostAddress());
            log.info("Hostname of my system is: {}", ipAddress.getHostName());
        } catch (UnknownHostException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
