import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CityNameExample {
    public static String getCityName(String ip) {
        String cityName = null;
        try {
            String urlStr = "http://ip.taobao.com/service/getIpInfo.php?ip=" + URLEncoder.encode(ip, "UTF-8");
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));
            String output = br.readLine();
            Pattern pattern = Pattern.compile("\"city\":\"(\\S+?)\"");
            Matcher matcher = pattern.matcher(output);
            if (matcher.find()) {
                cityName = matcher.group(1);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityName;
    }
}
