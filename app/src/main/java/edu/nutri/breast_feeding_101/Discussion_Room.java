package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import edu.nutri.breast_feeding_101.adapter.CircleTransform;

/**
 * Created by Akano on 2/16/2017.
 */

public class Discussion_Room extends Activity {
InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion_room);

        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                // Check the LogCat to get your test device ID
                .addTestDevice("7D8AB0062B2FC24E9C68BFC765C104C7")
                .build();
//
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
//                showInterstitial();
            }

            @Override
            public void onAdClosed() {
//                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();

                if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mInterstitialAd.loadAd(adRequest);
                }

                Intent it = new Intent(getApplicationContext(), Discussions.class);
                it.putExtra("discussion_name", "within_6");
                startActivity(it);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
//                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
//                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(getApplicationContext(), Discussions.class);
                it.putExtra("discussion_name", "within_6");
                startActivity(it);
            }

            @Override
            public void onAdOpened() {
//                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
//            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
//
//            if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
//                AdRequest adRequest = new AdRequest.Builder().build();
//                mInterstitialAd.loadAd(adRequest);
//            }

            Intent it = new Intent(this, Discussions.class);
            it.putExtra("discussion_name", "within_6");
            startActivity(it);
        }
    }

    public void before_6(View v){
//        showInterstitial();
        Intent it = new Intent(this, Discussions.class);
        it.putExtra("discussion_name", "within_6");
        startActivity(it);
    }

    public void after_6(View v){
        Intent it = new Intent(this, Discussions.class);
        it.putExtra("discussion_name", "after_6");
        startActivity(it);
    }

    public void complementary(View v){
        Intent it = new Intent(this, Discussions.class);
        it.putExtra("discussion_name", "complementary");
        startActivity(it);
    }

    public void others(View v){
        Intent it = new Intent(this, Discussions.class);
        it.putExtra("discussion_name", "Others");
        startActivity(it);
    }

//    private void showInterstitial() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
//    }
}
