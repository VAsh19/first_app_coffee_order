/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       // displayPrice( quantity*5);

        EditText nameField =  (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String orderSummary = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For Coffee" + name);
        intent.putExtra(Intent.EXTRA_TEXT,orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice = 5;

        if(addWhippedCream)
        {
            basePrice+=1;
        }

        if(addChocolate)
        {
            basePrice+=2;
        }
        return basePrice*quantity;
    }

    private String createOrderSummary(String name,int price, boolean addWhippedCream, boolean addchocolate){
        String summary = "name = "+ name+"\n"+"add Whipped Cream? "+ addWhippedCream +"\nadd Chocolate? "+ addchocolate+"\nQuantity : "+ quantity+"\nTotal: "+"$"+price+ "\n" +"Thank You!";
        return summary;
    }

    public void increment(View view){
        if(quantity==100)
        {
            Toast.makeText(this,"You cannot Have More Than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity +1;
        display(quantity);
    }

    public void decrement(View view){
        if(quantity==0)
        {
            Toast.makeText(getApplicationContext(),"You cannot Have less than 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1 ;
        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    /**
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    */
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}