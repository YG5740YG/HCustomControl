package SimpleControls.MScrollControl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import yg.customcontrol.com.mylibrary.R;


/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class DrawerAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<DrawerContentModel> drawerContentModels;
    private boolean mShowAll;
    private int mTextAlpha=255;
    private View view;
    public DrawerAdapter(Context context, List<DrawerContentModel> drawerContentModels, boolean showAll){
        this.mContext=context;
        this.drawerContentModels=drawerContentModels;
        mShowAll=showAll;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drag_item, null);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myViewHolder myHolder = (myViewHolder) holder;
        myHolder.itemDrawerText.setText(drawerContentModels.get(position).getContent());
        myHolder.itemDrawerText.setTextColor(Color.argb(mTextAlpha, 0, 0, 0));
        Drawable topDrawable = mContext.getResources().getDrawable(drawerContentModels.get(position).getIcon());
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        myHolder.itemDrawerText.setCompoundDrawables(null, topDrawable, null, null);
        myHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClik(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawerContentModels.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView itemDrawerText;
        public myViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            itemDrawerText = (TextView) itemView.findViewById(R.id.item_drawer_text);
        }
    }

    public void refresh(int textAlpha){
        mTextAlpha=textAlpha;
        notifyDataSetChanged();
    }

    private void itemClik(int position){
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
        }
    }
}