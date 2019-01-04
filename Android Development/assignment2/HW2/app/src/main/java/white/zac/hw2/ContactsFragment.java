package white.zac.hw2;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class ContactsFragment extends Fragment {
	private static final int CONTACT_LOADER = 42;

	private long nextId = 1000;

	// our model for the RecyclerView
	private ContactCursorAdapter adapter;
	private boolean sideBySide;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		// check if in landscape view
		sideBySide = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
		// get the recycler view
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

		// create some dummy data
		Util.updateContact(getContext(), new Contact(-1,
				"Zac", "White", "4104104104", "1234567890", "0987654321", "zacswhite@gmail.com"));
		Util.updateContact(getContext(), new Contact(-1,
				"FirstName", "LastName", "1111111111", "2222222222", "3333333333", "first.last@email.com"));
		Util.updateContact(getContext(), new Contact(-1,
				"First1", "Last1", "4104104104", "1234567890", "0987654321", "first1last1@email.com"));

		// wrap the data in our adapter to use as a model for the recycler view
		adapter = new ContactCursorAdapter(getActivity(), getActivity().getLayoutInflater());
		recyclerView.setAdapter(adapter);

		// layout the items in the recycler view as a vertical list
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		// listen to the adapter to find out when a contact has been selected
		adapter.setContactListListener(new ContactCursorAdapter.ContactListListener() {
			@Override public void itemSelected(long id) {
				if (onTodoListFragmentListener != null)
					onTodoListFragmentListener.onTodoListFragmentItemSelected(id);
			}});

		// set up support for drag/swipe gestures
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
				new ItemTouchHelper.Callback() {
					// specify which drags/swipes we want to support
					@Override
					public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
						int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
						return makeMovementFlags(0, swipeFlags);
					}

					// if an contact is being dragged, tell the adapter
					@Override
					public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
						return true;
					}

					// if an contact is being swiped, tell the adapter
					@Override
					public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
						Util.delete(getContext(), viewHolder.getItemId());
					}
				}
		);

		// attach the swipe/gesture support to the recycler view
		itemTouchHelper.attachToRecyclerView(recyclerView);

		// NEW: Set up the Floating Action Button to act as "add new contact"
		FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
		assert fab != null;
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Contact contact = new Contact(nextId++, "", "", "","","","");
				if (onTodoListFragmentListener != null)
					onTodoListFragmentListener.onTodoListFragmentCreateItem();
			}
		});

		return view;
	}

	private OnTodoListFragmentListener onTodoListFragmentListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (!(context instanceof OnTodoListFragmentListener))
			throw new IllegalStateException("Activities using ContactsFragment must implement ContactsFragment.OnTodoListFragmentListener");
		onTodoListFragmentListener = (OnTodoListFragmentListener) context;
		getActivity().getSupportLoaderManager().initLoader(CONTACT_LOADER, null, loaderCallbacks);
	}

	@Override
	public void onDetach() {
		onTodoListFragmentListener = null;
		super.onDetach();
		getActivity().getSupportLoaderManager().destroyLoader(CONTACT_LOADER);
	}

	private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			String[] projection = {
					ContactContentProvider.COLUMN_ID,
					ContactContentProvider.COLUMN_FIRST_NAME,
					ContactContentProvider.COLUMN_LAST_NAME,
					ContactContentProvider.COLUMN_HOME_PHONE,
					ContactContentProvider.COLUMN_WORK_PHONE,
					ContactContentProvider.COLUMN_MOBILE_PHONE,
					ContactContentProvider.COLUMN_EMAIL
			};

			return new CursorLoader(
					getActivity(), ContactContentProvider.CONTENT_URI, projection, null, null,
					ContactContentProvider.COLUMN_LAST_NAME + " ASC"
			);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			if (adapter != null)
				adapter.changeCursor(cursor);
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			if (adapter != null)
				adapter.changeCursor(null);
		}
	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//only inflate the menu if we are in landscape mode
		//this prevents the "about" icon from showing twice
		if(!sideBySide){
			inflater.inflate(R.menu.menu_contact_list, menu);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_about:
				onTodoListFragmentListener.onTodoListFragmentAboutSelected();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public interface OnTodoListFragmentListener {
		void onTodoListFragmentItemSelected(long id);
		void onTodoListFragmentCreateItem(); //floating action button
		void onTodoListFragmentAboutSelected();
	}
}
