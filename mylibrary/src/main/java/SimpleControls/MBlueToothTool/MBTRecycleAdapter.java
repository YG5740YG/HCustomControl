package SimpleControls.MBlueToothTool;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import yg.customcontrol.com.mylibrary.R;


/**
 *
 * @author Administrator
 * @date 2017/12/6
 */

public class MBTRecycleAdapter extends RecyclerView.Adapter {
    List<BluetoothModel> mBluetoothModels;
    Context mContext;
    View mView;
    ItemClickListen mItemListen;
    AnimationDrawable animationSearchDrawable;

    public interface ItemClickListen{
        void setItemListen(int position,int state);
    }
    public void setItemClickListen(ItemClickListen itemClickListen){
        mItemListen=itemClickListen;
    }
    public MBTRecycleAdapter(Context context, List<BluetoothModel> bluetoothModels){
        this.mContext=context;
        this.mBluetoothModels=bluetoothModels;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth, null);
        return new myViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myViewHolder myHolder = (myViewHolder) holder;
        if(mBluetoothModels.get(position).getLayoutState()==0){
            myHolder.bluetoothLodingContent.setVisibility(View.VISIBLE);
            myHolder.itemDeviceState.setVisibility(View.GONE);
            if(mBluetoothModels.get(position).getTitle().contains(mContext.getResources().getString(R.string
                    .pair_printer_String))) {
                myHolder.bluetoothLoding.setImageResource(R.mipmap.new_printer);
            }else if(mBluetoothModels.get(position).getTitle().contains(mContext.getResources().getString(R.string
                    .bluetooth_String))){
                myHolder.bluetoothLoding.setImageResource(R.mipmap.bluetooth);
            }
            myHolder.itemDeviceId.setText(mBluetoothModels.get(position).getTitle());
        }else {
            myHolder.itemDeviceId.setText(
                    mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice
                            ().getName()+":"+
                    mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice
                    ().getAddress());
            myHolder.itemDeviceState.setText(TypeConversion(mBluetoothModels.get(position).getBlueTtoothList()
                    .getState()));
            myHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mItemListen!=null){
                        mItemListen.setItemListen(position,mBluetoothModels.get(position).getLayoutState());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mBluetoothModels.size()>0) {
            return mBluetoothModels.size();
        }else {
            return 0;
        }
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView itemDeviceId;
        TextView itemDeviceState;
        LinearLayout bluetoothLodingContent;
        ImageView bluetoothLoding;
        public myViewHolder(View itemView) {
            super(itemView);
            layout=(LinearLayout)itemView.findViewById(R.id.bluetooth_item_content);
            bluetoothLodingContent=(LinearLayout)itemView.findViewById(R.id.blue_tooth_loding_content);
            itemDeviceId = (TextView) itemView.findViewById(R.id.device_id);
            itemDeviceState = (TextView) itemView.findViewById(R.id.device_state);
            bluetoothLoding=(ImageView)itemView.findViewById(R.id.blue_tooth_loding);
        }
    }

    public void refresh(List<BluetoothModel> bluetoothModels){
        mBluetoothModels=bluetoothModels;
        notifyDataSetChanged();
    }
    public String TypeConversion(int value){
        String result="";
            switch (value)
            {
                case 1:
                    result= "在线";
                    break;
                case 2:
                    result= "未在线";
                    break;
                case 3:
                    result= "已连接";
                    break;
                case 4:
                    result= "未连接";
                    break;
                case 5:
                    result= "连接失败";
                    break;
                case 6:
                    result= "已断开";
                    break;
               default:
                   result="未在线";
                   break;

            }
            return result;
    }
}
