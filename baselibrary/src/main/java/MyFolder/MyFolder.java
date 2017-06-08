package MyFolder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.GetFilesUtils;

/**
 * Created by 20160330 on 2017/3/3.
 */
//获取根目录的文件列表和文件信息
    public class MyFolder extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener {
        private ListView folderLv;
    private TextView foldernowTv;
    private SimpleAdapter sAdapter;
    private List<Map<String, Object>> aList;
    private String baseFile;
    private TextView titleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_folder);
        baseFile= GetFilesUtils.getInstance().getBasePath();//获取默认的路径，有SD获取SD卡的响度路径，没有SD卡获取手机绝对路径
        titleTv=(TextView) findViewById(R.id.textview_title);
        titleTv.setText("本地文件");
        folderLv=(ListView) findViewById(R.id.folder_list);
        foldernowTv=(TextView) findViewById(R.id.folder_now);
        foldernowTv.setText(baseFile);
        foldernowTv.setOnClickListener(this);
        aList=new ArrayList<Map<String,Object>>();
        sAdapter=new SimpleAdapter(this, aList,R.layout.listitem_folder, new String[]{"fImg","fName","fInfo"},
                new int[]{R.id.folder_img,R.id.folder_name,R.id.folder_info});
        folderLv.setAdapter(sAdapter);
        folderLv.setOnItemClickListener(this);
        try {
            loadFolderList(baseFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void loadFolderList(String file) throws IOException{
        List<Map<String, Object>> list=GetFilesUtils.getInstance().getSonNode(file);//获取文件pathStr文件夹下的文件列表
        if(list!=null){
            Collections.sort(list, GetFilesUtils.getInstance().defaultOrder());
            aList.clear();
            for(Map<String, Object> map:list){
                String fileType=(String) map.get(GetFilesUtils.FILE_INFO_TYPE);
                Map<String,Object> gMap=new HashMap<String, Object>();
                if(map.get(GetFilesUtils.FILE_INFO_ISFOLDER).equals(true)){//存在文件夹
                    gMap.put("fIsDir", true);
                    gMap.put("fImg",R.drawable.check_box);
                    gMap.put("fInfo", map.get(GetFilesUtils.FILE_INFO_NUM_SONDIRS)+"个文件夹和"+
                            map.get(GetFilesUtils.FILE_INFO_NUM_SONFILES)+"个文件");//获取该文件夹下文件夹数量和文件数量
                }else{
                    gMap.put("fIsDir", false);//非文件夹
                    if(fileType.equals("txt")||fileType.equals("text")){//如果是txt文件
                        gMap.put("fImg", R.mipmap.chat_bq1);
                    }else{
                        gMap.put("fImg", R.mipmap.cancel31);
                    }
                    gMap.put("fInfo","文件大小:"+GetFilesUtils.getInstance().getFileSize(map.get(GetFilesUtils.FILE_INFO_PATH).toString()));
                    //获取文件大小
                }
                gMap.put("fName", map.get(GetFilesUtils.FILE_INFO_NAME));//文件夹名
                gMap.put("fPath", map.get(GetFilesUtils.FILE_INFO_PATH));//文件夹路径
                aList.add(gMap);
            }
        }else{
            aList.clear();
        }
        sAdapter.notifyDataSetChanged();
        foldernowTv.setText(file);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        try {
            if(aList.get(position).get("fIsDir").equals(true)){
                loadFolderList(aList.get(position).get("fPath").toString());
            }else{
                Toast.makeText(this, "这是文件，处理程序待添加", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.folder_now){
            try {
                String folder=GetFilesUtils.getInstance().getParentPath(foldernowTv.getText().toString());
                if(folder==null){
                    Toast.makeText(this, "无父目录，待处理", Toast.LENGTH_SHORT).show();
                }else{
                    loadFolderList(folder);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
