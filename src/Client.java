import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static final int MAKE_PORT = 6666;
    static final int CANCEL_PORT = 6667;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name : ");
        String name = scanner.nextLine();
        Socket make = new Socket("localhost", MAKE_PORT);
        Socket cancel = new Socket("localhost", CANCEL_PORT);
        PrintWriter printWriterMake = new PrintWriter(make.getOutputStream(), true);
        PrintWriter printWriterCancel = new PrintWriter(cancel.getOutputStream(), true);
        BufferedReader makeResponse = new BufferedReader(new InputStreamReader(make.getInputStream()));
        BufferedReader cancelResponse = new BufferedReader(new InputStreamReader(cancel.getInputStream()));
        printWriterMake.println("Patient Name = " + name);
        printWriterCancel.println("Patient Name = " + name);
        int choice, id, timeIndex;
        while (true) {
            System.out.printf("What do you want ?\n(0)Make appointment\n(1)Cancel appointment" +
                    "\n(2)close the program\n");
            choice = scanner.nextInt();
            if (choice > 2) {
                System.out.println("Invalid choice please try again");
                continue;
            }
            if (choice == 2){
                printWriterMake.println("ID = " + -1);
                break;
            }

            System.out.println("Please enter the ID of the doctor");
            id = scanner.nextInt();
            System.out.println("Please enter the Timeslot Index of the doctor");
            timeIndex = scanner.nextInt();
            String response;
            if (choice == 0) {
                printWriterMake.println("ID = " + id);
                printWriterMake.println("TimeIndex = " + timeIndex);
                response = makeResponse.readLine();
                System.out.println("The server message is = " + response);
            }
             else {
                printWriterCancel.println("ID = " + id);
                printWriterCancel.println("TimeIndex = " + timeIndex);
                response = cancelResponse.readLine();
                System.out.println("The server message is = " + response);
            }
        }

    }
}
