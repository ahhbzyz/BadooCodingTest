package com.badoo.test.transactionviewer.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.badoo.test.transactionviewer.R;
import com.badoo.test.transactionviewer.algorithm.BFS;
import com.badoo.test.transactionviewer.algorithm.Graph;
import com.badoo.test.transactionviewer.models.Transaction;
import com.badoo.test.transactionviewer.models.Transactions;
import com.badoo.test.transactionviewer.adapters.TransactionsRecordLvAdapter;
import com.badoo.test.transactionviewer.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progress;
    final ArrayList<Transactions> transactionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");

        // Initial progress dialog
        progress = new ProgressDialog(this);
        progress.setMessage("Calculating transactions...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        // Do in background
        new CalculateTransactions().execute();
    }

    private class CalculateTransactions extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            Graph graph = new Graph();

            try {
                /** Create currency exchange graph **/
                String ratesJSON = Util.LoadJsonFile("rates.json", getApplicationContext());
                if (ratesJSON == null) {
                    return "rates.json is empty!";
                }
                JSONArray ratesArray = new JSONArray(ratesJSON);
                for (int i = 0; i < ratesArray.length(); i++) {
                    String from = ratesArray.getJSONObject(i).getString("from");
                    String to = ratesArray.getJSONObject(i).getString("to");
                    float rate = (float)ratesArray.getJSONObject(i).getDouble("rate");
                    graph.addEdge(from, to, rate);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "JSON Format Error in rates.json!";
            }

            try {
                /** Extract all the transaction into a map **/
                Map<String, List<Transaction>> transMap = new TreeMap<>();
                String transJSON = Util.LoadJsonFile("transactions.json", getApplicationContext());
                if (transJSON == null) {
                    return "transactions.json is empty!";
                }
                JSONArray transArray = new JSONArray(transJSON);

                for (int i = 0; i < transArray.length(); i++) {
                    // Transaction Object
                    String sku = transArray.getJSONObject(i).getString("sku");
                    String currency = transArray.getJSONObject(i).getString("currency");
                    float amount = (float)transArray.getJSONObject(i).getDouble("amount");
                    float amountInGBP = amount * BFS.calculateRate(graph, currency, "GBP");
                    Transaction transaction = new Transaction(amount, sku, currency, amountInGBP);

                    // Update transaction map
                    List<Transaction> list = transMap.containsKey(sku)? transMap.get(sku) : new ArrayList<Transaction>();
                    list.add(transaction);
                    transMap.put(sku, list);
                }

                //Put data into a list for showing on listview
                for (Map.Entry<String, List<Transaction>> entry : transMap.entrySet()) {
                    float totalAmount = 0;
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        totalAmount += entry.getValue().get(i).getAmountInGbp();
                    }
                    transactionsList.add(new Transactions(entry.getKey(), entry.getValue(), totalAmount));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "JSON Format Error in transaction.json!";
            }

            return "Calculate transactions successfully!";
        }

        @Override
        protected void onPostExecute(String result) {
	    // Show calculating result	
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
            toast.show();

            // finishing calculation
            progress.dismiss();
            // Update listview and Set Adapter
            TransactionsRecordLvAdapter adapter = new TransactionsRecordLvAdapter(getApplicationContext(), transactionsList);
            ListView transactionsLv = (ListView)findViewById(R.id.lv_transactions);
            transactionsLv.setAdapter(adapter);

            transactionsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), TransactionsDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putFloat("totalAmount", transactionsList.get(i).getTotalAmount());
                    bundle.putString("transactionSku", transactionsList.get(i).getName());
                    bundle.putParcelableArrayList("transactions", (ArrayList<Transaction>)transactionsList.get(i).getTransactionsList());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        protected void onPreExecute() {
            progress.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
