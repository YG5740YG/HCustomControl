package newhome.baselibrary.MyHttpOld.JSON;

import android.app.Activity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class MyHttpPostFileOther extends Activity {
    private static int readTimeOut = 10 * 1000;
    private static int connectTimeout = 10 * 1000;
    private static final String CHARSET = "utf-8";
    private static final String CONTENT_TYPE = "multipart/form-data";
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static String newName ="image";
    private static final String BOUNDARY = UUID.randomUUID().toString();
    public static String toUploadFile(byte[] file, String RequestURL,
                                      Map<String, String> param) {
        try {
            URL url =new URL(RequestURL);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setReadTimeout(readTimeOut);
            con.setConnectTimeout(connectTimeout);
          /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);// 允许输入流
            con.setDoOutput(true); // 允许输出流
            con.setUseCaches(false);// 不允许使用缓存
          /* 设置传送的method=POST */
            con.setRequestMethod("POST");
          /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", CHARSET); // 设置编码
            con.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
//            con.setRequestProperty("Cookie", "PHPSESSID="
//                    + HttpDetailUtil.PHPSESSID);
            con.setRequestProperty("user-agent", "Mozilla/4.0 ("
                    + "compatible; MSIE 6.0; Windows NT 5.1; SV1)");
          /* 设置DataOutputStream */
            DataOutputStream dos =
                    new DataOutputStream(con.getOutputStream());
            StringBuffer sb = null;

            if (param != null && param.size() > 0) {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext()) {
                    sb = null;
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get(key);
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"")
                            .append(key).append("\"").append(LINE_END)
                            .append(LINE_END);
                    sb.append(value).append(LINE_END);
                    dos.write(sb.toString().getBytes());
                }
            }
            sb = null;
            sb = new StringBuffer();
            sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
            sb.append("Content-Disposition:form-data; name=\"image\"; "
//                    + "filename=\"" + HttpDetailUtil.UserDetail.nickname + "_"
                    + "filename=\"" + newName + "_"
                    + System.currentTimeMillis() + ".png\"" + LINE_END);
            sb.append("Content-Type: application/octet-stream" + LINE_END);
            sb.append(LINE_END);
            dos.write(sb.toString().getBytes());
//			byte[] bytes = new byte[1024];
//			int length = file.length;
//			int len = length / 1024;
//			for (int i = 0; i < len; i++) {
//				System.arraycopy(file, i * 1024, bytes, 0, 1024);
//				dos.write(bytes);
//			}
//			if (length % 1024 != 0) {
//				dos.write(bytes, 0, length % 1024);
//			}
            dos.write(file);
            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                    .getBytes();
            dos.write(end_data);
            dos.flush();

            int res = con.getResponseCode();
            if (res == 200) {
                InputStream input = con.getInputStream();
                sb = new StringBuffer();
                int ss = -1;
                while ((ss = input.read()) != -1) {
                    sb.append((char) ss);
                }
                return sb.toString();
            } else {
                return "";
            }
        } catch (MalformedURLException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }
}
