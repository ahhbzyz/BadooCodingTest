package com.badoo.test.transactionviewer.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.badoo.test.transactionviewer.R;
import com.badoo.test.transactionviewer.adapters.TransactionsDetailsLvAdapter;
import com.badoo.test.transactionviewer.models.Transaction;
import com.badoo.test.transactionviewer.utils.Util;

import java.util.List;

public class TransactionsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_details);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Set toolbar title
        String transactionSku = bundle.getString("transactionSku");
        getSupportActionBar().setTitle("Transactions for " + transactionSku);

        // Set total value
        float totalAmount = bundle.getFloat("totalAmount");
        TextView textView = (TextView)findViewById(R.id.tv_total);
        textView.setText("Total: £" + Util.round(totalAmount, 2));

        // Get transaction list
        List<Transaction> transactions = bundle.getParcelableArrayList("transactions");

        // Update listview and set adapter
        TransactionsDetailsLvAdapter adapter = new TransactionsDetailsLvAdapter(this, transactions);
        ListView transactionsLv = (ListView)findViewById(R.id.lv_transactions_details);
        transactionsLv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
