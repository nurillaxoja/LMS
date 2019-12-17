package lms.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lms.listBook.BookListController.Book;
import lms.listStudent.StudentListController;
import lms.listStudent.StudentListController.Student;



public final class databaseHandler {
     private static databaseHandler handler = null;

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    private databaseHandler(){
        createConnection();
        settupAdminTable();
        setUpBookTable();
        settupMemberTable();
        settupStudentTable();
        settupIssueTable();
        
    }
    
    public static databaseHandler getInstance(){
        if(handler == null){
            handler = new databaseHandler();
        }
        return handler;
    }
    
     private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        }
        catch (Exception e) {
            System.exit(0);
        }
    }
     //////////////////////////////////////////////////////
     void setUpBookTable(){
         String TABLE_NAME = "BOOK";
         try {
             stmt = conn.createStatement();
             DatabaseMetaData dbm = conn.getMetaData();
             ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(tables.next()){
                 System.out.println("Table " + TABLE_NAME + " already exists.Ready for go!");
             }else{
                 stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
                 +"     id varchar(200) primary key,\n"
                 +"     title varchar(200),\n"
                 +"     author varchar(200),\n"
                 +"     publisher varchar(200),\n"
                 +"     isAvail boolean default true"
                 +" )");    
             }
         } catch (SQLException e) {
             System.err.println(e.getMessage()+" ... setupDatabase");
         }finally{}   
     }
     
     /////////////////////////////////////////////
     
     void settupMemberTable(){
         String TABLE_NAME = "MEMBER";
         try {
             stmt = conn.createStatement();
             DatabaseMetaData dbm = conn.getMetaData();
             ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(tables.next()){
                 System.out.println("Table " + TABLE_NAME + " already exists.Ready for go!");
             }else{
                 stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
                 +"     id varchar(200) primary key,\n"
                 +"     name varchar(200),\n"
                 +"     surname varchar(200),\n"
                 +"     password varchar(200)\n"                
                 +" )");    
             }
         } catch (SQLException e) {
             System.err.println(e.getMessage()+" ... setupDatabase");
         }finally{}   
     }
     //////////////////////////////////////////////
     
     void settupStudentTable(){
         String TABLE_NAME = "STUDENT";
         try {
             stmt = conn.createStatement();
             DatabaseMetaData dbm = conn.getMetaData();
             ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(tables.next()){
                 System.out.println("Table " + TABLE_NAME + " already exists.Ready for go!");
             }else{
                 stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
                 +"     id varchar(200) primary key,\n"
                 +"     name varchar(200),\n"
                 +"     surname varchar(200),\n"
                 +"     password varchar(200)\n"                
                 +" )");    
             }
         } catch (SQLException e) {
             System.err.println(e.getMessage()+" ... setupDatabase");
         }finally{}   
     }
     
     //////////////////////////////////////////////
     void settupIssueTable(){
         String TABLE_NAME = "ISSUE";
         try {
             stmt = conn.createStatement();
             DatabaseMetaData dbm = conn.getMetaData();
             ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(tables.next()){
                 System.out.println("Table " + TABLE_NAME + " already exists.Ready for go!");
             }else{
                 stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
                 +"     bookId varchar(200) primary key,\n"
                 +"     studentId varchar(200),\n"
                 +"     issueTime timestamp default CURRENT_TIMESTAMP,\n"
                 +"     renew_count integer default 0,\n"
                 +"     FOREIGN KEY (bookid) REFERENCES BOOK(id),\n"
                 +"     FOREIGN KEY (studentId) REFERENCES STUDENT(id)"                
                 +" )");     
             }
         } catch (SQLException e) {
             System.err.println(e.getMessage()+" ... setupDatabase");
         }finally{}   
     }
     
     
     //////////////////////////////////////////////
     
     void settupAdminTable(){
         String TABLE_NAME = "ADMIN";
         try {
             stmt = conn.createStatement();
             DatabaseMetaData dbm = conn.getMetaData();
             ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(tables.next()){
                 System.out.println("Table " + TABLE_NAME + " already exists.Ready for go!");
             }else{
                 stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
                 +"     id varchar(200) primary key,\n"
                 +"     name varchar(200),\n"
                 +"     surname varchar(200),\n"              
                 +"     password varchar(200)\n"                
                 +" )");    
             }
         } catch (SQLException e) {
             System.err.println(e.getMessage()+" ... setupDatabase");
         }finally{}   
     }
     ///////////////////////////////////////////
     
     public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler"  + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }finally {
        }
    }
    
    public boolean deleteBook(Book book){
         try {
             String deleteStatment = "DELETE FROM BOOK WHERE ID = ?";
             PreparedStatement stmt = conn.prepareStatement(deleteStatment);
             stmt.setString(1, book.getId());
             int res = stmt.executeUpdate();
             if(res == 1)
             {
                 return true;
             }
            return true;
         } catch (SQLException ex) {
             Logger.getLogger(databaseHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
    }
    public boolean BookAlreadyIssed(Book book){
        try {
             String checkstmt = "SELECT COUNT(*) FROM ISSUE WHERE BOOKID=?";
             PreparedStatement stmt = conn.prepareStatement(checkstmt);
             stmt.setString(1, book.getId());
             ResultSet rs = stmt.executeQuery();
             while(rs.next()){
                 int count = rs.getInt(1);
                 System.out.println(count);
                 return (count>0);
             }    
        } catch (SQLException ex) {
             Logger.getLogger(databaseHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
    }
    public boolean updateBook(Book book){
         try {
             String update = "UPDATE BOOK SET TITLE=?,AUTHOR=?, PUBLISHER=? WHERE ID=?";
             PreparedStatement stmt = conn.prepareStatement(update);
             stmt.setString(1, book.getTitle());
             stmt.setString(2, book.getAuthor());
             stmt.setString(3, book.getPublisher()); 
             stmt.setString(4, book.getId()); 
             int res = stmt.executeUpdate();
             return (res>0);
             
         } catch (SQLException ex) {
             Logger.getLogger(databaseHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
    }
    public boolean updateStudent(Student student){
         try {
             String update = "UPDATE STUDENT SET ID=?,NAME=?,SURNAME=?, PASSWORD=? WHERE ID=?";
             PreparedStatement stmt = conn.prepareStatement(update);
             stmt.setString(1, student.getId());
             stmt.setString(2, student.getName());
             stmt.setString(3, student.getSurname()); 
             stmt.setString(4, student.getPassword()); 
             int res = stmt.executeUpdate();
             return (res>0);
             
         } catch (SQLException ex) {
             Logger.getLogger(databaseHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
    }
    
    public boolean StudentIssedBook(Student student){
        try {
             String checkstmt = "SELECT COUNT(*) FROM ISSUE WHERE STUDENTID=?";
             PreparedStatement stmt = conn.prepareStatement(checkstmt);
             stmt.setString(1, student.getId());
             ResultSet rs = stmt.executeQuery();
             while(rs.next()){
                 int count = rs.getInt(1);
                 System.out.println(count);
                 return (count>0);
             }    
        } catch (SQLException ex) {
             Logger.getLogger(databaseHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
    }
    
    public boolean deleteStudent(Student Student){
         try {
             String deleteStatment = "DELETE FROM STUDENT WHERE ID = ?";
             PreparedStatement stmt = conn.prepareStatement(deleteStatment);
             stmt.setString(1, Student.getId());
             int res = stmt.executeUpdate();
             if(res == 1)
             {
                 return true;
             }
            return true;
         } catch (SQLException ex) {
             Logger.getLogger(databaseHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
    }
    
    

}

    
   