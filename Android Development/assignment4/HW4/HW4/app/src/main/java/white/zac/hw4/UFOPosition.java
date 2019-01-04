package white.zac.hw4;


import android.os.Parcel;
import android.os.Parcelable;

public class UFOPosition implements Parcelable{
    private int shipNumber;
    private double lat;
    private double lon;

    public UFOPosition(int shipNumber, double lat, double lon) {
        this.shipNumber = shipNumber;
        this.lat = lat;
        this.lon = lon;
    }

    public UFOPosition() {
    }

    public int getShipNumber() {
        return shipNumber;
    }


    public double getLat() {
        return lat;
    }


    public double getLon() {
        return lon;
    }


    protected UFOPosition(Parcel in){
        this.shipNumber = in.readInt();
        this.lat = in.readDouble();
        this.lon = in.readDouble();
    }


    public static final Parcelable.Creator<UFOPosition> CREATOR
            = new Parcelable.Creator<UFOPosition>() {
        public UFOPosition createFromParcel(Parcel in) {
            return new UFOPosition(in);
        }

        public UFOPosition[] newArray(int size) {
            return new UFOPosition[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.shipNumber);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lon);
    }
}
