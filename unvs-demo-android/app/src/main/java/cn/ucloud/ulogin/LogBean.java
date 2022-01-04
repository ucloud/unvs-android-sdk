package cn.ucloud.ulogin;

public class LogBean {
    private long timestamp;
    private String log;

    public LogBean(String log) {
        timestamp = System.currentTimeMillis();
        this.log = log;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getLog() {
        return log;
    }

    public LogBean setLog(String log) {
        this.log = log;
        return this;
    }
}
