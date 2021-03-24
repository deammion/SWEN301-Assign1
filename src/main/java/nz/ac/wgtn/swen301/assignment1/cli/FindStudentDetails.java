package nz.ac.wgtn.swen301.assignment1.cli;

import nz.ac.wgtn.swen301.assignment1.StudentManager;
import nz.ac.wgtn.swen301.studentdb.NoSuchRecordException;
import nz.ac.wgtn.swen301.studentdb.Student;

import java.sql.SQLException;

public class FindStudentDetails {

    // THE FOLLOWING METHOD MUST IMPLEMENTED
    /**
     * Executable: the user will provide a student id as single argument, and if the details are found,
     * the respective details are printed to the console.
     * E.g. a user could invoke this by running "java -cp <someclasspath> nz.ac.wgtn.swen301.assignment1.cli.FindStudentDetails id42"
     * @param arg
     */
    public static void main (String[] arg) {
        try {
            Student student = StudentManager.readStudent(arg[0]);
            assert student != null;
            System.out.println("Student Details  id: " + student.getId() + " First name: " + student.getName()
            + " Last name: " + student.getFirstName() + " Degree id: " + student.getDegree().getId() + " Degree name: "
                    + student.getDegree().getName());
        } catch (NoSuchRecordException e) {
            System.out.println("No such record exist");
            e.printStackTrace();
        }
    }
}
