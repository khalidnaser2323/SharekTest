package com.example.khalid.sharektest;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity implements View.OnClickListener, SlidingDrawer.OnDrawerOpenListener, SlidingDrawer.OnDrawerCloseListener {
    SlidingDrawer slidingDrawer ;
    Button handleButton, firstChoiceButton, secondChoiceButton, thirdChoiceButton, fourthChoiceButton,sendmes;
    EditText name,phone,email,message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer ) ;

        slidingDrawer .setOnDrawerOpenListener(this);
        slidingDrawer .setOnDrawerCloseListener(this);

        handleButton = (Button) findViewById(R.id.cous_handle_button ) ;
        firstChoiceButton = (Button ) findViewById(R.id.cous_slidingChoice1_Button ) ;
        secondChoiceButton = (Button ) findViewById(R.id.cous_slidingChoice2_Button ) ;
        thirdChoiceButton = (Button ) findViewById(R.id.cous_slidingChoice3_Button ) ;
        fourthChoiceButton = (Button ) findViewById(R.id.cous_slidingChoice4_Button ) ;
        sendmes=(Button)findViewById(R.id.cous_send_button);
        sendmes.setOnClickListener(this);

        firstChoiceButton.setOnClickListener(this);
        secondChoiceButton.setOnClickListener(this);
        thirdChoiceButton.setOnClickListener(this);
        fourthChoiceButton.setOnClickListener(this);
        name=(EditText)findViewById(R.id.Cous_name_editText);
        message=(EditText)findViewById(R.id.cous_mes_editText);
        email=(EditText)findViewById(R.id.cous_email_editText);
        phone=(EditText)findViewById(R.id.cous_phone_edittext);

    }

    @Override
    public void onClick(View view) {
        if(view == firstChoiceButton ){
            Intent Call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01009936942"));
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(Call);

        }

        else if(view == secondChoiceButton ){ Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "01093402662"));
            intent.putExtra("sms_body", "Write your SMS");
            startActivity(intent);

        }

        else if(view == thirdChoiceButton ){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://www.facebook.com/Forca.Barcelona/"));
            startActivity(intent);
        }

        else if(view == fourthChoiceButton ){ Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://twitter.com/FCBarcelona"));
            startActivity(intent);

        } else if (view == sendmes) {
//            String n = name.getText().toString();
//            String p=phone.getText().toString();
//            String em=email.getText().toString();
//            String mas=message.getText().toString();
//            String ex="Name : "+n +
//                    "Phone : "+p +
//                    "Email : " + em+
//                    "Message : "+mas ;
            Toast.makeText(this, "message is sent", Toast.LENGTH_LONG).show();

        }
    }



    @Override
    public void onDrawerClosed() {
        handleButton .setText("click or slide up for options");
    }

    @Override
    public void onDrawerOpened() {
        handleButton .setText("click or slide down to hide");
    }




}
