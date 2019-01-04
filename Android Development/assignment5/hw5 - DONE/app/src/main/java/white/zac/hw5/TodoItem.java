package white.zac.hw5;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.provider.Settings;

import java.text.SimpleDateFormat;

public class TodoItem {
    public final ObservableLong id = new ObservableLong();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableInt priority = new ObservableInt();
    public final ObservableLong dueTime = new ObservableLong();
    public final ObservableField<String> status = new ObservableField<>();


    public TodoItem() {
        this.id.set(-1);
        this.name.set("");
        this.description.set("");
        this.priority.set(1);
        this.dueTime.set(System.currentTimeMillis() + 10000);
        this.status.set("Pending");
    }
    public TodoItem(long id, String name, String description, long dueTime, int priority, String status) {
        this.id.set(id);
        this.name.set(name);
        this.description.set(description);
        this.priority.set(priority);
        this.dueTime.set(dueTime);
        this.status.set(status);
    }

    @Override
    public String toString() {
        return "Item{id=" + id.get() +
                ", name=" + name.get() +
                ", description=" + description.get() +
                ", priority=" + priority.get() +
                ", due time=" + dueTime.get() +
                ", status=" + status.get() +"}";
    }

    //used to display the date in specific format
    public String convertDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
        return formatter.format(this.dueTime.get());
    }
}
