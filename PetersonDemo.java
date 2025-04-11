import java.util.Random;

public class PetersonDemo {
    static int shared = 0;
    static boolean[] flag = new boolean[2]; // Flags for the two threads
    static int turn = 0; // Variable to indicate whose turn it is

    // Sender Thread
    public static class SendingThread extends Thread {
        Random rand = new Random();

        public void run() {
            while (true) {
                flag[0] = true; // Indicate interest in the critical section
                turn = 1; // Give turn to the other thread
                
                // Wait until receiver finishes its critical section
                while (flag[1] && turn == 1);

                // Critical Section
                int tmp = rand.nextInt(1000);
                System.out.println("Send: " + tmp);
                shared = tmp;

                flag[0] = false; // Exit critical section
            }
        }
    }

    // Receiver Thread
    public static class ReceivingThread extends Thread {
        public void run() {
            while (true) {
                flag[1] = true; // Indicate interest in the critical section
                turn = 0; // Give turn to the sender
                
                // Wait until sender finishes its critical section
                while (flag[0] && turn == 0);

                // Critical Section
                System.out.println("Received: " + shared);

                flag[1] = false; // Exit critical section
            }
        }
    }

    public static void main(String[] args) {
        SendingThread sender = new SendingThread();
        ReceivingThread receiver = new ReceivingThread();

        sender.start();
        receiver.start();
    }
}
