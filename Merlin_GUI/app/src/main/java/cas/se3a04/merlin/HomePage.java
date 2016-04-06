package cas.se3a04.merlin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.Menu;

/**
 * Created by Stephan on 2016-04-05.
 */
public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_gui);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate actionbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}
