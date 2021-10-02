package com.project.kda.activities.intro_activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.kda.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Donate extends Fragment implements PaymentResultListener {

    private Button startpayment;
    private EditText orderamount;
    private String TAG ="main";
    Button one,two,three;

    public Donate() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);
        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        orderamount = view.findViewById(R.id.orderamount);
        startpayment = view.findViewById(R.id.donate);


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(orderamount.getText().toString());
                int newamount = amount + 100;
                orderamount.setText(newamount+"");

            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(orderamount.getText().toString());
                int newamount = amount + 200;
                orderamount.setText(newamount+"");

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(orderamount.getText().toString());
                int newamount = amount + 300;
                orderamount.setText(newamount+"");

            }
        });


        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderamount.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Amount is empty", Toast.LENGTH_LONG).show();
                }else {
                    startPayment();
                }
            }
        });

        return view;
    }


    public void startPayment() {
        Checkout checkout =new Checkout();
        checkout.setKeyID("rzp_test_FzdXdG2D1Vm0xH");

        final Donate donate = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "KDA Mumbai");
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            String payment = orderamount.getText().toString();
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "viveklimbad1010@gmail.com");
            preFill.put("contact", "9769107288");
            options.put("prefill", preFill);
            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        // payment successfull pay_DGU19rDsInjcF2
        Log.e(TAG, " Payment Successfull "+ s.toString());
        Toast.makeText(getActivity(), "Payment Successfully done! " +s, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG,  "Error code "+String.valueOf(i)+" -- Payment Failed "+s.toString()  );
        try {
            Toast.makeText(getActivity(), "Payment Error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("On PaymentError", "Exception in onPaymentError", e);
        }

    }
}
