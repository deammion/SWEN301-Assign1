package nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.studentdb.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A student managers providing basic CRUD operations for instances of Student, and a read operation for instances of Degree.
 * @author jens dietrich
 */
public class StudentManager {

    // DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // AND THE APPLICATION CAN CONNECT TO IT WITH JDBC
    static {
        StudentDB.init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    // THE FOLLOWING METHODS MUST IMPLEMENTED :

    /**
     * Return a student instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * @param id
     * @return
     * @throws NoSuchRecordException if no record with such an id exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_readStudent (followed by optional numbers if multiple tests are used)
     */
    public static Student readStudent(String id) throws NoSuchRecordException, SQLException {
        String sql = "SELECT * FROM STUDENTS WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:derby:memory:studentdb");
            PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String idnum = result.getString(1);
                String lName = result.getString(2);
                String fName = result.getString(3);
                String degree = result.getString(4);
                result.close();
                connection.close();
                return new Student(idnum, lName, fName, readDegree(degree));
            } else {
                throw new NoSuchRecordException();
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * Return a degree instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * @param id
     * @return
     * @throws NoSuchRecordException if no record with such an id exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_readDegree (followed by optional numbers if multiple tests are used)
     */
    public static Degree readDegree(String id) throws NoSuchRecordException, SQLException {
        String sql = "SELECT * FROM DEGREES WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:derby:memory:studentdb");
             PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String idnum = result.getString(1);
                String degreeName = result.getString(2);
                result.close();
                connection.close();
                return new Degree(idnum, degreeName);
            } else {
                throw new NoSuchRecordException();
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * Delete a student instance from the database.
     * I.e., after this, trying to read a student with this id will result in a NoSuchRecordException.
     * @param student
     * @throws NoSuchRecordException if no record corresponding to this student instance exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_delete
     */
    public static void delete(Student student) throws NoSuchRecordException, SQLException {
        String id = student.getId();
        String sql = "DELETE FROM STUDENTS WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:derby:memory:studentdb");
             PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, id);

            stmt.executeQuery();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * Update (synchronize) a student instance with the database.
     * The id will not be changed, but the values for first names or degree in the database might be changed by this operation.
     * After executing this command, the attribute values of the object and the respective database value are consistent.
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param student
     * @throws NoSuchRecordException if no record corresponding to this student instance exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_update (followed by optional numbers if multiple tests are used)
     */
    public static void update(Student student) throws NoSuchRecordException, SQLException {
        String id = student.getId();
        String name = student.getName();
        String fName = student.getFirstName();
        String degree = student.getDegree().getId();

        String sql = "UPDATE STUDENTS SET name = ?, first_name = ?, degree = ? WHERE id=?";

        try (Connection connection = DriverManager.getConnection("jdbc:derby:memory:studentdb");
             PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, fName);
            stmt.setString(2, name);
            stmt.setString(3, degree);
            stmt.setString(4, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }


    /**
     * Create a new student with the values provided, and save it to the database.
     * The student must have a new id that is not been used by any other Student instance or STUDENTS record (row).
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param name
     * @param firstName
     * @param degree
     * @return a freshly created student instance
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_createStudent (followed by optional numbers if multiple tests are used)
     */
    public static Student createStudent(String name,String firstName,Degree degree) throws SQLException {
        ArrayList<String> currentStudents = new ArrayList<>(getAllStudentIds());
        String lastCurrentStudentId = currentStudents.get(currentStudents.size()-1);
        int lastIdNumber = Integer.parseInt(lastCurrentStudentId.substring(2));
        int newStudentIdNumber = lastIdNumber +1;
        String newStudentId = "id" + newStudentIdNumber;

        String sql = "INSERT INTO STUDENTS (id, name, first_name, degree)" + " VALUES (?,?,?,?) ";
        try (Connection connection = DriverManager.getConnection("jdbc:derby:memory:studentdb");
             PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, newStudentId);
            stmt.setString(2, firstName);
            stmt.setString(3, name);
            stmt.setString(4, degree.getId());

            stmt.executeUpdate();
            connection.close();
            return new Student(newStudentId, name, firstName, degree);
        } catch (SQLException e) {
            throw new SQLException();
        }

    }

    /**
     * Get all student ids currently being used in the database.
     * @return
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_getAllStudentIds (followed by optional numbers if multiple tests are used)
     */
    public static Collection<String> getAllStudentIds() throws SQLException {
        String sql = "SELECT id FROM STUDENTS";
        Collection<String> studentIds = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:derby:memory:studentdb");
             PreparedStatement stmt = connection.prepareStatement(sql)){

            ResultSet result = stmt.executeQuery();

            while(result.next()){
                studentIds.add(result.getString(1));
            }
            result.close();
            connection.close();
            return studentIds;

        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * Get all degree ids currently being used in the database.
     * @return
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_getAllDegreeIds (followed by optional numbers if multiple tests are used)
     */
    public static Iterable<String> getAllDegreeIds() throws SQLException {
        String sql = "SELECT * FROM DEGREES";
        ArrayList<String> degreeIds = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:derby:memory:studentdb");
             PreparedStatement stmt = connection.prepareStatement(sql)){

            ResultSet result = stmt.executeQuery();

            while(result.next()){
                degreeIds.add(result.getString(1));
            }
            result.close();
            connection.close();
            return degreeIds;

        } catch (SQLException e) {
            throw new SQLException();
        }
    }
}
