package com.example.jalankuy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.appcompat.widget.AppCompatDrawableManager.get;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTicket> myTickets;

    public TicketAdapter(Context c,ArrayList<MyTicket> p){
        context = c;
        myTickets = p;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.xnama_wisata.setText(myTickets.get(i).getNama_wisata());
        myViewHolder.xlokasi.setText(myTickets.get(i).getLokasi());
        myViewHolder.xjumlah_tiket.setText(myTickets.get(i).getJumlah_tiket() + " Tickets");
        String getNamaWisata = myTickets.get(i).getNama_wisata();


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetails = new Intent(context, MyTicketActivity.class);
                gotomyticketdetails.putExtra("nama_wisata",getNamaWisata);
                context.startActivity(gotomyticketdetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTickets.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView xnama_wisata,xlokasi,xjumlah_tiket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_tiket = itemView.findViewById(R.id.xjumlah_tiket);
        }
    }

}
