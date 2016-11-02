package com.badoo.test.transactionviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.badoo.test.transactionviewer.R;
import com.badoo.test.transactionviewer.models.Transaction;
import com.badoo.test.transactionviewer.utils.Util;

import java.util.List;

/**
 * Created by yaozhong on 02/11/2016.
 */

public class TransactionsDetailsLvAdapter extends ArrayAdapter<Transaction> {
    private static class ViewHolder {
        TextView amount;
        TextView amount_gbp;
    }

    public TransactionsDetailsLvAdapter(Context context, List<Transaction> transactions) {
        super(context, R.layout.item_transaction_details, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Transaction transaction = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_transaction_details, parent, false);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.tv_amount);
            viewHolder.amount_gbp = (TextView) convertView.findViewById(R.id.tv_amount_gbp);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // set transaction details
        viewHolder.amount.setText(Util.getCurrencySymbol(transaction.getCurrency()) + Util.round(transaction.getAmount(), 2));
        viewHolder.amount_gbp.setText("£" + Util.round(transaction.getAmountInGbp(), 2));
        return convertView;
    }

}
