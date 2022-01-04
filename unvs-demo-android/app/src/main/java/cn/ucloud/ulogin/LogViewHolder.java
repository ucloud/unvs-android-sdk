package cn.ucloud.ulogin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogViewHolder extends RecyclerView.ViewHolder {

    private TextView txt_log_time;
    private TextView txt_log_content;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public LogViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_log_time = itemView.findViewById(R.id.txt_log_time);
        txt_log_content = itemView.findViewById(R.id.txt_log_content);
    }

    public void bindData(LogBean log) {
        txt_log_time.setText(format.format(new Date(log.getTimestamp())));
        txt_log_content.setText(log.getLog());
    }

}
