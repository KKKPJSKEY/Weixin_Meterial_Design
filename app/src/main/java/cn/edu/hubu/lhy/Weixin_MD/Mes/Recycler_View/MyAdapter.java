package cn.edu.hubu.lhy.Weixin_MD.Mes.Recycler_View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.hubu.lhy.Weixin_MD.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private List<NatureModel> objectList;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<NatureModel> objectList) {
        mInflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = mInflater.inflate(R.layout.music_list_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        NatureModel current = objectList.get(position);
        myViewHolder.setData(current, position);
        myViewHolder.setListeners();
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private ImageView imgThumb, imgDelete, imgCopy;
        private int position;
        private NatureModel currentObject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            imgThumb = itemView.findViewById(R.id.img_thumb);
            imgCopy = itemView.findViewById(R.id.img_copy);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }


        public void setData(NatureModel current, int position) {
            this.title.setText(current.getTitle());
            this.imgThumb.setImageResource(current.getImageID());
            this.position = position;
            this.currentObject = current;


        }

        public void setListeners() {
            imgDelete.setOnClickListener(MyViewHolder.this);
            imgCopy.setOnClickListener(MyViewHolder.this);
            imgThumb.setOnClickListener(MyViewHolder.this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.img_copy:
                    addItem(position, currentObject);
                    break;
                case R.id.img_delete:
                    removeItem(position);
                    break;
            }
        }

        public  void removeItem(int position){
            objectList.remove(position);
            notifyItemRemoved(position);
             notifyItemRangeChanged(position, objectList.size());
            notifyDataSetChanged();
        }

        public void addItem(int position, NatureModel currentObject){
            objectList.add(position, currentObject);
            notifyItemInserted(position);
             notifyItemRangeChanged(position, objectList.size());
             notifyDataSetChanged();
        }

    }
}
