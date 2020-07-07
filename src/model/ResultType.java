package model;

//Class to return to the UI the information about files!
public class ResultType {

    private String msg = "Operación exitosa";
    private double originalSize = 0.0;
    private double newSize = 0.0;
    private boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public double getOriginalSize() {
        return originalSize;
    }

    public void setOriginalSize(double originalSize) {
        this.originalSize = originalSize;
    }

    public double getNewSize() {
        return newSize;
    }

    public void setNewSize(double newSize) {
        this.newSize = newSize;
    }

    public ResultType(String msg, double originalSize, double newSize, boolean error){
        this.setMsg(msg);
        this.originalSize = originalSize;
        this.newSize = newSize;
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
