package fine.koaca.fineworksupportmanagement;

public class InOutStockList {
    String date;
    int in;
    int out;
    int stock;
    String remark;
    String depot;
    String usingName;
    String time;
    String etc;

    public InOutStockList(){

    }

    public InOutStockList(String date, int in, int out, int stock, String remark, String depot, String usingName, String time, String etc) {
        this.date = date;
        this.in = in;
        this.out = out;
        this.stock = stock;
        this.remark = remark;
        this.depot = depot;
        this.usingName = usingName;
        this.time = time;
        this.etc = etc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getUsingName() {
        return usingName;
    }

    public void setUsingName(String usingName) {
        this.usingName = usingName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
