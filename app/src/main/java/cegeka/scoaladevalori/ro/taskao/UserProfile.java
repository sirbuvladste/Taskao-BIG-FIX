package cegeka.scoaladevalori.ro.taskao;

public class UserProfile {
    public String userEmail;
    public String userName;

    public UserProfile(){
    }

    public UserProfile(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

