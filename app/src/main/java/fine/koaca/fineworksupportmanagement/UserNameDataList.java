package fine.koaca.fineworksupportmanagement;

public class UserNameDataList {
    String usingName;
    int out;
    String dayOut;
    String usingPercent;
    public UserNameDataList(){

    }

    public UserNameDataList(String usingName, int out, String dayOut, String usingPercent) {
        this.usingName = usingName;
        this.out = out;
        this.dayOut = dayOut;
        this.usingPercent = usingPercent;
    }

    public String getUsingName() {
        return usingName;
    }

    public void setUsingName(String usingName) {
        this.usingName = usingName;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public String getDayOut() {
        return dayOut;
    }

    public void setDayOut(String dayOut) {
        this.dayOut = dayOut;
    }

    public String getUsingPercent() {
        return usingPercent;
    }

    public void setUsingPercent(String usingPercent) {
        this.usingPercent = usingPercent;
    }
}
