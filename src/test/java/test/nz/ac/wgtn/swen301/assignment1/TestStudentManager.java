package test.nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.assignment1.StudentManager;
import nz.ac.wgtn.swen301.studentdb.Degree;
import nz.ac.wgtn.swen301.studentdb.NoSuchRecordException;
import nz.ac.wgtn.swen301.studentdb.Student;
import nz.ac.wgtn.swen301.studentdb.StudentDB;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.*;


public class TestStudentManager {

    // DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // AND IN ITS INITIAL STATE BEFORE EACH TEST RUNS
    @Before
    public  void init () {
        StudentDB.init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    @Test
    public void dummyTest() throws Exception {
        Student student = new StudentManager().readStudent("id42");
        // THIS WILL INITIALLY FAIL !!
        assertNotNull(student);
    }

    @Test
    public void test_readStudent() throws NoSuchRecordException {
        try {
            Student studentExpected = new Student("id0","James","Smith",new Degree());

            Student studentReceived = new StudentManager().readStudent("id0");
            assertNotNull(studentReceived);
            assertEquals(studentExpected, studentReceived);

        } catch (NoSuchRecordException | SQLException e) {
            fail("No such record exists");
        }
    }

    @Test
    public void test_readDegree() throws NoSuchRecordException {
        try {
            Degree degreeExpected = new Degree("deg0", "BSc Computer Science");

            Degree degreeReceived = new StudentManager().readDegree("deg0");
            assertNotNull(degreeReceived);
            assertEquals(degreeExpected, degreeReceived);

        } catch (NoSuchRecordException | SQLException e) {
            fail("No Degree exists");
        }
    }

    @Test
    public void test_delete() throws NoSuchRecordException {
        try {
            Student studentToDelete = new Student("id0","James","Smith",new Degree());

            StudentManager.delete(studentToDelete);
            StudentManager.readStudent("id0");

        } catch (NoSuchRecordException | SQLException e) {
            assert(true);
        }
    }

    @Test
    public void test_update() throws NoSuchRecordException {
        try {
            Student studentExpected = new Student("id0", "Student", "Updated", new Degree("deg0", "BSc Computer Science"));
            StudentManager.update(studentExpected);

            Student studentReceived = StudentManager.readStudent("id0");
            assertNotNull(studentReceived);
            assertEquals(studentExpected, studentReceived);

        } catch (NoSuchRecordException | SQLException e) {
            fail("No such record exists");
        }
    }

    @Test
    public void test_createStudent() {
        try {
            Student createdStudent = StudentManager.createStudent("Student", "Created", new Degree("deg0", "BSC Computer Science"));
            String newStudentId = "id10000";

            Student studentReceived = StudentManager.readStudent(newStudentId);

            assertNotNull(createdStudent);
            assertEquals(studentReceived,createdStudent);
        } catch (SQLException | NoSuchRecordException e) {
            fail("No such record exists");
        }
    }

    @Test
    public void test_getAllStudentIds() {
         try {
             Collection<String> studentIds = StudentManager.getAllStudentIds();
             int expectedStudentIdNumber = 10000;
             int recievedStudentIdNumber = studentIds.size();
             assertNotNull(studentIds);
             assertEquals(expectedStudentIdNumber, recievedStudentIdNumber);
         } catch (SQLException e) {
             fail("Expected value and received value differ");
         }
    }

    @Test
    public void test_getAllDegreeIds() {
         try {
            Iterable<String> degreeIds = StudentManager.getAllDegreeIds();
            //hard coded as all values of degree id is known, test will need to be updated if new degree is added
            String[] expectedDegreeIds = {"deg0", "deg1", "deg2", "deg3", "deg4", "deg5", "deg6", "deg7", "deg8", "deg9"};
            assertNotNull(degreeIds);
            ArrayList<String> receivedDegreeIds = new ArrayList<>((Collection<? extends String>) degreeIds);
            for(int i = 0; i < receivedDegreeIds.size(); i++) {
                assertEquals(expectedDegreeIds[i], receivedDegreeIds.get(i));
            }
        } catch (SQLException e) {
            fail("Expected value and received value differ");
        }
    }

    @Test
    public void test_performance() {
        long startTime = System.nanoTime();
        try {
            for(int i = 0; i <= 1000; i++){
                int randomId = new Random().nextInt(10000);
                Student studentReceived = StudentManager.readStudent("id" + randomId);
                assertNotNull(studentReceived);
            }
        } catch (NoSuchRecordException | SQLException e) {
            fail("No such record exists");
        }
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000 + "ms");
    }
}
