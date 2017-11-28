package SimpleControls.MDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import SimpleControls.Tools.Tools;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class MDialog {
    AlertDialog.Builder mydialog;
    //弹出提示框
    public MDialog(Context mcontext, String message, int icon){
        AlertDialog.Builder dialog=create(mcontext,"",message,icon);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mydialog=dialog;
    }
    //弹出提示框，自定义标题
    public MDialog(Context mcontext,String title,String message,int icon){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mydialog=dialog;
    }
    //弹出提示框，自定义标题,增加一个自定义事件
    public MDialog(Context mcontext,String title,String message,String itemConfirm,DialogInterface.OnClickListener Confirm,int icon){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setPositiveButton(itemConfirm, Confirm);
        mydialog=dialog;
    }
    //弹出提示框，自定义标题,增加两个自定义事件
    public MDialog(Context mcontext,String title,String message,String itemConfirm,DialogInterface.OnClickListener Confirm
            ,String itemDelete,DialogInterface.OnClickListener Delete,int icon){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setPositiveButton(itemDelete,Delete );
        dialog.setNegativeButton(itemConfirm,Confirm);
        mydialog=dialog;
    }
    //弹出提示框，自定义标题,增加三个自定义事件
    public MDialog(Context mcontext,String title,String message,String itemConfirm,DialogInterface.OnClickListener Confirm
            ,String itemDelete,DialogInterface.OnClickListener Delete,String itemNeutra,DialogInterface.OnClickListener Neutra
            ,int icon){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setPositiveButton(itemDelete,Delete );//在最右边
        dialog.setNegativeButton(itemConfirm,Confirm);
        dialog.setNeutralButton(itemNeutra,Neutra);//在最左边
        mydialog=dialog;
    }
    //弹出提示框，自定义标题,增加1个自定义事件,带输入框
    public MDialog(Context mcontext, String title, String message, EditText editText, String itemConfirm, DialogInterface.OnClickListener Confirm
            , int icon){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setView(editText);
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });//在最右边
        dialog.setNegativeButton(itemConfirm,Confirm);
        mydialog=dialog;
    }
    //弹出提示框，自定义标题,增加1个自定义事件,带一组多选框，多选框会与提示内容冲突，如果需要显示多选框就不能设置内容
    public MDialog(Context mcontext, String title, String message, String itemConfirm, DialogInterface.OnClickListener Confirm
            , int icon, String[] items,boolean[]checkItems, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setMultiChoiceItems(items, checkItems, onMultiChoiceClickListener);
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });//在最右边
        dialog.setNegativeButton(itemConfirm,Confirm);
        mydialog=dialog;
    }
    //弹出提示框，自定义标题,增加1个自定义事件,带一组单选框，单选框会与提示内容冲突，如果需要显示多选框就不能设置内容
    public MDialog(Context mcontext, String title, String message, String itemConfirm, DialogInterface.OnClickListener Confirm
            , int icon, String[] items, int chcek,DialogInterface.OnClickListener checkClick){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setSingleChoiceItems(items, chcek, checkClick);
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });//在最右边
        dialog.setNegativeButton(itemConfirm,Confirm);
        mydialog=dialog;
    }
    //弹出提示框，自定义标题,增加1个自定义事件,带一组选框，选框会与提示内容冲突，如果需要显示选框就不能设置内容
    public MDialog(Context mcontext, String title, String message, String itemConfirm, DialogInterface.OnClickListener Confirm
            , int icon, String[] items,DialogInterface.OnClickListener checkClick){
        final AlertDialog.Builder dialog=create(mcontext,title,message,icon);
        dialog.setItems(items, checkClick);
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });//在最右边
        dialog.setNegativeButton(itemConfirm,Confirm);
        mydialog=dialog;
    }

    //初始化dialog
    protected AlertDialog.Builder create(Context mcontext, String title, String message,int Icon){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(mcontext);
        if(Tools.isEmpty(title)) {
            dialog.setTitle("小提示！");
        }else{
            dialog.setTitle(title);
        }
        if(Icon!=0){
            dialog.setIcon(Icon);
        }
        if(!Tools.isEmpty(message)) {
            dialog.setMessage(message);
        }
        dialog.setCancelable(false);//在 setCancelable()中传入了 false，表示 ProgressDialog 是不能通过 Back 键取消掉的
        return dialog;
    }

    //弹出自定义布局dialog提示框
    public MDialog(Context mcontext,int icon,int layout){
        AlertDialog.Builder dialog=create(mcontext,"",icon,layout);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mydialog=dialog;
    }
    //初始化dialog,自定义布局*********************************
    protected AlertDialog.Builder create(Context mcontext, String title,int Icon,int layout){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(mcontext);
        dialog.setView(layout);
        if(Tools.isEmpty(title)) {
            dialog.setTitle("自定义布局！");
        }else{
            dialog.setTitle(title);
        }
        if(Icon!=0){
            dialog.setIcon(Icon);
        }
        dialog.setCancelable(false);//在 setCancelable()中传入了 false，表示 ProgressDialog 是不能通过 Back 键取消掉的
        return dialog;
    }

    public void Show(){
        mydialog.create().show();
    }
    //    对话框菜单
    public void DialogMenu(final Context context){
        new AlertDialog.Builder(context)
                .setTitle("choice")
                .setItems(new String[] { "选择1", "选择2", "选择3", "选择4" },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(context,
                                        which + "", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }).show();
    }
}
