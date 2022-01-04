package cn.ucloud.ulogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<LogBean> logs;

    public LogAdapter(Context context, List<LogBean> logs) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.logs = logs;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogViewHolder(inflater.inflate(R.layout.item_log, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        if (logs == null)
            return;
        LogBean log = logs.get(position);
        if (log == null)
            return;
        holder.bindData(log);
    }

    @Override
    public int getItemCount() {
        return logs == null ? 0 : logs.size();
    }
}
