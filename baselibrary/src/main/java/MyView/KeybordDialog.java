package MyView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class KeybordDialog extends Dialog {
    View contentView;
    Context mcontext;
    Activity mactivity;
    WindowManager.LayoutParams lp;
    EditText etprice,etpostage;
    List<EditText> editTexts;
    TextView txtzero, txtone,txttwo,txtthree,txtfour,txtfive,txtsix,txtseven,txteight,txtnine,txtdian,txtsubmit,txtback,dimiss,text_image;
    List<TextView> textViews;
    String number;
    //    public PopupWindow popupWindow;
    int selectPosition;
    TextView backTextView;
    double et_price=0;
    double et_postage=0;
    public KeybordDialog(final Context context, View view,double et_price,double et_postage, final Activity activity, TextView backTextView){
        super(context);
        //一个自定义的布局，作为显示的内容
        this.et_price=et_price;
        this.et_postage=et_postage;
        lp=activity.getWindow().getAttributes();
        selectPosition=0;
        lp.alpha=1f;
        mcontext=context;
        mactivity=activity;
        this.backTextView=backTextView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        findView();
        setUp();
    }
    public void Dismiss(){
        dismiss();
    }
    public void findView(){
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        contentView= LayoutInflater.from(mcontext).inflate(R.layout.dialog_mykeybord,null);
        setContentView(contentView);
        etprice=(EditText)contentView.findViewById(R.id.et_price);
        etpostage=(EditText)contentView.findViewById(R.id.etpostage);
        txtone =(TextView)contentView.findViewById(R.id.txtone);
        txttwo =(TextView)contentView.findViewById(R.id.txttwo);
        txtthree =(TextView)contentView.findViewById(R.id.txtthree);
        txtfour =(TextView)contentView.findViewById(R.id.txtfour);
        txtfive =(TextView)contentView.findViewById(R.id.txtfive);
        txtsix =(TextView)contentView.findViewById(R.id.txtsix);
        txtseven =(TextView)contentView.findViewById(R.id.txtseven);
        txteight =(TextView)contentView.findViewById(R.id.txteight);
        txtnine =(TextView)contentView.findViewById(R.id.txtnine);
        txtdian =(TextView)contentView.findViewById(R.id.txtdian);
        txtback =(TextView)contentView.findViewById(R.id.txtback);
        txtsubmit =(TextView)contentView.findViewById(R.id.txtsubmit);
        txtzero=(TextView)contentView.findViewById(R.id.txtzero);
        dimiss=(TextView)contentView.findViewById(R.id.dimiss);
        text_image=(TextView)contentView.findViewById(R.id.text_image);
    }
    public void setUp(){
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        textViews=new ArrayList<TextView>();
        number="";
        textViews.add(txtzero);
        textViews.add(txtone);
        textViews.add(txttwo);
        textViews.add(txtthree);
        textViews.add(txtfour);
        textViews.add(txtfive);
        textViews.add(txtsix);
        textViews.add(txtseven);
        textViews.add(txteight);
        textViews.add(txtnine);
        textViews.add(txtdian);
        textViews.add(txtback);
        textViews.add(txtsubmit);
        textViews.add(dimiss);
        editTexts=new ArrayList<EditText>();
        editTexts.add(etprice);
        editTexts.add(etpostage);
        etprice.setFocusable(true);
        etprice.setFocusableInTouchMode(true);
        etprice.requestFocus();
        etprice.requestFocusFromTouch();
        etprice.setText(et_price==0?"":String.valueOf(et_price));
        etprice.setSelection(etprice.getText().toString().length());
        number=etprice.getText().toString();
        selectPosition=number.length();
        etpostage.setText(et_postage==0?"":String.valueOf(et_postage).substring(0,String.valueOf(et_postage).indexOf(".")));

        disableShowSoftInput(editTexts);//光标显示，软键盘禁止弹出
        editClick(editTexts);
        txtClick(textViews);
    }
    public void editClick(List<EditText> editTexts){
        try {
            for (final EditText editText : editTexts) {
                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        number = editText.getText().toString();
                        selectPosition = editText.getSelectionStart();

                    }
                });
                editText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                editText.setFocusable(true);
                                editText.setFocusableInTouchMode(true);
                                editText.requestFocus();
                                editText.requestFocusFromTouch();
                                break;
                        }
                        return false;
                    }
                });
            }
        }catch (Exception e){

        }
    }
    public void txtClick(List<TextView> textViews){
        int i=0;
        for(final TextView textView:textViews){
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (finalI < 11) {
                            Logs.Debug("gg==number=====gg==15=" + number);
                            if (view.getId() == R.id.txtdian && number.contains(".")) return;
                            if ((view.getId() == R.id.txtzero || view.getId() == R.id.txtdian) && selectPosition == 0) {
                                if(etpostage.isFocused()){
                                    etpostage.setSelection(number.length());
                                    selectPosition=number.length();
                                }else if(etprice.isFocused()){
                                    etprice.setSelection(number.length());
                                    selectPosition=number.length();
                                }
                                return;
                            }
                            Logs.Debug("gg==number=====gg==11=" + number);
                            String num = "";
                            String num_last = "";
                            if (number.toString().contains(".")) {
                                num = number.toString().split("\\.")[0];
                                if (num.length() + 1 != number.length()) {
                                    num_last = number.split("\\.")[1];
                                }
                            } else {
                                num = number;
                                num_last = "";
                            }
                            Logs.Debug("gg==number=====gg==12=" + num + "==last==" + num_last);
                            if (view.getId() != R.id.txtdian) {
                                if (etpostage.isFocused() && num.length() >= 3 && num.length() >= selectPosition) {
//                                    MyToast(mcontext, mactivity, "运费不能超过999哦", 10);
                                    return;
                                }
                                if ((etprice.isFocused()) && num.length() >= 6 && num.length() >= selectPosition) {
//                                    MyToast(mcontext, mactivity, "价格不能超过999999哦", 10);
                                    return;
                                }
                            }
                            if (!Tools.isEmpty(num_last) && num_last.length() >= 2 && num.length() < selectPosition) {
                                return;
                            } else {
                                if (number.length() > selectPosition) {
                                    number = number.substring(0, selectPosition) + textView.getText() + number.substring(selectPosition, number.length());
                                } else {
                                    number = number + textView.getText();
                                }
                                selectPosition++;
                            }
                        } else if(R.id.dimiss==view.getId()){
                            dismiss();
                        }else {
                            if (R.id.txtsubmit == view.getId()) {
                                dismiss();
//                                popupWindow.dismiss();
                            }
                        }
                        if (etprice.isFocused()) {
                            etprice.setText(number);
                            etprice.setSelection(selectPosition);
                            et_price=Double.parseDouble(Tools.isEmpty(number.toString()) ? "0" : number.toString());
                            backTextView.setText(Tools.isEmpty(number.toString()) ? "" : String.valueOf(Double.parseDouble(number)));
                        } else if (etpostage.isFocused()) {
                            Logs.Debug("gg============etpostage=="+number);
                            if(number.contains(".")){
//                                    etpostage.setText(number.substring(0,number.indexOf(".")));
                                etpostage.setText(number.replace(".",""));
                                selectPosition=etpostage.length();
                            }else {
                                etpostage.setText(number);
                            }
//                                postage=Double.parseDouble(Tools.isEmpty(number.toString())?"0":number.toString());
                            et_postage=Double.parseDouble(Tools.isEmpty(number.toString()) ? "0" : number.toString());
                            etpostage.setSelection(selectPosition);
                        }
                    }catch (Exception e){

                    }
                }
            });
            txtback.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        if (selectPosition > 0) {
                            if (number.substring(selectPosition - 1, selectPosition).equals(".")) {
                                if (etpostage.isFocused() && number.length() - 1 > 3) {
                                    return false;
                                } else if ((etprice.isFocused()) && number.length() - 1 > 6) {
                                    return false;
                                }
                            }
                        }
                        if (!Tools.isEmpty(number)) {
                            Logs.Debug("selectposition======12==" + selectPosition + "==number==" + number);
                            if (selectPosition > 0) {
                                Logs.Debug("selectposition======" + selectPosition);
                                selectPosition--;
                                if (number.length() > selectPosition+1) {
                                    Logs.Debug("selectposition======1");
                                    number = number.substring(0, selectPosition) + number.substring(selectPosition + 1, number.length());
                                } else {
                                    Logs.Debug("selectposition======2");
                                    number = number.substring(0, selectPosition);
                                }
                            }
                        } else {
                            number = "";
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtback.setBackground(mcontext.getResources().getDrawable(R.drawable.bd_bord_gray_ss));
                        text_image.setBackground(mcontext.getResources().getDrawable(R.mipmap.keybordclearblack));
                        }
                    } else if(event.getAction()==MotionEvent.ACTION_UP){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            text_image.setBackground(mcontext.getResources().getDrawable(R.mipmap.keybordclearwhite));
                            txtback.setBackground(mcontext.getResources().getDrawable(R.drawable.bd_bord_gray_sss));
                        }
                    }
                    return false;
                }
            });
            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            if(textView.getId()==R.id.txtdian){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    textView.setBackground(mactivity.getResources().getDrawable(R.drawable.bd_bord_gray_ss));
                                }
                            }else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    textView.setBackground(mactivity.getResources().getDrawable(R.drawable.bd_bord_gray_ssg));
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if(textView.getId()==R.id.txtdian){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    textView.setBackground(mactivity.getResources().getDrawable(R.drawable.bd_bord_gray_sss));
                                }
                            }else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    textView.setBackground(mactivity.getResources().getDrawable(R.drawable.bd_bord_gray_ss));
                                }
                            }

                            break;
                    }
                    return false;
                }
            });
            i++;
        }
    }
    // 禁止Edittext弹出软件盘，光标依然正常显示。
    public void disableShowSoftInput(List<EditText> editTexts) {
        for (EditText editText : editTexts) {
            if (android.os.Build.VERSION.SDK_INT <= 10) {
                editText.setInputType(InputType.TYPE_NULL);
            } else {
                Class<EditText> cls = EditText.class;
                Method method;
                try {
                    method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(editText, false);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                try {
                    method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(editText, false);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }
    public double getPostage(){
        return et_postage;
    }
    public double getPrice(){
        return et_price;
    }
    //自定义Toast
    public void MyToast(Context context,Activity activity,String mytoasttitle,int Duration){
        Toast toast=new Toast(context);
        LayoutInflater inflater =activity.getLayoutInflater();
        View Layout = inflater.inflate(R.layout.mytoastlayout,null);
        ImageView image = (ImageView) Layout
                .findViewById(R.id.toast_image);
        image.setImageResource(R.mipmap.chat_bq1);
        TextView title = (TextView) Layout.findViewById(R.id.toast_text);
        title.setText(mytoasttitle);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER ,0, 0);
        toast.setDuration(Duration);
        toast.setView(Layout);
        toast.show();
    }
}
