package cas.se3a04.merlin.input;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import cas.se3a04.merlin.HomePage;
import cas.se3a04.merlin.R;

/**
 * Created by Stephan on 2016-04-03.
 */
public class ArtistInput extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_gui);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                //Return to home page with any data that has been entered
                Intent returnToHome = new Intent(ArtistInput.this, HomePage.class);
                startActivity(returnToHome);
                return true;
            case R.id.done:
                //Navigate to home page with data (last page)
                Intent finishToHome = new Intent(ArtistInput.this, HomePage.class);
                startActivity(finishToHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
