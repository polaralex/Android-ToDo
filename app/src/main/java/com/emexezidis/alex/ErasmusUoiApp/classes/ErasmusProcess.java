package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ErasmusProcess implements Parcelable {

    private ArrayList<Step> mSteps;

    public ErasmusProcess(ArrayList<Step> steps) {
        this.mSteps = steps;
    }

    public ErasmusProcess() {
    }

    protected ErasmusProcess(Parcel in) {
        mSteps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<ErasmusProcess> CREATOR = new Creator<ErasmusProcess>() {
        @Override
        public ErasmusProcess createFromParcel(Parcel in) {
            return new ErasmusProcess(in);
        }

        @Override
        public ErasmusProcess[] newArray(int size) {
            return new ErasmusProcess[size];
        }
    };

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public Step getStep(int position) {

        if (position < mSteps.size()) {
            return mSteps.get(position);
        } else {
            return null;
        }

    }

    public void setExactStep(Step step, int position) {
        mSteps.set(position, step);
    }

    public int getNumberOfSteps() {
        return mSteps.size();
    }

    public void attachStepsList(ArrayList<Step> stepsList) {
        mSteps = stepsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mSteps);
    }
}
