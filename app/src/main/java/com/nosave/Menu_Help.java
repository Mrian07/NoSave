package com.nosave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.util.Objects;

public class Menu_Help extends AppCompatActivity {

    //In-App Review Declaration
    ReviewInfo reviewInfo;
    private ReviewManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__help);
        Objects.requireNonNull(getSupportActionBar()).setTitle("About");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.action_bar_bg));

        //Call Method!
        initReviewInfo();

        findViewById(R.id.feedback_email).setOnClickListener(v -> feedback_email());
        findViewById(R.id.Share_Me).setOnClickListener(v -> shareMe());

        //Credits
        TextView versionName = findViewById(R.id.app_version);
        versionName.setText(String.format("Version %s", BuildConfig.VERSION_NAME));
    }

    private void feedback_email() {

        Toast.makeText(Objects.requireNonNull(getApplicationContext()).getApplicationContext(), "Thankyou for your feedback!", Toast.LENGTH_SHORT).show();

        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:codeguy0098@gmail.com" +
                "?subject=NoSave Feedback"));
        email.putExtra(Intent.EXTRA_SUBJECT, "NoSave Feedback");
        startActivity(email);
    }

    private void shareMe() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String url = "https://play.google.com/store/apps/details?id=com.nosave";
        String shareBody = "NoSave is a small tool use to message any WhatsApp" +
                " or WhatsApp Business user without saving his/her into your phone-book.\n\n" + "Here is the link:\n" + url;
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "NoSave");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void initReviewInfo() {
        //In-App Review
        manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (reviewInfo != null) {
            Task<Void> flow = manager.launchReviewFlow(Menu_Help.this, reviewInfo);
            flow.addOnCompleteListener(task -> {
                // The flow has finished. The API does not indicate whether the user
                // reviewed or not, or even whether the review dialog was shown. Thus, no
                // matter the result, we continue our app flow.
            });
        }
        super.onBackPressed();
    }
}