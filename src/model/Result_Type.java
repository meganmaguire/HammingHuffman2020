package model;

//Class to return to the UI the information about files!
public class Result_Type {
    double originalSize = 0.0;
    double newSize = 0.0;
    boolean error = false;

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

    public Result_Type(double originalSize, double newSize, boolean error){
        this.originalSize = originalSize;
        this.newSize = newSize;
        this.error = error;
    }
}
