package syn.databingdemo;

import android.databinding.BaseObservable;

/**
 * Created by yanan on 2017/5/9.
 */

public class Express extends BaseObservable {
    private String number;
    private String time;
    private String state;
    private String image;

    public Express(String number, String time, String state,String image) {
        this.number = number;
        this.time = time;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
