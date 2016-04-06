package cas.se3a04.merlin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cas.se3a04.merlin.input.TempoInput;


public class LaunchScreen extends AppCompatActivity {


    private Button mButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        mButton = (Button) findViewById(R.id.goStart);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), TempoInput.class);
                startActivity(i);
            }
        });
    }


}
