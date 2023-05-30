package siren.ocean.server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by Siren on 2023/5/29.
 */
public class NetworkUtils {

    public static String getIPAddress() {
        String ipAddress = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address.getHostAddress().indexOf(':') == -1) {
                        ipAddress = address.getHostAddress();
                        break;
                    }
                }
                if (ipAddress != null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}

