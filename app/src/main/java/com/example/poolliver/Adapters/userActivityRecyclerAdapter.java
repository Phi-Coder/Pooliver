package com.example.poolliver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poolliver.R;
import com.example.poolliver.database.dataHolder;
import com.example.poolliver.userDeliveryInfo;

import java.util.List;

public class userActivityRecyclerAdapter extends RecyclerView.Adapter<userActivityRecyclerAdapter.MyUserViewHolder> {

    List<dataHolder> userDataHolderList;
    Context context;

    public userActivityRecyclerAdapter(List<dataHolder> userDataHolderList, Context context) {
        this.userDataHolderList = userDataHolderList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userordercardview, parent, false);
        MyUserViewHolder viewHolder = new MyUserViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserViewHolder holder, int position) {

        MyUserViewHolder myUserViewHolderclass = (MyUserViewHolder) holder;
        dataHolder UseritemPosition = userDataHolderList.get(position);

        myUserViewHolderclass.name.setText(String.valueOf(UseritemPosition.getname()));
        myUserViewHolderclass.itemtype.setText(UseritemPosition.getitemtype());
        myUserViewHolderclass.Price.setText(String.valueOf(UseritemPosition.getPrice()));
        myUserViewHolderclass.pickupAddress.setText(String.valueOf(UseritemPosition.getPickupAddress()));
        myUserViewHolderclass.dropAddress.setText(String.valueOf(UseritemPosition.getdropAddress()));
        myUserViewHolderclass.Time.setText(String.valueOf(UseritemPosition.getTime()));

        myUserViewHolderclass.UsercardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, userDeliveryInfo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            intent.putExtra("pickupaddress", String.valueOf(UseritemPosition.getPickupAddress()));
//            intent.putExtra("pLat", String.valueOf(UseritemPosition.get()));
//            intent.putExtra("pLong", String.valueOf(UseritemPosition.getpLong()));
            intent.putExtra("dropaddress", String.valueOf(UseritemPosition.getdropAddress()));
//            intent.putExtra("dLat", String.valueOf(UseritemPosition.getdLat()));
//            intent.putExtra("dLong", String.valueOf(UseritemPosition.getdLong()));
            intent.putExtra("timings", String.valueOf(UseritemPosition.getTime()));
            intent.putExtra("uid", String.valueOf(UseritemPosition.getUid()));
            intent.putExtra("itemtype", String.valueOf(UseritemPosition.getitemtype()));
//            intent.putExtra("name", String.valueOf(UseritemPosition.getname()));
            intent.putExtra("price", String.valueOf(UseritemPosition.getPrice()));

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userDataHolderList.size();
    }

    public class MyUserViewHolder extends RecyclerView.ViewHolder {

        TextView name, pickupAddress, dropAddress, itemtype, Price, Time;
        TextView pLat, pLong, dLat, dLong;
        CardView UsercardView;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userNameU);
            pickupAddress = itemView.findViewById(R.id.pickupAddsU);
            dropAddress = itemView.findViewById(R.id.dropAddsU);
            itemtype = itemView.findViewById(R.id.ItemTypeU);
            Price = itemView.findViewById(R.id.PriceU);
            Time = itemView.findViewById(R.id.timeU);
            UsercardView = itemView.findViewById(R.id.userCardview);

        }
    }
}
