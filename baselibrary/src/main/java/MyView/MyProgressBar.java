package MyView;

import android.app.ProgressDialog;
import android.content.Context;

import newhome.baselibrary.Tools.Tools;

/**
 * Created by 20160330 on 2017/2/9.
 */
//ProgressDialog与AlertDialog类似，详细查看MyDialog
public class MyProgressBar {
    ProgressDialog mydialog;
    //弹出提示框
    public MyProgressBar(Context mcontext,String title,String message){
        ProgressDialog dialog=create(mcontext,"",message);
        mydialog=dialog;
    }
    //弹出提示框
    public MyProgressBar(Context mcontext,String message){
        ProgressDialog dialog=create(mcontext,message);
        mydialog=dialog;
    }
    //弹出提示框
    public MyProgressBar(Context mcontext){
        ProgressDialog dialog=create(mcontext,"");
        mydialog=dialog;
    }
    //初始化dialog
    protected ProgressDialog create(Context mcontext, String message){
        final ProgressDialog dialog=new ProgressDialog(mcontext);
        dialog.setProgress(ProgressDialog.STYLE_SPINNER);
        if(!Tools.isEmpty(message)) {
            dialog.setMessage(message);
        }
        dialog.setCancelable(true);//在 setCancelable()中传入了 false，表示 ProgressDialog 是不能通过 Back 键取消掉的
        return dialog;
    }
    //初始化dialog
    protected ProgressDialog create(Context mcontext, String title, String message){
        final ProgressDialog dialog=new ProgressDialog(mcontext);
        dialog.setProgress(ProgressDialog.STYLE_SPINNER);
        if(Tools.isEmpty(title)) {
            dialog.setTitle("小提示！");
        }else{
            dialog.setTitle(title);
        }
        if(!Tools.isEmpty(message)) {
            dialog.setMessage(message);
        }
        dialog.setCancelable(true);//在 setCancelable()中传入了 false，表示 ProgressDialog 是不能通过 Back 键取消掉的
        return dialog;
    }
    public void Show(){
        mydialog.show();
    }
    public void Dismiss(){
        mydialog.dismiss();
    }
}
