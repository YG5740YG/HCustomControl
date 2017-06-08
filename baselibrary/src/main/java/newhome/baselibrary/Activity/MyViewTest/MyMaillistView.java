package newhome.baselibrary.Activity.MyViewTest;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import MyView.MyDialog;
import me.nereo.multi_image_selector.bean.Image;
import newhome.baselibrary.Adapter.ListAdapter;
import newhome.baselibrary.Bus.BusAction;
import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Bus.BusProvider;
import newhome.baselibrary.Model.UserBolmData;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baselibrary.Tools.UITools;
import newhome.baseres.view.BaseActivity;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class MyMaillistView extends Activity {
    EditText et_phone;
    ImageView iv_contancts;
    private Dialog dialog;
    private View dialogView;
    private ListAdapter listAdapter;
    Context context;
    /**
     * 一个联系人的号码集合
     */
    private List<String> mUserPhoneList=new ArrayList<>();
    /**
     * 移除+86号码的数组
     */
    private String[] mPhones;
    /**
     * 经过排除后的号码集合(座机排除，+86的直接把+86移除)
     */
    private List<String> mPhoneList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mymaillist);
        findViewById();
        setUp();
    }
    public void findViewById() {
        et_phone=(EditText)findViewById(R.id.et_phone);
        iv_contancts=(ImageView)findViewById(R.id.iv_contancts);
    }

    public void setUp() {
        context=this;
        iv_contancts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(context).request(Manifest.permission.READ_CONTACTS).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent= null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR) {
                                intent = new Intent(Intent.ACTION_PICK,
                                        ContactsContract.Contacts.CONTENT_URI);
                            }
                            startActivityForResult(intent,1);
                            UserBolmData userBolmData=getUserBolm();
                            if(userBolmData!=null){
                                Gson gson=new Gson();
                                String json=gson.toJson(userBolmData);
                                Logs.Debug("gg==12==json=============="+json);
//                                TopicControl topicControl=new TopicControl();
//                                topicControl.postContact(context, json, new DataResponse() {
//                                    @Override
//                                    public void onSucc(Object o) {
//                                        Logs.Debug("gg==12==onSuccess=====12="+o.toString());
//                                    }
//                                    @Override
//                                    public void onFail(String s) {
//                                        Logs.Debug("gg==12====onFailure==="+s.toString());
//                                    }
//                                });
                            }
                        } else {
//                            UITools.msgBox(context, "小九提示：", "尊敬的用户您好，为了更好的为您服务，您需要开通一些必要权限，谢谢。", "确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            });
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取电话薄信息
     */
    public UserBolmData getUserBolm(){
        UserBolmData userBolmDatas=new UserBolmData();
        userBolmDatas.setUserId("123456");
        List<UserBolmData.Tx1Bean> contentBeens=new ArrayList<>();
        UserBolmData.Tx1Bean contentBean;
        Cursor cursor = null;
        try {
// 查询联系人数据
            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                contentBean=new UserBolmData.Tx1Bean();
                List<String>phones=new ArrayList<>();
                boolean userMorePhone=false;
// 获取联系人姓名
                String displayName = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
// 获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                if(contentBeens.size()>0){
                    for (int i=0;i<contentBeens.size();i++){
                        if(contentBeens.get(i).getName().equals(displayName)){
                            userMorePhone=true;
                            for (int j=0;j<contentBeens.get(i).getPhone().size();j++){
                                phones.add(contentBeens.get(i).getPhone().get(j));
                                if(j==contentBeens.get(i).getPhone().size()-1){
                                    phones.add(number);
                                }
                            }
                        }
                        if(userMorePhone){
                            contentBeens.get(i).setPhone(phones);
                            break;
                        }
                    }
                }
                if(!userMorePhone) {
                    contentBean.setName(displayName);
                    phones.add(number);
                    contentBean.setPhone(phones);
                    contentBeens.add(contentBean);
                }
                Logs.Debug("gg=======name=="+displayName+"==number=="+number);
            }
            if(contentBeens.size()>0){
                userBolmDatas.setTx1(contentBeens);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if(userBolmDatas.getTx1().size()>0){
            return userBolmDatas;
        }else{
            return null;
        }
    }
    public void myDialog(final List<String> mPhoneList){
        dialog=new Dialog(context);
        dialogView= LayoutInflater.from(context).inflate(R.layout.popuwindow_maptools,null);
        TextView tsTitle= (TextView) dialogView.findViewById(R.id.ts_title);
        ListView listView= (ListView) dialogView.findViewById(R.id.listView_map);
        tsTitle.setText("请选择电话号码");

        listAdapter=new ListAdapter(context,mPhoneList);
        listView.setAdapter(listAdapter);
        int item=mPhoneList.size();
        item+=1;
        float height= (float) (item*0.1);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                et_phone.setText(mPhoneList.get(i));
                et_phone.setSelection(mPhoneList.get(i).length());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /**
     * 假如有多个电话号码，弹出选择框
     * @param   mPhoneList 号码集合
     */
    private void showTools(final List<String> mPhoneList) {
        myDialog(mPhoneList);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==-1){
            Uri contactData=data.getData();
            Cursor cursor=managedQuery(contactData,null,null,null,null);
            cursor.moveToFirst();
            try {
                List<String> phList=new ArrayList<>();
                phList = this.getContactPhone(cursor);
                mPhoneList.clear();
                for (int i = 0; i < phList.size(); i++) {
                    if (phList.get(i).contains("+86")){
                        mPhones=phList.get(i).split("[+]86");
                        if (Tools.isMobile(mPhones[1])) {
                            mPhoneList.add(mPhones[1]);
                        }
                    }else if (Tools.isMobile(phList.get(i))){
                        mPhoneList.add(phList.get(i));
                    }
                }

                if (mPhoneList.size()>1){
                    showTools(mPhoneList);
                }else if (mPhoneList.size()==1){
                    et_phone.setText(mPhoneList.get(0));
                    et_phone.setSelection(mPhoneList.get(0).length());
                    BusEvent event = new BusEvent();
                    event.setAction(BusAction.PARKINLEFT);
                    event.setObject(mPhoneList.get(0));
                    BusProvider.getInstance().post(event);
                }
            }catch (Exception e){
                UITools.toastShowLong(context, "未能获取到联系人，请允许九机网访问联系人信息。");
            }
        }
    }
    /**
     *  获取号码
     * @param cursor  cursor
     * @return  返回号码集合
     */
    private List<String> getContactPhone(Cursor cursor){
        mUserPhoneList.clear();
        int phoneColumn=cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum=cursor.getInt(phoneColumn);
        String result="";
        if (phoneNum>0){
            //获取联系人ID号
            int idColumn=cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId=cursor.getString(idColumn);

            //获取联系人的电话
            Cursor phone=context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="
                            +contactId,null,null);

            if (phone.moveToFirst()){
                for (; !phone.isAfterLast();phone.moveToNext()){
                    int index=phone.getColumnIndex(ContactsContract.CommonDataKinds
                            .Phone.NUMBER);
                    int typeindex=phone.getColumnIndex(ContactsContract.CommonDataKinds
                            .Phone.TYPE);

                    String phoneNumber=phone.getString(index);
                    result=phoneNumber;
                    mUserPhoneList.add(result.replace(" ","").replace("-",""));
                }
                if (!phone.isClosed()){
                    phone.close();
                }
            }
        }
        return mUserPhoneList;
    }
    public void refreshView() {

    }
}
