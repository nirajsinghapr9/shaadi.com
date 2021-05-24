package com.example.shaadi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shaadi.model.ResultData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<ResultData> data;
    private ClickListener clickListener;

    public ArrayList<String> list = new ArrayList<String>();
    boolean enabled = true;

    public MyAdapter(Context context) {
        this.context = context;

    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;

    }

    public void setList(List<ResultData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rview, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        ResultData serviceData = data.get(position);
        holder.name.setText(data.get(position).getName().getFirst()+ " (" + data.get(position).getDob().getAge()+")");
        holder.address.setText(data.get(position).getLocation().getCity());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.drawable.ic_pic);

        Glide.with(context)
                .load(data.get(position).getPicture().getLarge())
                .apply(requestOptions)
                .into(holder.pic);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.buttons.setVisibility(View.GONE);
                holder.message.setVisibility(View.VISIBLE);
                holder.message.setText("Member Accepted");
                holder.message.setTextColor(ContextCompat.getColor(context, R.color.teal_200));
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.buttons.setVisibility(View.GONE);
                holder.message.setVisibility(View.VISIBLE);
                holder.message.setText("Member Declined");
                holder.message.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
        });
        holder.itemView.setAlpha(0);
        holder.itemView.animate().alpha(1).start();
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();


    }

    static class MyViewHolder extends RecyclerView.ViewHolder {


        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.address)
        TextView address;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.Name)
        TextView name;
        @BindView(R.id.message)
        TextView message;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.pic)
        ImageView pic;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.accept)
        ImageView accept;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.reject)
        ImageView reject;
        @BindView(R.id.buttons)
        LinearLayout buttons;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }

    public interface ClickListener {
        void onClickViewOrder();
    }
}
