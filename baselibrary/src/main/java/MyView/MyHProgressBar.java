package MyView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by 20160330 on 2017/2/9.
 */
public class MyHProgressBar {
    ProgressDialog mydialog;
    int xh_count = 0;
    boolean flage=false;
    //弹出提示框
    public MyHProgressBar(Context mcontext){
        ProgressDialog dialog=create(mcontext,"");
        mydialog=dialog;
    }
    //初始化dialog
    protected ProgressDialog create(Context mcontext, String message){
        final ProgressDialog dialog=new ProgressDialog(mcontext);// 创建ProgressDialog对象

        // 设置进度条风格，风格为圆形，旋转的
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // 设置ProgressDialog 标题
        dialog.setTitle("提示");

        // 设置ProgressDialog提示信息
        dialog.setMessage("这是一个长形进度条对话框");

        // 设置ProgressDialog标题图标
        dialog.setIcon(newhome.baseres.R.mipmap.icon_scan);

        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        dialog.setIndeterminate(false);

        // 设置ProgressDialog 进度条进度
        dialog.setProgress(100);

        // 设置ProgressDialog 是否可以按退回键取消
        dialog.setCancelable(true);
        dialog.setButton("取消下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!flage) {
                    flage = true;
                }else{
                    flage=false;
                }
            }
        });

        // 让ProgressDialog显示
//        xh_pDialog.show();
        return dialog;
    }
    public void Show(){
        mydialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    while (xh_count <= 100 && !flage) {
                        // 由线程来控制进度
                        mydialog.setProgress(xh_count++);
                        Thread.sleep(100);
                    }
//                    mydialog.cancel();//加载完毕后删除
                } catch (Exception e) {
                    mydialog.cancel();
                }
            }
        }.start();
    }
    public void Dismiss(){
        mydialog.dismiss();
    }
}
