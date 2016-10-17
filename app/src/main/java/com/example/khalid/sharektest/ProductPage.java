package com.example.khalid.sharektest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductPage extends AppCompatActivity implements View.OnClickListener{
    TextView Pname,Pprice,Pperiod,Ploction,Pdescription,Pgender;
    ImageView Ppic;
    Button conatct,showmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Pdescription=(TextView)findViewById(R.id.textView);
        Pname=(TextView)findViewById(R.id.product_productname_textView);
        Pprice=(TextView)findViewById(R.id.product_price_textView);
        Ploction=(TextView)findViewById(R.id.product_loc_textView);
        Pperiod=(TextView)findViewById(R.id.product_period_textView);
        Pgender=(TextView)findViewById(R.id.product_gender_textView);
        Ppic=(ImageView)findViewById(R.id.product_productpic_imageView);
        conatct=(Button)findViewById(R.id.product_contact_button);
        showmap=(Button)findViewById(R.id.product_showmap_button);
        conatct.setOnClickListener(this);
        showmap.setOnClickListener(this);
        Pdescription.setMovementMethod(new ScrollingMovementMethod());





    }

    @Override
    public void onClick(View v) {if (v==conatct){
        Toast.makeText(this, "اتصل يا اسطي ", Toast.LENGTH_LONG).show();}
        if (v==showmap){
            Snackbar.make(v, "ازيك يا اسطي ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();}

    }
}
