package com.example.poolliver.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poolliver.DeliveryInfo;
import com.example.poolliver.R;
import com.example.poolliver.database.dataHolder;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<dataHolder> dataHolderList;

    public RecyclerViewAdapter(List<dataHolder> dataHolderList) {
        this.dataHolderList = dataHolderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordercardview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyViewHolder myViewHolderclass = (MyViewHolder) holder;
        dataHolder itemPosition = dataHolderList.get(position);

        myViewHolderclass.username.setText(itemPosition.getname());
        myViewHolderclass.itemtype.setText(itemPosition.getitemtype());
        myViewHolderclass.Price.setText(String.valueOf(itemPosition.getPrice()));
        holder.pickupAddress.setText(String.valueOf(itemPosition.getPickupAddress()));
        holder.dropAddress.setText(String.valueOf(itemPosition.getdropAddress()));
        myViewHolderclass.Time.setText(String.valueOf(itemPosition.getTime()));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.parentLayout.getContext(), DeliveryInfo.class);
                intent.putExtra("pickupaddress", itemPosition.getPickupAddress());
                intent.putExtra("dropaddress", itemPosition.getdropAddress());
                intent.putExtra("timings", itemPosition.getTime());
                intent.putExtra("itemtype", itemPosition.getitemtype());
                intent.putExtra("username", itemPosition.getname());
                intent.putExtra("price", itemPosition.getPrice());
                holder.parentLayout.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataHolderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username, pickupAddress, dropAddress, itemtype, Price, Time;
        RelativeLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            pickupAddress = itemView.findViewById(R.id.pickupAdds);
            dropAddress = itemView.findViewById(R.id.dropAdds);
            itemtype = itemView.findViewById(R.id.cItemType);
            Price = itemView.findViewById(R.id.cPrice);
            Time = itemView.findViewById(R.id.ctime);
            parentLayout = itemView.findViewById(R.id.parentRelative);

        }
    }
}
