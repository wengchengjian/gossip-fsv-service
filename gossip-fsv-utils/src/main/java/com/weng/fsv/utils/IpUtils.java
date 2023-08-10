package com.weng.fsv.utils;

import com.alibaba.fastjson2.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author wengchengjian
 * @date 2022/7/1-17:57
 */
// CHECKSTYLE:OFF
@Slf4j
public class IpUtils {

    @SuppressWarnings("checkstyle:StaticVariableName")
    private static final int[][] IP_RANGE = {
            {607649792, 608174079},
            {1038614528, 1039007743},
            {1783627776, 1784676351},
            {2035023872, 2035154943},
            {2078801920, 2079064063},
            {-1950089216, -1948778497},
            {-1425539072, -1425014785},
            {-1236271104, -1235419137},
            {-770113536, -768606209},
            {-569376768, -564133889},
    };

    /**
     * 获取当前网络ip
     */
    public static String getIpAddr() {

        HttpServletRequest request =
                ((ServletRequestAttributes)
                        Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String getRandomIp() {
        Random random = new Random();
        int index = random.nextInt(10);
        String ip = num2ip(IP_RANGE[index][0] + new Random().nextInt(IP_RANGE[index][1] - IP_RANGE[index][0]));
        return ip;
    }

    public static String num2ip(int ip) {
        int[] b = new int[4];
        String ipStr = "";
        b[0] = (ip >> 24) & 0xff;
        b[1] = (ip >> 16) & 0xff;
        b[2] = (ip >> 8) & 0xff;
        b[3] = ip & 0xff;
        ipStr = b[0] + "." + b[1] + "." + b[2] + "." + b[3];

        return ipStr;
    }

    public static String getRealAddress(String ip) {
        String result = "unknown";
        try {
            // 加载地址库
            ClassPathResource resource = new ClassPathResource("GeoLite2-City.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(resource.getInputStream()).build();
            InetAddress inetAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(inetAddress);
            // 获取所在国家
            String country = response.getCountry().getNames().get("ja");
            // 库内获取不到的IP，访问ali的地域查询
            if (response.getSubdivisions().size() > 0) {
                // 获取所在省份
                String province = response.getSubdivisions().get(0).getNames().get("zh-CN");
                // 获取所在城市
                String city = response.getCity().getNames().get("zh-CN");
                city = city == null ? "" : city;
                result = country + province + city;
            } else {
                result = getAlibaba(ip);
            }
        } catch (Exception e) {
            log.error("获取真实ip地址失败,reason:{}", e.getMessage(), e);
        }
        return result;
    }

    /**
     * description ali地域查询
     *
     * @param ip ip地址
     * @return java.lang.String
     * @version 1.0
     */
    public static String getAlibaba(String ip) {
        Map<String, String> map = new HashMap<>();
        map.put("ip", ip);
        map.put("accessKey", "alibaba-inc");
        String result = HttpClientUtil.post("http://ip.taobao.com/outGetIpInfo", map);
        Map valueMap = JSONObject.parseObject(result, Map.class);

        // 请求成功，解析响应数据
        if ("query success".equals(valueMap.get("msg"))) {
            Map<String, String> dataMap = (Map<String, String>) valueMap.get("data");
            String country = dataMap.get("country");
            String region = dataMap.get("region");
            String city = dataMap.get("city");
            return country + region + city;
        }
        return "unknown";
    }

    /**
     * 获取真实IP
     */
    @SuppressWarnings("all")
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        return checkIp(ip)
                ? ip
                : (checkIp(ip = request.getHeader("Proxy-Client-IP"))
                ? ip
                : (checkIp(ip = request.getHeader("WL-Proxy-Client-IP"))
                ? ip
                : request.getRemoteAddr()));
    }

    /**
     * 校验IP
     */
    private static boolean checkIp(String ip) {
        return !StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip);
    }

    /**
     * 获取操作系统,浏览器及浏览器版本信息
     */
    @SuppressWarnings("all")
    public static Map<String, String> getOsAndBrowserInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String user = userAgent.toLowerCase();
        String os;
        String browser = "";

        // =================OS Info=======================
        if (userAgent.toLowerCase().contains("windows")) {
            os = "Windows";
        } else if (userAgent.toLowerCase().contains("mac")) {
            os = "Mac";
        } else if (userAgent.toLowerCase().contains("x11")) {
            os = "Unix";
        } else if (userAgent.toLowerCase().contains("android")) {
            os = "Android";
        } else if (userAgent.toLowerCase().contains("iphone")) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }

        // ===============Browser===========================
        try {
            if (user.contains("edge")) {
                browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
            } else if (user.contains("msie")) {
                String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
                browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
            } else if (user.contains("safari") && user.contains("version")) {
                browser =
                        (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                                + "-"
                                + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr") || user.contains("opera")) {
                if (user.contains("opera")) {
                    browser =
                            (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                                    + "-"
                                    + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
                } else if (user.contains("opr")) {
                    browser =
                            ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                                    .replace("OPR", "Opera");
                }
            } else if (user.contains("chrome")) {
                browser =
                        (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
            } else if ((user.contains("mozilla/7.0"))
                    || (user.contains("netscape6"))
                    || (user.contains("mozilla/4.7"))
                    || (user.contains("mozilla/4.78"))
                    || (user.contains("mozilla/4.08"))
                    || (user.contains("mozilla/3"))) {
                browser = "Netscape-?";
            } else if (user.contains("firefox")) {
                browser =
                        (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
            } else if (user.contains("rv")) {
                String ieVersion =
                        (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
                browser = "IE" + ieVersion.substring(0, ieVersion.length() - 1);
            } else {
                browser = "UnKnown";
            }
        } catch (Exception e) {
            log.error("获取浏览器版本失败");
            log.error(e.getMessage());
            browser = "UnKnown";
        }
        Map<String, String> result = new HashMap<>(2);
        result.put("OS", os);
        result.put("BROWSER", browser);
        return result;
    }

    /**
     * 判断是否是内网IP
     */
    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    // CHECKSTYLE:OFF
    @SuppressWarnings("checkstyle:ReturnCount")
    private static boolean internalIp(byte[] addr) {
        if (addr == null || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                if (b1 == SECTION_6) {
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    // CHECKSTYLE:OFF
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }
        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    /**
     * 获取IP
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    /**
     * 获取主机名
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("获取hosts失败,reason:{}", e.getMessage());
        }
        return "未知";
    }

    public static String getIpAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String address = null;
        List<String> ipAddress = headers.get("x-forwarded-for");
        if (ipAddress == null || ipAddress.size() == 0 || ipAddress.stream().anyMatch((item) -> item.equalsIgnoreCase("unknown"))) {
            ipAddress = headers.get("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.size() == 0 || ipAddress.stream().anyMatch((item) -> item.equalsIgnoreCase("unknown"))) {
            ipAddress = headers.get("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.size() == 0 || ipAddress.stream().anyMatch((item) -> item.equalsIgnoreCase("unknown"))) {
            ipAddress = headers.get("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.size() == 0 || ipAddress.stream().anyMatch((item) -> item.equalsIgnoreCase("unknown"))) {
            ipAddress = headers.get("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress != null && ipAddress.size() > 0) {
            address = ipAddress.get(0);
        }
        if (ipAddress == null || ipAddress.size() == 0 || ipAddress.stream().anyMatch((item) -> item.equalsIgnoreCase("unknown"))) {
            address = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
            if ("127.0.0.1".equals(address) || "0:0:0:0:0:0:0:1".equals(address)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert inet != null;
                address = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  //"***.***.***.***".length() = 15
        if (address != null && address.length() > 15) {
            if (address.indexOf(",") > 0) {
                address = address.substring(0, address.indexOf(","));
            }
        }
        return address;
    }
}
