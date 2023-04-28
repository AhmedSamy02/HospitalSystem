import pojo.Hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Hospital hospital = new Hospital();
    static final int MAKE_PORT = 6666;
    static final int CANCEL_PORT = 6667;

    static public void main(String[] args) {
//        hospital.printData();
        System.out.println("The server has started");

        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(MAKE_PORT);
                while (true) {
                    new Appointement(serverSocket.accept(), 0).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(CANCEL_PORT);
                while (true) {
                    new Appointement(serverSocket.accept(), 1).start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    static class Appointement extends Thread {
        private final Socket currSocket;
        private final int choice;

        public Appointement(Socket socket, int choice) {
            this.currSocket = socket;
            this.choice = choice;
        }

        @Override
        public void run() {
            try {
                System.out.println("A client has connected");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.currSocket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(this.currSocket.getOutputStream(), true);

                String response = bufferedReader.readLine();
                String patientName = response.substring(15);
                while (true) {
                    response = bufferedReader.readLine();
                    int id = Integer.parseInt(response.substring(5));
                    if (id == -1)
                        break;
                    response = bufferedReader.readLine();
                    int timeIndex = Integer.parseInt(response.substring(12));
                    if (choice == 0) {
                        int val = hospital.makeAppointment(id, timeIndex, patientName);
                        if (val == 0) {
                            printWriter.println("The appointment has been set successfully");
                        } else if (val == 1) {
                            printWriter.println("Failed!! The doctor is busy in that time slot");
                        } else if (val == 3) {
                            printWriter.println("Failed!! The doctor's ID isn't found");
                        } else {
                            printWriter.println("Failed!! The time slot index is out of bounds");
                        }
                    } else {
                        int val = hospital.cancelAppointment(id, timeIndex, patientName);
                        if (val == 0) {
                            printWriter.println("The appointment has been removed successfully");
                        } else if (val == 1) {
                            printWriter.printf("Failed!! The doctor does not have an appointment at this timeslot\n");
                        } else if (val == 3) {
                            printWriter.printf("Failed!! The doctor's ID isn't found\n");
                        } else if (val == 2) {
                            printWriter.printf("Failed!! The time slot index is out of bounds\n");
                        } else {
                            printWriter.printf("Failed!! The patient in that time is different\n");
                        }
                    }
                    hospital.printData();
                }
                printWriter.close();
                currSocket.close();
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
