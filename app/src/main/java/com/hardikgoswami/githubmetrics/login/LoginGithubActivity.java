package com.hardikgoswami.githubmetrics.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hardikgoswami.githubmetrics.BuildConfig;
import com.hardikgoswami.githubmetrics.R;
import com.hardikgoswami.oauthLibGithub.GithubOauth;

public class LoginGithubActivity extends AppCompatActivity {

    public static final String GIT_CLIENT = BuildConfig.GITHUB_CLIENT;
    public static final String GIT_SECRET = BuildConfig.GITHUB_SECRET;
    Button loginBtn;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_github);
        loginBtn =(Button)findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GithubOauth
                        .Builder()
                        .withClientId(GIT_CLIENT)
                        .withClientSecret(GIT_SECRET)
                        .withContext(mContext)
                        .packageName("com.hardikgoswami.githubmetrics")
                        .nextActivity("com.hardikgoswami.githubmetrics.HomeActivity")
                        .debug(true)
                        .execute();

            }
        });
    }
}