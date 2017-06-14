package com.emexezidis.alex.ErasmusUoiApp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.classes.Place;
import com.emexezidis.alex.ErasmusUoiApp.activities.PlaceInfoScreen;

import java.util.ArrayList;

public class PlacesCardviewAdapter extends RecyclerView.Adapter<PlacesCardviewAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<Place> cardviewDataset;

    public PlacesCardviewAdapter(ArrayList<Place> cardviewDataset, Context context) {
        this.cardviewDataset = cardviewDataset;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_card_layout, parent, false);
        final CardViewHolder pvh = new CardViewHolder(v);

        pvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlaceInfoScreen.class);
                intent.putExtra("place", cardviewDataset.get(pvh.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        return pvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, final int position) {

        cardViewHolder.mCardTitle.setText(cardviewDataset.get(position).getName());
        cardViewHolder.mCardDescription.setText(cardviewDataset.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return cardviewDataset.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        TextView mCardTitle;
        TextView mCardDescription;

        CardViewHolder(View itemView) {
            super(itemView);
            mCardTitle = (TextView) itemView.findViewById(R.id.card_layout_name);
            mCardDescription = (TextView) itemView.findViewById(R.id.card_layout_description);
        }

    }

    public int getPositionOfObject(Place inputObject) {

        for (int i = 0; i < cardviewDataset.size(); i++) {

            if (cardviewDataset.get(i).equals(inputObject)) {
                return i;
            }
        }

        // If the Object is not found:
        return 0;
    }
}

