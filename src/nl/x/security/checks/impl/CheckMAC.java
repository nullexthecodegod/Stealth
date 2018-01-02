package nl.x.security.checks.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import nl.x.security.checks.Check;

/**
 * @author NullEX
 *
 */
public class CheckMAC extends Check {

	/*
	 * 
	 * @see nl.x.security.checks.Check#check()
	 */
	@Override
	public boolean check() {
		return false;
	}

	public static String getMAC() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while (i < mac.length) {
				Object[] arrobject = new Object[2];
				arrobject[0] = Byte.valueOf(mac[i]);
				arrobject[1] = i < mac.length - 1 ? "-" : "";
				sb.append(String.format("%02X%s", arrobject));
				++i;
			}
			System.out.println(sb.toString());
			return sb.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

}
