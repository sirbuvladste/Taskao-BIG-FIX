package cegeka.scoaladevalori.ro.taskao;

public class UserActivities {

    String userActivityTitile;
    String userActivityDescription;
    String userActivityDate;
    String userActivityId;

    public UserActivities(){
    }

    public UserActivities(String userActivityTitile, String userActivityDescription, String userActivityDate) {
        this.userActivityTitile = userActivityTitile;
        this.userActivityDescription = userActivityDescription;
        this.userActivityDate = userActivityDate;

    }

    public String getUserActivityTitile() {
        return userActivityTitile;
    }

    public void setUserActivityTitile(String userActivityTitile) {
        this.userActivityTitile = userActivityTitile;
    }

    public String getUserActivityDescription() {
        return userActivityDescription;
    }

    public void setUserActivityDescription(String userName) {
        this.userActivityDescription = userName;
    }

    public String getUserActivityDate() {
        return userActivityDate;
    }

    public void setUserActivityDate(String userActivityDate) {
        this.userActivityDate = userActivityDate;
    }

    public String getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(String userActivityId) {
        this.userActivityId = userActivityId;
    }

}
