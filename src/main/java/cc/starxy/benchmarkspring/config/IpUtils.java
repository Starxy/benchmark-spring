

package cc.starxy.benchmarkspring.config;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 *
 * @author
 * @since 1.0.0
 */
public class IpUtils {
	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

	public static String getIpAddr(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StrUtil.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			return "";
			// logger.error("IpUtils ERROR ", e);
		}
		return ip;
	}

	public static String getIpAddr() {
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		return IpUtils.getIpAddr(request);
	}

	/**
	 * p是否在ip区间内
	 * @param ip
	 * @param ipText  11.1111.11-11.1.1.1,111.12.1.1,111.12.2.2-111.12.2.3
	 * @return
	 */
	public static Boolean ipExistsInRange(String ip, String ipText) {
		try {
			if (StrUtil.isEmpty(ipText) || ipText.length() < 7) {
				return false;
			}
			ipText = ipText.trim();
			if (ipText.length() < 7) {
				return false;
			}
			String[] ipSectionArray = ipText.split(",");
			for (String ipSection : ipSectionArray) {
				ipSection = ipSection.trim();
				String[] ip2 = ipSection.split("-");
				String beginIP = ip2[0].trim();
				String endIP = ip2.length > 1 ? ip2[1].trim() : ip2[0].trim();
				if (Ipv4Util.ipv4ToLong(beginIP) <= Ipv4Util.ipv4ToLong(ip) && Ipv4Util.ipv4ToLong(ip) <= Ipv4Util
						.ipv4ToLong(endIP)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	public static long getIp2long(String ip) {
		return Ipv4Util.ipv4ToLong(ip);
	}
}
