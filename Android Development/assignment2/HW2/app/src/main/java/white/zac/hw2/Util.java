package white.zac.hw2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

public class Util {
	public static Contact findContact(Context context, long id) {
		Uri uri = ContentUris.withAppendedId(ContactContentProvider.CONTENT_URI, id);

		String[] projection = {
				ContactContentProvider.COLUMN_ID,
				ContactContentProvider.COLUMN_FIRST_NAME,
				ContactContentProvider.COLUMN_LAST_NAME,
				ContactContentProvider.COLUMN_HOME_PHONE,
				ContactContentProvider.COLUMN_WORK_PHONE,
				ContactContentProvider.COLUMN_MOBILE_PHONE,
				ContactContentProvider.COLUMN_EMAIL
		};

		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(uri, projection, null, null, null);

			if (cursor == null || !cursor.moveToFirst())
				return null;

			return new Contact(
				cursor.getLong(0),
				cursor.getString(1),
				cursor.getString(2),
				cursor.getString(3),
				cursor.getString(4),
				cursor.getString(5),
				cursor.getString(6)
			);
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}

	public static void updateContact(Context context, Contact contact) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(ContactContentProvider.COLUMN_FIRST_NAME, contact.getFirstName());
		contentValues.put(ContactContentProvider.COLUMN_LAST_NAME, contact.getLastName());
		contentValues.put(ContactContentProvider.COLUMN_HOME_PHONE, contact.gethPhone());
		contentValues.put(ContactContentProvider.COLUMN_WORK_PHONE, contact.getwPhone());
		contentValues.put(ContactContentProvider.COLUMN_MOBILE_PHONE, contact.getmPhone());
		contentValues.put(ContactContentProvider.COLUMN_EMAIL, contact.getEmailAddress());

		if (contact.getId() != -1) {
			Uri uri = ContentUris.withAppendedId(ContactContentProvider.CONTENT_URI, contact.getId());
			context.getContentResolver().update(uri, contentValues, null, null);
		} else {
			Uri uri = context.getContentResolver().insert(ContactContentProvider.CONTENT_URI, contentValues);
			if (uri == null) {
				throw new RuntimeException("No uri returned from insert");
			}
			String stringId = uri.getLastPathSegment();
			long id = Long.parseLong(stringId);
			contact.setId(id);
		}

	}

	public static void delete(Context context, long itemId) {
		Uri uri = ContentUris.withAppendedId(ContactContentProvider.CONTENT_URI, itemId);
		context.getContentResolver().delete(uri, null, null);
	}
}
