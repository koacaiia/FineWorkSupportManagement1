package fine.koaca.fineworksupportmanagement;

public class UsingDataList {
    String date;
    int out;
    String etc;
    String timeStamp;
    String userName;
    String usingName;

    public String getUsingName() {
        return usingName;
    }

    public void setUsingName(String usingName) {
        this.usingName = usingName;
    }

    public UsingDataList(){

    }

    public UsingDataList(String date, int out, String etc, String timeStamp, String userName) {
        this.date = date;
        this.out = out;
        this.etc = etc;
        this.timeStamp = timeStamp;
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public String getDepotName() {
        return etc;
    }

    public void setDepotName(String depotName) {
        this.etc = depotName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
