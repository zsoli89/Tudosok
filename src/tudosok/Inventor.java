package tudosok;

import javafx.beans.property.SimpleStringProperty;

public class Inventor extends Participant{
  //A simpleStringProperty olyan hordozó, ami képesek kommunikálni az adattáblával
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty age;
//    private final SimpleStringProperty invention;
    private final SimpleStringProperty id;

    public Inventor() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.age = new SimpleStringProperty("");
//        this.typeDegree = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }

    public Inventor(String lName, String fName, String email, String age) {
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleStringProperty(age);
//        this.typeDegree = new SimpleStringProperty(invention);
        this.id = new SimpleStringProperty("");
    }

    public Inventor(Integer id, String lName, String fName, String email, String age) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleStringProperty(age);
//        this.typeDegree = new SimpleStringProperty(invention);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lName) {
        lastName.set(lName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String fName) {
        email.set(fName);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String fId) {
        id.set(fId);
    }

    public String getAge() {
        return age.get();
    }

    public void setAge(String fAge) {
        age.set(fAge);
    }

//    public String getInvention() {
//        return typeDegree.get();
//    }
//
//    public void setInvention(String fInvention) {
//        invention.set(fInvention);
//    }

    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", age=" + age + ", id=" + id + '}';
    }
    
}
