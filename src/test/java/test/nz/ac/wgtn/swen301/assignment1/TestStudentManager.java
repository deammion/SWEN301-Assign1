package test.nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.assignment1.StudentManager;
import nz.ac.wgtn.swen301.studentdb.Degree;
import nz.ac.wgtn.swen301.studentdb.NoSuchRecordException;
import nz.ac.wgtn.swen301.studentdb.Student;
import nz.ac.wgtn.swen301.studentdb.StudentDB;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

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
    public void testReadStudent() throws NoSuchRecordException {
        try {
            Student studentExpected = new Student("id0","James","Smith",new Degree("deg 0", "BSc Computer Science"));

            Student studentReceived = new StudentManager().readStudent("id0");
            assertNotNull(studentReceived);
            assertEquals(studentExpected, studentReceived);

        } catch (NoSuchRecordException | SQLException e) {
            fail("No such record exists");
        }
    }

    @Test
    public void testReadDegree() throws NoSuchRecordException {
        try {
            Degree degreeExpected = new Degree("deg0", "BSc Computer Science");

            Degree degreeReceived = new StudentManager().readDegree("deg0");
            assertNotNull(degreeReceived);
            assertEquals(degreeExpected, degreeReceived);

        } catch (NoSuchRecordException | SQLException e) {
            fail("No Degree exists");
        }
    }

//    @Test
//    public void testDelete() throws NoSuchRecordException {
//        try {
//
//        } catch (NoSuchRecordException e) {
//
//        }
//    }
//
//    @Test
//    public void testUpdate() throws NoSuchRecordException {
//        try {
//
//        } catch (NoSuchRecordException e) {
//
//        }
//    }
//
//    @Test
//    public void testCreateStudent() {
//
//    }
//
//    @Test
//    public void testGetAllStudentIds() {
//
//    }
//
//    @Test
//    public void testGetAllDegreeIds() {
//
//    }
}
