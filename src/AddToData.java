import pojo.Doctor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AddToData {
    public static void main(String[] args) throws IOException {
        int id, appointment;
        String name;
        ArrayList<Doctor> doctors = new ArrayList<>();
        Doctor doctor;
        while (true) {
            doctor = new Doctor();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Write the ID");
            id = scanner.nextInt();
            if (id == -1)
                break;
            doctor.setiD(id);
            System.out.println("Write the Name");
            name = scanner.next();
            doctor.setName(name);
            System.out.println("Write the number of time slots");
            id = scanner.nextInt();
            if (id > 23)
                id = 10;
            boolean[] timeSlots = new boolean[id];
            String[] names = new String[id];
            System.out.println("Write the number of patients");
            id = scanner.nextInt();
            for (int i = 0; i < id; i++) {
                System.out.println("Write the number of appointment of the patient number : " + i);
                appointment = scanner.nextInt();
                System.out.println("Write the Name of the patient number : " + i);
                names[appointment] = scanner.next();
                timeSlots[appointment] = true;

            }
            doctor.setPatients(names);
            doctor.setTimeslots(timeSlots);
            doctors.add(doctor);
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("doctors.txt"));
        for (Doctor doc : doctors) {
            outputStream.writeObject(doc);
        }
        outputStream.close();
        System.out.println("List of Doctors written to file successfully.");
    }
}
