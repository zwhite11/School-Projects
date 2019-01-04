package white.zac.hw2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayFragment extends Fragment {

    private TextView first_name;
    private TextView last_name;
    private TextView home_phone;
    private TextView work_phone;
    private TextView mobile_phone;
    private TextView email;
    private long id; // NEW: hold the id of the todoItem being edited

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            id = savedInstanceState.getLong("id", -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        // find the fields for the data
        first_name = (TextView) view.findViewById(R.id.first_name);
        last_name = (TextView) view.findViewById(R.id.last_name);
        home_phone = (TextView) view.findViewById(R.id.home_phone);
        work_phone = (TextView) view.findViewById(R.id.work_phone);
        mobile_phone = (TextView) view.findViewById(R.id.mobile_phone);
        email = (TextView) view.findViewById(R.id.email);

        if (savedInstanceState != null) {
            setContactId(savedInstanceState.getLong("id", -1L));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("id", id);
    }


    public void setContactId(long id) {
        this.id = id;
        if (id == -1) {
            first_name.setText("");
            last_name.setText("");
            home_phone.setText("");
            work_phone.setText("");
            mobile_phone.setText("");
            email.setText("");
        } else {
            Contact item = Util.findContact(getContext(), id);
            if (item == null) {
                first_name.setText("");
                last_name.setText("");
                home_phone.setText("");
                work_phone.setText("");
                mobile_phone.setText("");
                email.setText("");
                this.id = -1;
            } else {
                first_name.setText(item.getFirstName());
                last_name.setText(item.getLastName());
                home_phone.setText(item.gethPhone());
                work_phone.setText(item.getwPhone());
                mobile_phone.setText(item.getmPhone());
                email.setText(item.getEmailAddress());

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_display, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_email:
                onDisplayFragmentListener.onDisplayFragmentEmailSelected(id);
                return true;
            case R.id.action_edit:
                onDisplayFragmentListener.onDisplayFragmentEditSelected(id);
                return true;
            case R.id.action_about:
                onDisplayFragmentListener.onDisplayFragmentAboutSelected(id);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onDisplayFragmentListener = (OnDisplayFragmentListener) context;
    }

    @Override
    public void onDetach() {
        onDisplayFragmentListener = null;
        super.onDetach();
    }

    private OnDisplayFragmentListener onDisplayFragmentListener;

    public interface OnDisplayFragmentListener {
        void onDisplayFragmentEmailSelected(long id);
        void onDisplayFragmentEditSelected(long id);
        void onDisplayFragmentAboutSelected(long id);
    }

}
