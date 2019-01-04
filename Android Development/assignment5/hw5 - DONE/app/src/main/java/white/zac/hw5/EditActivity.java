package white.zac.hw5;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import white.zac.hw5.databinding.ContentEditBinding;
import white.zac.hw5.databinding.ActivityEditBinding;

// an activty to edit a todo item
public class EditActivity extends AppCompatActivity {

    private TodoItem item;

    ActivityEditBinding binding;

    // set up the user interface when the activity has been created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the id of the item to edit (or -1 if not set)
        long itemId = getIntent().getLongExtra("itemId", -1L);

        // if there's an id, lookup the item
        if (itemId != -1) {
            item = Util.findTodo(this, itemId);

        // if no id, create a new item
        } else {
            item = new TodoItem();
        }

        //set binding item
        binding.content.setItem(item);

    }

    // set up the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    // handle action bar items pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.done:
                binding.content.getItem().status.set("Done");

                //update database
                Util.updateTodo(this, binding.content.getItem());

                updateNotification();
                finish();
                return true;

            case R.id.snooze:
                long dueTime = this.item.dueTime.get() + 10000;
                binding.content.getItem().dueTime.set(dueTime);

                //update database
                Util.updateTodo(this, binding.content.getItem());

                updateNotification();
                finish();
                return true;

            // if "save" was pressed, save the data in a new item and return it
            case R.id.save:
                // update the item in the database
                Util.updateTodo(this, binding.content.getItem());

                finish();
                return true;

            // if "cancel" was pressed, just return "canceled" without an item
            case R.id.cancel:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateNotification() {
        //update notification
        if(Util.getDueItems(this).isEmpty()){
            Util.cancelNotification(this);
        }
        else{
            Intent updateNotification = new Intent(this, AlarmReceiver.class);
            sendBroadcast(updateNotification);
        }
    }
}
