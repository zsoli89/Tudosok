package tudosok;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    
    public DB() {
        
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A kapcsolat letrejott");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection létrehozásakor.");
            System.out.println(""+ex);
        }
        
        
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
//        try{
//            String sql = "drop table contacts";
//            createStatement.execute(sql);
//        }catch(SQLException ex) {
//            System.out.println("Nem létezik");
//        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
            if(!rs.next())
            { 
             createStatement.execute("create table contacts(id INT not null primary key "
                     + "GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), "
                     + "lastname varchar(20), firstname varchar(20), email varchar(30), age varchar(3))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    
    public ArrayList<Person> getAllContacts(){
        String sql = "select * from contacts";
        ArrayList<Person> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (rs.next()){
                Person actualPerson = new Person(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname"),
                        rs.getString("email"),rs.getString("age"));
                users.add(actualPerson);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
      return users;
    }
    
    public void addContact(Person person){
      try {
          System.out.println(person.toString());
          String sql = "insert into contacts (lastname, firstname, email, age) values (?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, person.getLastName());
        preparedStatement.setString(2, person.getFirstName());
        preparedStatement.setString(3, person.getEmail());
        preparedStatement.setString(4, person.getAge());
//        preparedStatement.setString(5, person.getTypeDegree());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateContact(Person person){
      try {
            String sql = "update contacts set lastname = ? , firstname = ? , "
                    + "email = ? , age = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4, person.getAge());
//            preparedStatement.setString(5, person.getTypeDegree());
            preparedStatement.setInt(6, Integer.parseInt(person.getId()));
            
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact frissetesekor");
            System.out.println(""+ex);
        }
    }
    
     public void removeContact(Person person){
      try {
            String sql = "delete from contacts where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(person.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println(""+ex);
        }
    }

    @Override
    public String toString() {
        return "DB{" + "conn=" + conn + ", createStatement=" + createStatement + ", dbmd=" + dbmd + '}';
    }
     
     
    
}

