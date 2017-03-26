package Entities;

/**
 * Created by krirs on 19.03.2017.
 */
public class Box {


    private int id;
    private String pic;
    private String name;
    private double dailyRate;
    private boolean window;
    private boolean isDeleted;
    private String comment;

    public Box(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment) {
        this.id = id;
        this.pic = pic;
        this.name = name;
        this.dailyRate = dailyRate;
        this.window = window;
        this.isDeleted= isDeleted;
        this.comment=comment;
    }
    public Box (){}

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean getWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String toString (){
        return "[BoxID:"+id+"|Boxname:"+name+"|Rate:"+dailyRate+"|Window:"+window+"]";
    }


}
