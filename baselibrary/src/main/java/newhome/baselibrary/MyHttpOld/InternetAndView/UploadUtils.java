package newhome.baselibrary.MyHttpOld.InternetAndView;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 *
 * 实现文件上传的工具类
 */
public class UploadUtils {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    public static final String SUCCESS="1";
    public static final String FAILURE="0";
    /**
     * android上传文件到服务器
     * @param file  需要上传的文件
     * @param RequestURL  请求的rul
     * @return  返回响应的内容
     */
    public static String uploadFile(File file, String RequestURL)
    {
        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if(file!=null)
            {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam=conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:"+res);
                if(res==200)
                {
                    return SUCCESS;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FAILURE;
    }
}

//服务器端代码
///**
// * 文件上传的Serlvet类
// *
// */
//public class FileImageUpload extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//    private ServletFileUpload upload;
//    private final long MAXSize = 4194304*2L;//4*2MB
//    private String filedir;
//
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public FileImageUpload() {
//        super();
//        // TODO Auto-generated constructor stub
//    }

//    /**
//     * 设置文件上传的初始化信息
//     * @see Servlet#init(ServletConfig)
//     */
//    public void init(ServletConfig config) throws ServletException {
//        // Create a factory for disk-based file items
//        FileItemFactory factory = new DiskFileItemFactory();
//        // Create a new file upload handler
//        this.upload = new ServletFileUpload(factory);
//        // Set overall request size constraint 4194304
//        this.upload.setSizeMax(this.MAXSize);
////      File file = new File(pathname);
//        filedir=config.getServletContext().getRealPath("images");
//        File file = new File(filedir);
//        if (!file.exists()) {
//            //创建临时目录
//            file.mkdir();
//        }
//    }
//
//
//    @SuppressWarnings("unchecked")
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // TODO Auto-generated method stub
//        PrintWriter out=response.getWriter();
//        try {
//            List<FileItem> items = this.upload.parseRequest(request);
//            if(items!=null  && !items.isEmpty()){
//                for (FileItem fileItem : items) {
//
//                    System.out.println(fileItem);
//
//                    String filename=fileItem.getName();
//                    String filepath=filedir+File.separator+filename;
//
//                    System.out.println("文件保存路径为:"+filepath);
//
//                    File file=new File(filepath);
//                    InputStream inputSteam=fileItem.getInputStream();
//                    BufferedInputStream fis=new BufferedInputStream(inputSteam);
//                    FileOutputStream fos=new FileOutputStream(file);
//                    int f;
//                    while((f=fis.read())!=-1)
//                    {
//                        fos.write(f);
//                    }
//                    fos.flush();
//                    fos.close();
//                    fis.close();
//                    inputSteam.close();
//                    System.out.println("文件："+filename+"上传成功!");
//                }
//            }
//            System.out.println("上传文件成功!");
//            out.write("上传文件成功!");
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//            out.write("上传文件失败:"+e.getMessage());
//        }
//    }
//}