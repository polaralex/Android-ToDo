package com.emexezidis.alex.ErasmusUoiApp.adapters;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.classes.Step;

import java.util.ArrayList;

public class ErasmusStepsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static ArrayList<Step> stepsList;
    private static ClickListener clickListener;

    private static final int TYPE_ITEM_WITH_HEADER = 0;
    private static final int TYPE_SIMPLE_ITEM = 1;

    public static class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private TextView mTextViewTitle;
        private CheckBox mCheckBox;

        public SimpleItemViewHolder (View v) {

            super(v);

            mTextViewTitle = (TextView) v.findViewById(R.id.stepTitle);
            mCheckBox = (CheckBox) v.findViewById(R.id.steps_list_checkbox);

            v.setOnClickListener(this);
            mCheckBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick (View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {

            if (mCheckBox.isChecked()) {
                stepsList.get(getAdapterPosition()).setState(true);
            } else {
                stepsList.get(getAdapterPosition()).setState(false);
            }
        }
    }

    public static class ItemWithHeaderViewHolder extends SimpleItemViewHolder {

        private TextView groupHeaderText;

        public ItemWithHeaderViewHolder(View v) {

            super(v);
            groupHeaderText = (TextView) v.findViewById(R.id.steps_list_header_textview);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ErasmusStepsRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public ErasmusStepsRecyclerViewAdapter(ArrayList<Step> myDataset) {
        stepsList = myDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final SimpleItemViewHolder vh;

        if (viewType == TYPE_ITEM_WITH_HEADER) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.with_header_step_list_layout, parent, false);

            vh = new ItemWithHeaderViewHolder(view);

        } else {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checkbox_list_for_erasmus_steps, parent, false);

            vh = new SimpleItemViewHolder(view);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        // Depending on the type of holder, we cast to the type to be able
        // to change the data in the view:

        final SimpleItemViewHolder itemViewHolder = (SimpleItemViewHolder) holder;
        final Step currentStep = stepsList.get(position);

        if (holder instanceof ItemWithHeaderViewHolder) {
            ((ItemWithHeaderViewHolder) itemViewHolder).
                    groupHeaderText.setText(currentStep.getGroup());
        }

        itemViewHolder.mTextViewTitle.setText(currentStep.getName());

        itemViewHolder.mCheckBox.setOnCheckedChangeListener(null);

        changeViewDependingOnStates(itemViewHolder, currentStep.getState());

        itemViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    currentStep.setState(true);
                } else {
                    currentStep.setState(false);
                }

                changeViewDependingOnStates(itemViewHolder, isChecked);
            }
        });
    }

    public void changeViewDependingOnStates(SimpleItemViewHolder viewHolder, boolean state) {

        if (state) {
            viewHolder.mCheckBox.setChecked(true);
            viewHolder.mTextViewTitle.setTypeface(null, Typeface.NORMAL);
            viewHolder.mTextViewTitle.setPaintFlags(viewHolder.mTextViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            resetListElementStyle(viewHolder);
        }

        viewHolder.itemView.invalidate();
    }

    @Override
    public int getItemViewType(int position) {

        if (isItemFirstOfGroup(position)) {
            return TYPE_ITEM_WITH_HEADER;
        } else {
            return TYPE_SIMPLE_ITEM;
        }
    }

    private boolean isItemFirstOfGroup(int position) {

        // Edge case - the first item always needs a header:
        if (position == 0) {
            return true;
        }

        // Case where the current step belongs in a different Step Group than
        // the previous one:
        if (!stepsList.get(position).getGroup().equals(stepsList.get(position - 1).getGroup())) {
            return true;
        }

        // Else, it's just a common item:
        return false;
    }

    private void resetListElementStyle(SimpleItemViewHolder viewHolder) {
        viewHolder.mCheckBox.setChecked(false);
        viewHolder.mTextViewTitle.setPaintFlags(viewHolder.mTextViewTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

    public Step getItem(int position) {
        return stepsList.get(position);
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }
}
