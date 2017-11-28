package SimpleControls.horizontalImage;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import MyTools.ImageLoader;
import butterknife.ButterKnife;
import yg.customcontrol.com.mylibrary.R;

public class HorizontalImageAdapter extends RecyclerView.Adapter<HorizontalImageAdapter.ViewHolder> {

    private Context context;
    private List<String> urlList;

    private RecyclerView recyclerView;

    private ClickImageListen clickImageListen;


    public HorizontalImageAdapter(Context context){
        this.context = context;
        urlList = new ArrayList<>();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView = recyclerView;
    }

    public void setData(List<String> urlList){
        this.urlList.clear();
        this.urlList.addAll(urlList);
        notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public void setData(String url){
        this.urlList.clear();
        this.urlList.add(url);
        notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public interface ClickImageListen{
        void ClickImage(String src);
    }
    public void setMoveListen(ClickImageListen clickListen){
        this.clickImageListen=clickListen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String url = urlList.get(position);
        ImageLoader.load(context, url, holder.itemPicture);
        holder.itemPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickImageListen!=null){
                    clickImageListen.ClickImage(url);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return urlList != null ? urlList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView=(ImageView)itemView.findViewById(R.id.item_picture);
        }
    }

}
