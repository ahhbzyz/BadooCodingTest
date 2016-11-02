package com.badoo.test.transactionviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.badoo.test.transactionviewer.R;
import com.badoo.test.transactionviewer.models.Transactions;

import java.util.List;

/**
 * Created by yaozhong on 02/11/2016.
 */

public class TransactionsRecordLvAdapter extends ArrayAdapter<Transactions> {

    private static class ViewHolder {
        TextView sku;
        TextView count;
    }

    public TransactionsRecordLvAdapter(Context context, List<Transactions> transactionsList) {
        super(context, R.layout.item_transactions_record, transactionsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Transactions transactions = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_transactions_record, parent, false);
            viewHolder.sku = (TextView) convertView.findViewById(R.id.tv_sku);
            viewHolder.count = (TextView) convertView.findViewById(R.id.tv_count);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set transaction record
        viewHolder.sku.setText(transactions.getName());
        viewHolder.count.setText(transactions.getTransactionsList().size() + " transactions");
        return convertView;
    }
}
