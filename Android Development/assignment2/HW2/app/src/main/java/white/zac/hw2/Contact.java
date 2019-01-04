package white.zac.hw2;

import android.os.Parcel;
import android.os.Parcelable;

// a parcelable object representing a single contact
public class Contact implements Parcelable {
	private long id; // NEW: use an id so we can handle updates better

	//name
	private String firstName;
	private String lastName;

	//phone numbers
	private String hPhone;
	private String wPhone;
	private String mPhone;

	private String emailAddress;

	//constructor
	public Contact() {}

	public Contact(long id,
				   String firstName,
				   String lastName,
				   String hPhone,
				   String wPhone,
				   String mPhone,
				   String emailAddress) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hPhone = hPhone;
		this.wPhone = wPhone;
		this.mPhone = mPhone;
		this.emailAddress = emailAddress;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String gethPhone() {
		return hPhone;
	}

	public void sethPhone(String hPhone) {
		this.hPhone = hPhone;
	}

	public String getwPhone() {
		return wPhone;
	}

	public void setwPhone(String wPhone) {
		this.wPhone = wPhone;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	// only used by framework code for "special" cases
	// you should always return 0
	@Override
	public int describeContents() {
		return 0;
	}

	// write the data for this instance

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(hPhone);
		dest.writeString(wPhone);
		dest.writeString(mPhone);
		dest.writeString(emailAddress);
	}

	// read the data and create a new instance
	// note that the field MUST be named "CREATOR", all uppercase
	public static Creator<Contact> CREATOR = new Creator<Contact>() {

		@Override
		public Contact createFromParcel(Parcel source) {
			Contact contact = new Contact();
			contact.setId(source.readLong());
			contact.setFirstName(source.readString());
			contact.setLastName(source.readString());
			contact.sethPhone(source.readString());
			contact.setwPhone(source.readString());
			contact.setmPhone(source.readString());
			contact.setEmailAddress(source.readString());
			return contact;
		}

		@Override
		public Contact[] newArray(int size) {
			return new Contact[size];
		}
	};
}
