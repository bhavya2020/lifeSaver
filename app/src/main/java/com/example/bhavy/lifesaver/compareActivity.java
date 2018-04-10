package com.example.bhavy.lifesaver;

        import android.app.ActionBar;
        import android.content.ContentResolver;
        import android.content.ContentValues;
        import android.database.CharArrayBuffer;
        import android.database.ContentObserver;
        import android.database.Cursor;
        import android.database.DataSetObserver;
        import android.database.sqlite.SQLiteDatabase;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.PersistableBundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.helper.ItemTouchHelper;
        import android.util.Log;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.bhavy.lifesaver.data.waterContract;
        import com.example.bhavy.lifesaver.data.waterDbHelper;
        import com.example.bhavy.lifesaver.utilities.preferenceUtilities;



/**
 * Created by bhavy on 06-08-2017.
 */

public class compareActivity extends AppCompatActivity {

   private  compareAdapter mAdapter;
    private  SQLiteDatabase mDb;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        RecyclerView waitlistRecyclerView;
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        waterDbHelper dbHelper = new waterDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor =getAllGuests();
        mAdapter = new compareAdapter(this, cursor);
        waitlistRecyclerView.setAdapter(mAdapter);

       /* new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
               delete2(id);
                mAdapter.swapCursor(getAllGuests());
            }
        }).attachToRecyclerView(waitlistRecyclerView);*/


    }
    public  Cursor getAllGuests() {
        return mDb.query(
                waterContract.WaterDay.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                waterContract.WaterDay.COLUMN_TIMESTAMP
        );
    }
    public boolean delete2(long id) {
        String id1=String.valueOf(id);

        return mDb.delete(waterContract.WaterDay.TABLE_NAME, waterContract.WaterDay.ID +" = ?" , new String[] {id1}) > 0;
    }

    public void delete1(View view) {

        delete();

        mAdapter.swapCursor(getAllGuests());

    }

   public boolean delete() {
        return mDb.delete(waterContract.WaterDay.TABLE_NAME, waterContract.WaterDay.ID , null) > 0;
    }


}

