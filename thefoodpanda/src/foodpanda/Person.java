package foodpanda;

public class Person {
    private String userId;
    private String personName;
    private String userPassword;


    public Person(String userId, String personName, String userPassword) {
        this.userId = userId;
        this.personName = personName;
        this.userPassword = userPassword;
    }
    public Person(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
