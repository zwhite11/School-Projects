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
import android.widget.EditText;

public class EditFragment extends Fragment {
	private EditText firstName;
	private EditText lastName;
	private EditText hPhone;
	private EditText wPhone;
	private EditText mPhone;
	private EditText email;

	private long id; // NEW: hold the id of the todoItem being edited

	private OnEditFragmentListener onEditFragmentListener;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState != null)
			id = savedInstanceState.getLong("id", -1);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit, container, false);
		// find the fields for the data
		firstName = (EditText) view.findViewById(R.id.first_name);
		lastName = (EditText) view.findViewById(R.id.last_name);
		hPhone = (EditText) view.findViewById(R.id.home_phone);
		wPhone = (EditText) view.findViewById(R.id.work_phone);
		mPhone = (EditText) view.findViewById(R.id.mobile_phone);
		email = (EditText) view.findViewById(R.id.email);

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
			firstName.setText("");
			lastName.setText("");
			hPhone.setText("");
			wPhone.setText("");
			mPhone.setText("");
			email.setText("");

		} else {
			Contact item = Util.findContact(getContext(), id);
			if (item == null) {
				firstName.setText("");
				lastName.setText("");
				hPhone.setText("");
				wPhone.setText("");
				mPhone.setText("");
				email.setText("");
				this.id = -1;
			} else {
				firstName.setText(item.getFirstName());
				lastName.setText(item.getLastName());
				hPhone.setText(item.gethPhone());
				wPhone.setText(item.getwPhone());
				mPhone.setText(item.getmPhone());
				email.setText(item.getEmailAddress());

			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_edit, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_done:
				saveData();
				if (onEditFragmentListener != null)
					onEditFragmentListener.onEditFragmentDone(id);
				return true;
			case R.id.action_cancel:
				if (onEditFragmentListener != null)
					onEditFragmentListener.onEditFragmentCancel(id);
				return true;
			case R.id.action_reload:
				if (onEditFragmentListener != null)
					onEditFragmentListener.onEditFragmentReload(id);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	private void saveData() {
		// when the user presses "back", we use that as "save" for now
		//   (we'll replace this with ActionBar buttons later)
		// create a to-do contact that we'll return
		Contact contact = new Contact();
		contact.setId(id); // NEW: store the id of the contact so we can look it up in the list adapter
		contact.setFirstName(firstName.getText().toString());
		contact.setLastName(lastName.getText().toString());
		contact.sethPhone(hPhone.getText().toString());
		contact.setwPhone(wPhone.getText().toString());
		contact.setmPhone(mPhone.getText().toString());
		contact.setEmailAddress(email.getText().toString());

		Util.updateContact(getContext(), contact);
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (!(context instanceof OnEditFragmentListener))
			throw new IllegalStateException("Activities using EditFragment must implement EditFragment.OnEditFragmentListener");
		onEditFragmentListener = (OnEditFragmentListener) context;
	}

	@Override
	public void onDetach() {
		onEditFragmentListener = null;
		super.onDetach();
	}

	public interface OnEditFragmentListener {
		void onEditFragmentDone(long id);
		void onEditFragmentCancel(long id);
		void onEditFragmentReload(long id);
	}
}
