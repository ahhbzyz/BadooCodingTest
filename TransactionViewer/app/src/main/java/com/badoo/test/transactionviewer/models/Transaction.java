package com.badoo.test.transactionviewer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yaozhong on 02/11/2016.
 */

public class Transaction implements Parcelable{
    private float amount;
    private String sku;
    private String currency;
    private float amountInGbp;

    public Transaction(float amount, String sku, String currency, float amountInGbp) {
        this.amount = amount;
        this.sku = sku;
        this.currency = currency;
        this.amountInGbp = amountInGbp;
    }

    public float getAmount() {
        return amount;
    }

    public String getSku() {
        return sku;
    }

    public String getCurrency() {
        return currency;
    }

    public float getAmountInGbp() {
        return amountInGbp;
    }

    // Parcelling part
    public Transaction(Parcel in){
        this.amount = in.readFloat();
        this.sku = in.readString();
        this.currency = in.readString();
        this.amountInGbp = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(amount);
        parcel.writeString(sku);
        parcel.writeString(currency);
        parcel.writeFloat(amountInGbp);
    }
    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction [] newArray(int size) {
            return new Transaction[size];
        }
    };

}
