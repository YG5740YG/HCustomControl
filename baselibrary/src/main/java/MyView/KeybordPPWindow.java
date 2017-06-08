package MyView;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;

/**
 * Created by 20160330 on 2017/1/10.
 */

public class KeybordPPWindow {
    View contentView;
    Context mcontext;
    Activity mactivity;
    WindowManager.LayoutParams lp;
    EditText etprice,etprice_original,etpostage;
    List<EditText> editTexts;
    CheckBox checkBox_postage;
    TextView txtzero, txtone,txttwo,txtthree,txtfour,txtfive,txtsix,txtseven,txteight,txtnine,txtdian,txtsubmit,txtback;
    TextView txtjing;
    List<TextView> textViews;
    String number;
    PopupWindow popupWindow;
    int selectPosition;
    TextView backTextView;
//    PublishDraftData draftData;
    public KeybordPPWindow(final Context context, View view, final Activity activity, TextView backTextView){
        //一个自定义的布局，作为显示的内容
        lp=activity.getWindow().getAttributes();
        selectPosition=0;
        lp.alpha=0.5f;
        mcontext=context;
        mactivity=activity;
//        this.draftData=draftData;
        this.backTextView=backTextView;
        findView();
        checkBox_postage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    etpostage.setText("0");
                    etpostage.setFocusable(true);
                    etpostage.setFocusableInTouchMode(true);
                    etpostage.requestFocus();
                    etpostage.requestFocusFromTouch();
                    etpostage.setSelection(etpostage.getText().length());
//                    draftData.getDraft().setPostage(0);
                    number="0";
                }else{
                    etpostage.setText("");
                    selectPosition=0;
                    number="";
                }
            }
        });
        popupWindow=new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
            }
        });
        popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.color.es_w));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        isDismiss();
        setUp();
    }
    public void isDismiss(){
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha=1.0f;
                mactivity.getWindow().setAttributes(lp);
            }
        });
    }
 public void findView(){
     contentView= LayoutInflater.from(mcontext).inflate(R.layout.mykeyboardpupopwindow,null);
     etprice=(EditText)contentView.findViewById(R.id.et_price);
     etprice_original=(EditText)contentView.findViewById(R.id.etprice_original);
     etpostage=(EditText)contentView.findViewById(R.id.etpostage);
     checkBox_postage=(CheckBox)contentView.findViewById(R.id.checkbox_postage);
     txtone =(TextView)contentView.findViewById(R.id.txtone);
     txttwo =(TextView)contentView.findViewById(R.id.txttwo);
     txtthree =(TextView)contentView.findViewById(R.id.txtthree);
     txtfour =(TextView)contentView.findViewById(R.id.txtfour);
     txtfive =(TextView)contentView.findViewById(R.id.txtfive);
     txtsix =(TextView)contentView.findViewById(R.id.txtsix);
     txtseven =(TextView)contentView.findViewById(R.id.txtseven);
     txteight =(TextView)contentView.findViewById(R.id.txteight);
     txtnine =(TextView)contentView.findViewById(R.id.txtnine);
     txtjing =(TextView)contentView.findViewById(R.id.txtjing);
     txtdian =(TextView)contentView.findViewById(R.id.txtdian);
     txtsubmit =(TextView)contentView.findViewById(R.id.txtsubmit);
     txtback =(TextView)contentView.findViewById(R.id.txtback);
     txtzero=(TextView)contentView.findViewById(R.id.txtzero);
 }
    public void setUp(){
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
        textViews.add(txtjing);
        textViews.add(txtsubmit);
        textViews.add(txtback);
        editTexts=new ArrayList<EditText>();
        editTexts.add(etprice);
        editTexts.add(etpostage);
        editTexts.add(etprice_original);
        etprice.setFocusable(true);
        etprice.setFocusableInTouchMode(true);
        etprice.requestFocus();
        etprice.requestFocusFromTouch();
//        etprice.setText(draftData.getDraft().getPrice()==0?"":String.valueOf(draftData.getDraft().getPrice()));
        etprice.setSelection(etprice.getText().toString().length());
        number=etprice.getText().toString();
        selectPosition=number.length();
//        etpostage.setText(draftData.getDraft().getPostage()==0?"":String.valueOf(draftData.getDraft().getPostage()));
//        etpostage.setSelection(etpostage.getText().toString().length());
//        etprice_original.setText(draftData.getDraft().getPrice_original()==0?"":String.valueOf(draftData.getDraft().getPrice_original()));
//        etprice_original.setSelection(etprice_original.getText().toString().length());
        disableShowSoftInput(editTexts);//光标显示，软键盘禁止弹出
        editClick(editTexts);
        txtClick(textViews);
    }
    public void editClick(List<EditText> editTexts){
        for (final  EditText editText:editTexts){
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    number = editText.getText().toString();
                    selectPosition=editText.getSelectionStart();
                    Logs.Debug("gg==number=====gg=="+selectPosition);
                }
            });
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()) {
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
    }
    public void txtClick(List<TextView> textViews){
        int i=0;
        for(final TextView textView:textViews){
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(finalI <11) {
                            if(view.getId()==R.id.txtdian&&number.contains("."))return;
                            if((view.getId()==R.id.txtzero||view.getId()==R.id.txtdian)&&selectPosition==0)return;
                            Logs.Debug("gg==number=====gg==11="+number);
                            String num="";
                            String num_last="";
                            if(number.toString().contains(".")){
                                num=number.toString().split("\\.")[0];
                                if(num.length()+1!=number.length()) {
                                    num_last = number.split("\\.")[1];
                                }
                            }else{
                                num=number;
                                num_last="";
                            }
                            Logs.Debug("gg==number=====gg==12="+num+"==last=="+num_last);
                            if(view.getId()!=R.id.txtdian) {
                                if (etpostage.isFocused() && num.length() >= 3&&num.length()>=selectPosition) {
                                        MyToast(mcontext, mactivity, "运费不能超过999哦", 100);
                                        return;
                                    }
                                if ((etprice.isFocused() || etprice_original.isFocused()) &&num.length() >= 6&&num.length()>=selectPosition) {
                                        MyToast(mcontext, mactivity, "价格不能超过999999哦", 100);
                                        return;
                                    }
                            }
                            if(!Tools.isEmpty(num_last)&&num_last.length()>=2&&num.length()<selectPosition)
                            {
                                return;
                            }else {
                                if (checkBox_postage.isChecked() && etpostage.isFocused()) {
                                    number = "";
                                    selectPosition = etpostage.getText().toString().length();
                                    Logs.Debug("" + selectPosition);
                                    return;
                                }
                                if (number.length() > selectPosition) {
                                    number = number.substring(0, selectPosition) + textView.getText() + number.substring(selectPosition, number.length());
                                } else {
                                    number = number + textView.getText();
                                }
                                selectPosition++;
                            }
                        }else{
                            int i1 = view.getId();
                            if (i1 == R.id.txtback) {
                                if (selectPosition > 0) {
                                    if (number.substring(selectPosition - 1, selectPosition).equals(".")) {
                                        if (etpostage.isFocused() && number.length() - 1 > 3) {
                                            return;
                                        } else if ((etprice.isFocused() || etprice_original.isFocused()) && number.length() - 1 > 6) {
                                            return;
                                        }
                                    }
                                }
                                if (!Tools.isEmpty(number)) {
                                    Logs.Debug("selectposition======12==" + selectPosition + "==number==" + number);
                                    if (selectPosition > 0) {
                                        Logs.Debug("selectposition======" + selectPosition);
                                        selectPosition--;
                                        if (number.length() > selectPosition) {
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

                            } else if (i1 == R.id.txtjing) {
                                if (selectPosition > 0) {
                                    selectPosition--;
                                    if (etprice.isFocused()) {
                                        etprice.setSelection(selectPosition);
                                    } else if (etpostage.isFocused()) {
                                        etpostage.setSelection(selectPosition);
                                    } else if (etprice_original.isFocused()) {
                                        etprice_original.setSelection(selectPosition);
                                    }
                                }

                            } else if (i1 == R.id.txtsubmit) {
                                popupWindow.dismiss();

                            }
                        }
                        if(etprice.isFocused()){
                                etprice.setText(number);
                            etprice.setSelection(selectPosition);
//                            draftData.getDraft().setPrice(Double.parseDouble(Tools.isEmpty(number.toString())?"0":number.toString()));
                            backTextView.setText(Tools.isEmpty(number.toString())?"":String.valueOf(Double.parseDouble(number)));
                        }else if(etpostage.isFocused()){
                            if(checkBox_postage.isChecked()){
                                etpostage.setText("0");
//                                draftData.getDraft().setPostage(0);
                                number="";
                            }else{
                                if(number.contains(".")){
                                etpostage.setText(number.substring(0,number.indexOf(".")));
                                }else {
                                    etpostage.setText(number);
                                }
//                                postage=Double.parseDouble(Tools.isEmpty(number.toString())?"0":number.toString());
//                                draftData.getDraft().setPostage(Double.parseDouble(Tools.isEmpty(number.toString())?"0":number.toString()));
                            }
                            etpostage.setSelection(selectPosition);
                        }else if(etprice_original.isFocused()){
                                etprice_original.setText(number);
//                            price_original=Double.parseDouble(Tools.isEmpty(number.toString())?"0":number.toString());
//                            draftData.getDraft().setPrice_original(Double.parseDouble(Tools.isEmpty(number.toString())?"0":number.toString()));
                            etprice_original.setSelection(selectPosition);
                        }
                    }
                });
            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                textView.setBackground(mactivity.getResources().getDrawable(R.drawable.bd_bord_gray_ssg));
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                textView.setBackground(mactivity.getResources().getDrawable(R.drawable.bd_bord_gray_ss));
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
    //自定义Toast
    public void MyToast(Context context, Activity activity, String mytoasttitle, int Duration){
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