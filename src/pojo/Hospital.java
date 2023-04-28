package pojo;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Hospital {
    Doctor[] doctors;

    public Hospital() {
        ArrayList<Doctor> doctorList = new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("doctors.txt"));
            while (true) {
                try {
                    // Read an object from the file and add it to the list
                    Doctor doctor = (Doctor) objectInputStream.readObject();
                    doctorList.add(doctor);
                } catch (EOFException e) {
                    // End of file reached
                    break;
                }
            }
            objectInputStream.close();
            System.out.println("List of Doctors read from file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        doctors = doctorList.toArray(new Doctor[0]);
    }

    /*
    Return Values
    0-->success
    1--> busy
    2--> out of bounds
    3--> doctor not found
     */
    public int makeAppointment(int ID, int indexOfTimeSlot, String patient) {
        for (Doctor doc : doctors) {
            if (doc.getiD() == ID) {
                if (doc.getTimeslots().length <= indexOfTimeSlot) return 2;
                if (!doc.getTimeslots()[indexOfTimeSlot]) {
                    doc.getTimeslots()[indexOfTimeSlot] = true;
                    doc.getPatients()[indexOfTimeSlot] = patient;
                    return 0;
                }
                if (doc.getTimeslots()[indexOfTimeSlot]) return 1;
            }
        }
        return 3;
    }

    /*
    Return Values
    0 --> Success
    1 --> The time slot is free
    2 --> Out of bounds
    3 --> The doctor not found
    4 --> The patient is different
     */
    public int cancelAppointment(int ID, int indexOfTimeSlot, String patient) {
        for (Doctor doc : doctors) {
            if (doc.getiD() == ID) {
                if (doc.getTimeslots().length <= indexOfTimeSlot) return 2;
                if (!doc.getTimeslots()[indexOfTimeSlot]) {
                    return 1;
                }
                if (doc.getTimeslots()[indexOfTimeSlot] && doc.getPatients()[indexOfTimeSlot].contains(patient)) {
                    doc.getTimeslots()[indexOfTimeSlot] = false;
                    doc.getPatients()[indexOfTimeSlot] = null;
                    return 0;
                } else return 4;

            }
        }
        return 3;
    }

    public void printData() {
        if (doctors.length == 0) System.out.println("There's no doctors in the hospital");
        for (Doctor doctor : doctors) {
            System.out.println("Doctor ID = " + doctor.getiD());
            System.out.println("Doctor Name = " + doctor.getName());
            int len = doctor.getPatients().length;
            boolean found = false;
            for (int i = 0; i < len; i++) {
                if (doctor.getTimeslots()[i]) {
                    found = true;
                    System.out.println("Patient : " + doctor.getPatients()[i] + " is at time slot " + i);
                }
            }
            if (!found) System.out.println("There's no patients");
            System.out.println();
            System.out.println();
        }
    }
}
