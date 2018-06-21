package hu.ait.android.rosesandthorns.data;

/**
 * Created by mayavarghese on 5/12/18.
 */

public class Entry {
    private String uId;
    private String displayName;
    private String rose;
    private String thorn;
    private String bud;

    public Entry(){}

    public Entry(String uId, String displayName, String rose, String thorn, String bud) {
        this.uId = uId;
        this.displayName = displayName;
        this.rose = rose;
        this.thorn = thorn;
        this.bud = bud;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRose() {
        return rose;
    }

    public void setRose(String rose) {
        this.rose = rose;
    }

    public String getThorn() {
        return thorn;
    }

    public void setThorn(String thorn) {
        this.thorn = thorn;
    }

    public String getBud() {
        return bud;
    }

    public void setBud(String bud) {
        this.bud = bud;
    }
}
