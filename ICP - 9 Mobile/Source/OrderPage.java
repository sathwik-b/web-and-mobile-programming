package com.example.pizzaapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import org.apache.commons.*;

import java.util.ArrayList;
import java.util.List;
public class OrderPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final Integer PIZZA_PRICE = 9;
    private static final Integer CHICKEN_PRICE = 4;
    private static final Integer VEGGIE_PRICE = 3;
    private static final Integer PEPPERONI_PRICE = 5;
    private static final Integer OP_PRICE = 2;
    float totalPrice;
    Integer quantity = 1;
    String orderSummary;

    EditText userNameText;
    TextView quantityTextView;
    CheckBox chickenChecked, veggieChecked, pepperoniChecked, opChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        quantityTextView = findViewById(R.id.quantity);
        userNameText = findViewById(R.id.user_input);
        chickenChecked = findViewById(R.id.chic_checkBox);
        veggieChecked = findViewById(R.id.veg_checkBox);
        pepperoniChecked = findViewById(R.id.pep_checkBox);
        opChecked = findViewById(R.id.other_checkBox);
        // Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

       // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Fries");
        categories.add("Coke");
        categories.add("fries and Coke");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //spinner.setAdapter(dataAdapter);
        Button detailsBtn = findViewById(R.id.orderinfo);
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                orderSummary(view);
            }
        });

        Button placeOrderBtn = findViewById(R.id.placeorder);
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                orderPizzaMain(view);
            }
        });
    }


    private boolean isUserEmpty(){
        String userName = userNameText.getText().toString();
        if(userName == null || userName.isEmpty()){
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.userNull);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return true;
        }
        return false;
    }


    private String fetchDetails() {
        boolean chicken = chickenChecked.isChecked();
        boolean veggie = veggieChecked.isChecked();
        boolean pepperoni = pepperoniChecked.isChecked();
        boolean op = opChecked.isChecked();

        totalPrice = calculatePrice(chicken, veggie, pepperoni, op, quantity);
        return fetchOrderSummary(userNameText.getText().toString(), chicken, veggie, pepperoni, op, totalPrice);
    }

    public void orderSummary(View view) {
        if (!isUserEmpty()) {
            orderSummary = fetchDetails();
            Intent intent = new Intent(OrderPage.this, Ordered_details.class);
            intent.putExtra("orderSummary", orderSummary);
            startActivity(intent);
        }
    }

    public void orderPizzaMain(View view) {
        if (!isUserEmpty()) {
            orderSummary = fetchDetails();
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"PizzaOrder@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
            emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
            startActivity(Intent.createChooser(emailIntent, ""));
        }
    }



    private String fetchOrderSummary(String Name, boolean chicken, boolean veggie,
                                     boolean pepperoni, boolean op, float totalPrice) {
        return getString(R.string.order_summary_name,Name) +"\n"+
                getString(R.string.order_summary_chicken,chicken? "Yes": "No")+"\n"+
                getString(R.string.order_summary_veggie,veggie? "Yes": "No") +"\n"+
                getString(R.string.order_summary_pepperoni,pepperoni ?  "Yes": "No") +"\n"+
                getString(R.string.order_summary_op,op?  "Yes": "No") +"\n"+
                getString(R.string.order_summary_quantity,quantity)+"\n"+
                getString(R.string.order_summary_total_price,totalPrice) +"\n"+
                getString(R.string.thank_you);
    }

    private float calculatePrice(boolean chicken, boolean veggie, boolean pepperoni, boolean op, Integer quantity) {
        int basePrice = PIZZA_PRICE;
        if (chicken) {
            basePrice += CHICKEN_PRICE;
        }
        if (veggie) {
            basePrice += VEGGIE_PRICE;
        }
        if (pepperoni){
            basePrice +=PEPPERONI_PRICE;
        }
        if(op){
            basePrice +=OP_PRICE;
        }
        return quantity * basePrice;
    }

    public void increment(View view) {
        if (quantity < 20) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("PizzaOrder", "Please select less than 20 Pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = "Please select less than 20 Pizzas";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }


    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("pizzaOrder", "Please select atleast one Pizza");
            Context context = getApplicationContext();
            String upperLimitToast = "Please select atleast one Pizza";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }

    private void display(int number) {
        quantityTextView.setText("" + number);
    }

    public void callStore(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:2244466666"));
        startActivity(Intent.createChooser(callIntent, ""));
    }
    @Override


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}