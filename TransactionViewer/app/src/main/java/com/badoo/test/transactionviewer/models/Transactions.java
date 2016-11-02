package com.badoo.test.transactionviewer.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaozhong on 02/11/2016.
 */

public class Transactions {

    private List<Transaction> transactionsList = new ArrayList<>();
    private String name;
    private float totalAmount;

    public Transactions(String name, List<Transaction> transactionsList, float totalAmount) {
        this.transactionsList = transactionsList;
        this.name = name;
        this.totalAmount = totalAmount;
    }

    public List<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public String getName() {
        return name;
    }

    public float getTotalAmount() {
        return totalAmount;
    }
}
