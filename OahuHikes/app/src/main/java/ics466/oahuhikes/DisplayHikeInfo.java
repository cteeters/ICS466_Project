package ics466.oahuhikes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;

public class DisplayHikeInfo extends AppCompatActivity
{
    private TextView textName, textLength, textDiff, directions;
    private RatingBar ratingBar;
    SQLiteHelper myDB;
    Cursor results;
    String hikeName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hike_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDB = new SQLiteHelper(this);
        textName = (TextView) findViewById(R.id.textName);
        textLength = (TextView) findViewById(R.id.textLength);
        textDiff = (TextView) findViewById(R.id.textDiff);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        directions = (TextView) findViewById(R.id.directions);

        Object extra = getIntent().getExtras().get("name");
        hikeName = extra.toString();
        results = myDB.search(hikeName, "NAME");

        results.moveToNext();
        textName.setText(results.getString(0));
        textLength.setText("Length: " + results.getString(1));
        textDiff.setText("Difficulty: " + results.getString(2));
        ratingBar.setRating(results.getFloat(3));

//        Directions test
        directions.setClickable(true);
        directions.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://www.google.com/maps/place/Koko+Crater+Arch,+8483+HI-72,+Honolulu,+HI+96825/@21.2815724,-157.690732,15z/data=!3m1!4b1!4m2!3m1!1s0x7c001216253edcf7:0x9ef1880d4efab896'> Google Maps </a>";
        directions.setText(Html.fromHtml(text));
    }

}
