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

import com.example.poolliver.DeliveryInfo;
import com.example.poolliver.R;
import com.example.poolliver.database.dataHolder;
import com.example.poolliver.userDeliveryInfo;

import java.util.List;

public class userActivityRecyclerAdapter extends RecyclerView.Adapter<userActivityRecyclerAdapter.MyViewHolder> {

    List<dataHolder> dataHolderList;
    Context context;

    public userActivityRecyclerAdapter(List<dataHolder> dataHolderList, Context context) {
        this.dataHolderList = dataHolderList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userordercardview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyViewHolder myViewHolderclass = (MyViewHolder) holder;
        dataHolder itemPosition = dataHolderList.get(position);

        myViewHolderclass.name.setText(String.valueOf(itemPosition.getname()));
        myViewHolderclass.itemtype.setText(itemPosition.getitemtype());
        myViewHolderclass.Price.setText(String.valueOf(itemPosition.getPrice()));
        myViewHolderclass.pickupAddress.setText(String.valueOf(itemPosition.getPickupAddress()));
        myViewHolderclass.dropAddress.setText(String.valueOf(itemPosition.getdropAddress()));
        myViewHolderclass.Time.setText(String.valueOf(itemPosition.getTime()));

        myViewHolderclass.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, userDeliveryInfo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            intent.putExtra("pickupaddress", String.valueOf(itemPosition.getPickupAddress()));
            intent.putExtra("pLat", String.valueOf(itemPosition.getpLat()));
            intent.putExtra("pLong", String.valueOf(itemPosition.getpLong()));
            intent.putExtra("dropaddress", String.valueOf(itemPosition.getdropAddress()));
            intent.putExtra("dLat", String.valueOf(itemPosition.getdLat()));
            intent.putExtra("dLong", String.valueOf(itemPosition.getdLong()));
            intent.putExtra("timings", String.valueOf(itemPosition.getTime()));
            intent.putExtra("uid", String.valueOf(itemPosition.getUid()));
            intent.putExtra("itemtype", String.valueOf(itemPosition.getitemtype()));
            intent.putExtra("name", String.valueOf(itemPosition.getname()));
            intent.putExtra("price", String.valueOf(itemPosition.getPrice()));

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, pickupAddress, dropAddress, itemtype, Price, Time;
        TextView pLat, pLong, dLat, dLong;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            pickupAddress = itemView.findViewById(R.id.pickupAdds);
            dropAddress = itemView.findViewById(R.id.dropAdds);
            itemtype = itemView.findViewById(R.id.cItemType);
            Price = itemView.findViewById(R.id.cPrice);
            Time = itemView.findViewById(R.id.ctime);
            cardView = itemView.findViewById(R.id.userCardview);

        }
    }
}
