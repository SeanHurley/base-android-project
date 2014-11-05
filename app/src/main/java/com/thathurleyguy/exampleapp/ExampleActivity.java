package com.thathurleyguy.exampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.thathurleyguy.exampleapp.models.Repo;
import com.thathurleyguy.exampleapp.services.GithubService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


public class ExampleActivity extends Activity {
    @InjectView(R.id.txt_title) TextView buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.inject(this);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();

        GithubService service = restAdapter.create(GithubService.class);
        service.listRepos("0")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ExampleActivity.this, "Uh oh something went wrong :(", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        Toast.makeText(ExampleActivity.this, "Found " + repos.size() + " repos!", Toast.LENGTH_LONG).show();
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
