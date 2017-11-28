package SimpleControls.horizontalImage;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import MyTools.ImageLoader;
import butterknife.Unbinder;
import yg.customcontrol.com.mylibrary.R;

/**
 * @author: zhanggh
 * @date: 2017/11/27.
 */

public class MShowImageDialog extends DialogFragment {
    LinearLayout mDialogContent;
    ImageView mDialogImage;
    String mImageUrl="";
    Unbinder unbinder;
    public static MShowImageDialog getInstance() {
        return new MShowImageDialog();
    }
    public MShowImageDialog setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        return this;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout((int) (dm.widthPixels * 0.87), ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_image_show, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        mDialogImage=(ImageView) view.findViewById(R.id.dialog_img);
        mDialogContent=(LinearLayout)view.findViewById(R.id.dialog_content);
        ImageLoader.load(getContext(),mImageUrl,mDialogImage);
        mDialogContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

}
