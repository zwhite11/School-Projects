package white.zac.hw2;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class ContactsActivity extends FragmentFrameworkActivity<ContactsActivity.State, ContactsActivity.Event, Long>
implements ContactsFragment.OnTodoListFragmentListener,
	EditFragment.OnEditFragmentListener,
	DisplayFragment.OnDisplayFragmentListener{

	private boolean sideBySide;

	public enum State implements FragmentFrameworkActivity.State {
		List, Edit, Display, New, Exit;
	}
	public enum Event implements FragmentFrameworkActivity.Event {
		Reload, ItemSelected, EditContact, Done, Cancel, NewItem, Back;
	}

	private ContactsFragment contactsFragment;
	private EditFragment editFragment;
	private DisplayFragment displayFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list_framework);

		// NEW: Setup the Toolbar - find it and use it like an ActionBar
		//      We could alternatively directly inflate a menu into it and
		//      set a listener to handle actions. If you only have a single
		//      toolbar and it should be at the top of the activity, it's
		//      simpler to use the standard ActionBar support
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		contactsFragment = (ContactsFragment) getSupportFragmentManager().findFragmentById(R.id.contactsFragment);
		editFragment = (EditFragment) getSupportFragmentManager().findFragmentById(R.id.editFragment);
		displayFragment = (DisplayFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment);

		sideBySide = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

		stateMachine()
				.fragmentContainer(R.id.fragment_container1)
				.fragmentContainer(R.id.fragment_container2)

				.stateType(State.class)
				.initialState(State.List)
				.exitState(State.Exit)
				.backEvent(Event.Back)

				.state(State.List)
					.fragmentPriority(R.id.contactsFragment, R.id.displayFragment)
					.on(Event.ItemSelected).goTo(State.Display)
					.on(Event.NewItem).goTo(State.New)
					.on(Event.Back).goTo(State.Exit)

				.state(State.Edit)
					.fragmentPriority(R.id.editFragment, R.id.contactsFragment)
					.on(Event.Done).goTo(State.Display)
					.on(Event.Cancel).goTo(State.Display)
					.on(Event.Back).goTo(State.Display)
					.on(Event.Reload).goTo(State.Edit)

				.state(State.Display)
					.fragmentPriority(R.id.displayFragment, R.id.contactsFragment)
					.on(Event.EditContact).goTo(State.Edit)
					.on(Event.NewItem).goTo(State.Edit)
					.on(Event.Back).goTo(State.List)

				.state(State.New)
					.fragmentPriority(R.id.editFragment, R.id.contactsFragment)
					.on(Event.Done).goTo(State.List)
					.on(Event.Cancel).goTo(State.List)
					.on(Event.Back).goTo(State.List)

				.state(State.Exit);
	}

	@Override
	protected void onStateChanged(State state, Long id) {
		if (id == null) {
			id = -1L;
		}
	}

	//Contact List options
	@Override
	public void onTodoListFragmentItemSelected(long id) {
		displayFragment.setContactId(id);
		handleEvent(Event.ItemSelected, id);
	}

	@Override
	public void onTodoListFragmentCreateItem() {
		editFragment.setContactId(-1L);
		handleEvent(Event.NewItem, -1L);
	}

	@Override
	public void onTodoListFragmentAboutSelected(){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}


	// edit fragment options
	@Override
	public void onEditFragmentDone(long id) {
		displayFragment.setContactId(id);
		handleEvent(Event.Done, id);
	}

	@Override
	public void onEditFragmentCancel(long id) {
		displayFragment.setContactId(id);
		handleEvent(Event.Cancel, id);
	}

	@Override
	public void onEditFragmentReload(long id) {
		editFragment.setContactId(id);
		handleEvent(Event.Reload, id);
	}

	// Display fragment options
	@Override
	public void onDisplayFragmentEmailSelected(long id) {
		Contact contact = Util.findContact(displayFragment.getContext(),id);
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", contact.getEmailAddress(), null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi " + contact.getFirstName() + "!");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "just wanted to say hi...");
		startActivity(emailIntent);	}

	@Override
	public void onDisplayFragmentEditSelected(long id) {
		editFragment.setContactId(id);
		handleEvent(Event.EditContact, id);
	}

	@Override
	public void onDisplayFragmentAboutSelected(long id) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
}
