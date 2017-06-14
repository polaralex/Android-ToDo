package com.emexezidis.alex.ErasmusUoiApp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.classes.Card;
import com.emexezidis.alex.ErasmusUoiApp.R;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {

    private ArrayList<Card> cardviewDataset;

    public CardsAdapter(ArrayList<Card> cardviewDataset) {
        this.cardviewDataset = cardviewDataset;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main_card_view, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int position) {

        final Card currentCard = cardviewDataset.get(position);

        cardViewHolder.mCardTitle.setText(currentCard.getTitle());
        cardViewHolder.mCardDescription.setText(currentCard.getDescription());
        cardViewHolder.mIcon.setImageResource(currentCard.getIcon());

        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentCard.hasOnClickAction()) {
                    v.getContext().startActivity(currentCard.getIntentForOnClick());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardviewDataset.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        TextView mCardTitle;
        TextView mCardDescription;
        ImageView mIcon;

        CardViewHolder(View itemView) {
            super(itemView);
            mCardTitle = (TextView) itemView.findViewById(R.id.cardviewTitle);
            mCardDescription = (TextView) itemView.findViewById(R.id.cardviewDescription);
            mIcon = (ImageView) itemView.findViewById(R.id.card_icon);
        }
    }

}