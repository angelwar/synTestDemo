package syn.databingdemo;

/**
 * Created by yanan on 2017/5/9.
 */

public class Express {
    private String number;
    private String time;
    private String state;

    public Express(String number, String time, String state) {
        this.number = number;
        this.time = time;
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
