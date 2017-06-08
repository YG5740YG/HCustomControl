package newhome.baselibrary.MyHttpOld.JSON.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by scorpio on 15/9/24.
 */
public class RequestParams {

    public static final String UTF8 = "UTF-8";

    protected  Map<String, String> mStringParams = null ;

    public  String cookie ;

    public boolean isEnCode = true ;

    public boolean isRefresh = false ;

    protected final ConcurrentHashMap<String, FileWrapper> fileParams = new ConcurrentHashMap<String, FileWrapper>();

    public boolean isEnCode() {
        return isEnCode;
    }

    public void setEnCode(boolean enCode) {
        isEnCode = enCode;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public RequestParams() {
        this.mStringParams = new TreeMap<String, String>();
    }

    public RequestParams(Map<String, String> source) {
        this.mStringParams = new TreeMap<String, String>();
        if(source != null) {
            Iterator var3 = source.entrySet().iterator();
            while(var3.hasNext()) {
                Map.Entry entry = (Map.Entry)var3.next();
                this.put((String)entry.getKey(), (String)entry.getValue());
            }
        }

    }


    /**
     * string 参数
     * @param key
     * @param value
     * @return
     */
    public RequestParams put(String key, String value) {
        mStringParams.put(key, value);
        return this;
    }

    /**
     * int 参数
     * @param key
     * @param value
     * @return
     */
    public RequestParams put(String key ,int value){
        mStringParams.put(key,String.valueOf(value));
        return this;
    }

    public RequestParams put(String key ,long value){
        mStringParams.put(key,String.valueOf(value));
        return this;
    }

    /**
     * 文件
     * @param key
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public RequestParams put(String key, File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (key!=null){
            fileParams.put(key,new FileWrapper(file,null,null));
        }
        return  this ;
    }


    byte[] encodeParameters() {
        StringBuffer encodedParams = new StringBuffer();
        try {
            for (Map.Entry<String, String> entry : mStringParams.entrySet()) {
                if (encodedParams.length() > 0) {
                    encodedParams.append("&");
                }
                try {
                    Thread.sleep(20);
                    encodedParams.append(URLEncoder.encode(entry.getKey(), UTF8));
                    encodedParams.append("=");
                    encodedParams.append(URLEncoder.encode(entry.getValue(),UTF8));
                }catch (Exception e){

                }
            }
            return encodedParams.toString().getBytes(UTF8);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + UTF8, uee);
        }
    }

    @Override
    public String toString(){
        return new String(encodeParameters());
    }

    public static class FileWrapper implements Serializable {
        public final File file;
        public final String contentType;
        public final String customFileName;

        public File getFile() {
            return file;
        }

        public String getContentType() {
            return contentType;
        }

        public String getCustomFileName() {
            return customFileName;
        }

        public FileWrapper(File file, String contentType, String customFileName) {
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }

    }
}
