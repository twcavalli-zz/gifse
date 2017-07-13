package gifse.thomas.com.br.gifse.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import gifse.thomas.com.br.gifse.R;
import gifse.thomas.com.br.gifse.data.dao.GifDao;
import io.realm.Realm;

public class RandomActivity extends BaseActivity {
    private Realm realm;
    private ImageView img_random_gif;
    private FloatingActionButton fab_update_Random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        realm = Realm.getDefaultInstance();
        img_random_gif = (ImageView) findViewById(R.id.img_random_gif);
        fab_update_Random = (FloatingActionButton) findViewById(R.id.fab_update_Random);

        fab_update_Random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        update();
        setupToolbar();
    }

    private void update() {
        GifDao dao = new GifDao(realm);
        String sRandom = dao.getRandomGif();

        if (sRandom != null) {
            Glide.with(this)
                    .load(sRandom)
                    .into(img_random_gif);
        } else {
            Toast.makeText(RandomActivity.this, "No downloaded gifs.", Toast.LENGTH_LONG).show();
        }
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        //Enable back to main activity
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(getString(R.string.see_random));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.setResult(Activity.RESULT_CANCELED, new Intent());
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
