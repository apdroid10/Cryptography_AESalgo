package com.apuroopa.Cipher_AESdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "SymmetricAlgorithmAES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Original text
        String theTestText = "This is just a simple test";
        TextView tvorig = (TextView)findViewById(R.id.tvorig);
        tvorig.setText("\n[ORIGINAL]:\n" + theTestText + "\n");

        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }

        // Encode the original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(theTestText.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
        }
        TextView tvencoded = (TextView)findViewById(R.id.tvencoded);
        tvencoded.setText("[ENCODED]:\n" +
                Base64.encodeToString(encodedBytes, Base64.DEFAULT) + "\n");

        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e(TAG, "AES decryption error");
        }
        TextView tvdecoded = (TextView)findViewById(R.id.tvdecoded);
        tvdecoded.setText("[DECODED]:\n" + new String(decodedBytes) + "\n");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
