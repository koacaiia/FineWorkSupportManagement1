package fine.koaca.fineworksupportmanagement;

public class OutDataList {
    String date;
    String timeStamp;
    String usingName;
    String etc;
    int out;

    public OutDataList(){

    }

    public OutDataList(String date, String timeStamp, String usingName, String etc, int out) {
        this.date = date;
        this.timeStamp = timeStamp;
        this.usingName = usingName;
        this.etc = etc;
        this.out = out;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUsingName() {
        return usingName;
    }

    public void setUsingName(String usingName) {
        this.usingName = usingName;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }
}
