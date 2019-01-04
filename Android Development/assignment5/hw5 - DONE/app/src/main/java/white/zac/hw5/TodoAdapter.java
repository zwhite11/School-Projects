package white.zac.hw5;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Set;

import white.zac.hw5.databinding.TodoCardBinding;

public class TodoAdapter extends CursorRecyclerViewAdapter<TodoAdapter.ViewHolder> {
    private Set<Integer> selectedRows = new HashSet<>();

    public interface OnItemClickedListener {
        void onItemClicked(long id);
    }
    private OnItemClickedListener onItemClickedListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TodoCardBinding binding;
        public long id;

        public ViewHolder(View view) {
            super(view);
            binding = TodoCardBinding.bind(view);
        }
    }

    public TodoAdapter(Context context, OnItemClickedListener onItemClickedListener) {
        super(context, null);
        this.onItemClickedListener = onItemClickedListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_card, parent, false);

        view.setClickable(true);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                onItemClickedListener.onItemClicked(vh.id);
            }});
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Cursor cursor) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemView.setSelected(selectedRows.contains(position));

        holder.binding.setItem(Util.todoItemFromCursor(cursor));

        holder.id = holder.binding.getItem().id.get();

        holder.binding.executePendingBindings();

    }

    public Cursor swapCursor(Cursor newCursor) {
        selectedRows.clear();
        return super.swapCursor(newCursor);
    }
}