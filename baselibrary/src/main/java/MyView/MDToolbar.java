package MyView;

/**
 * Created by 20160330 on 2017/2/28.
 */
        import android.content.Context;
        import android.content.res.TypedArray;
        import android.graphics.drawable.Drawable;
        import android.support.annotation.Nullable;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.widget.Toolbar;
        import android.util.AttributeSet;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import newhome.baselibrary.R;
        import newhome.baselibrary.Tools.Logs;
        import newhome.baselibrary.Tools.UITools;

/**
 * Created by scorpio on 2017/2/17.
 */

public class MDToolbar extends RelativeLayout {


    private TextView mBack;
    private TextView mTitle;
    private TextView mClose ;
    private Context context ;
    private RelativeLayout base_toolbar ;
    private TextView netView;
    public OnMenuClickListener onMenuClickListener ;
    int specSize;
    private int backTitleColor;
    private int rightIcon;
    private int backIconColor;

    private int BackIcon ;

    public MDToolbar(Context context) {
        super(context);
        init(context);
    }

    public MDToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MDToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,height);
    }

    private int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST){
            int  size = UITools.dip2px(context,48);
            specSize = Math.min(size,specSize);

        }else if(specMode == MeasureSpec.EXACTLY){
            TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[] {
                    android.R.attr.actionBarSize
            });

            specSize = (int) actionbarSizeTypedArray.getDimension(0, 0);
        }
        Logs.Debug("action size ==="+ specSize+ "=="+ specMode);


        return specSize;
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public void init(Context context){
        this.context = context ;

        View view = LayoutInflater.from(context).inflate(R.layout.base_toolbar,null);
        mBack = (TextView)view.findViewById(R.id.back);
        mTitle = (TextView)view.findViewById(R.id.title);
        mClose = (TextView)view.findViewById(R.id.close);
        base_toolbar = (RelativeLayout) view.findViewById(R.id.base_toolbar);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UITools.dip2px(context,48));
        view.setLayoutParams(lp);
        addView(view);

        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuClickListener.onBackClick();
            }
        });

        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuClickListener.onRigthClick();
            }
        });
    }


    public void setToolbarBackgroud(int res){
        base_toolbar.setBackgroundColor(res);
    }

    public void setMainTitle(String title){
        mTitle.setText(title);
    }

    public void setBackTitle(String title){
        mBack.setText(title);
    }

    public void setRightTitle(String title){
        mClose.setText(title);
    }

    public void setMainTitleColor(int mainTitleColor) {
        mTitle.setTextColor(mainTitleColor);
    }

    public void setRightTitleColor(int rightTitleColor) {
        mClose.setTextColor(rightTitleColor);
    }

    public void setBackTitleColor(int backTitleColor) {
        mBack.setTextColor(backTitleColor);
    }

    public void setRightIcon(int rightIcon) {
        Drawable drawable = ContextCompat.getDrawable(getContext(),rightIcon);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mClose.setCompoundDrawables(null,null,drawable,null);
    }

    public void setBackIcon(int backIcon) {
        this.BackIcon = backIcon ;
        Drawable drawable = ContextCompat.getDrawable(getContext(),backIcon);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mBack.setCompoundDrawables(drawable,null,null,null);
    }
    public void setBackIcon(int backIcon,int width,int height) {
        this.BackIcon = backIcon ;
        Drawable drawable = ContextCompat.getDrawable(getContext(),backIcon);
        drawable.setBounds(0,0,width,height);
        mBack.setCompoundDrawables(drawable,null,null,null);
    }


    public interface OnMenuClickListener{
        void onBackClick();
        void onRigthClick();
    }

    public void setRightVisibility(int visibility) {

        if (mClose != null) {
            if (visibility == GONE){
                visibility = INVISIBLE ;
            }
            mClose.setVisibility(visibility);
        }
    }
}
