import java.util.concurrent.*;
import java.util.*;

public class ThreadDemo {
    // Queue of customers waiting for a teller
    static Queue<Customer> customerQueue = new LinkedList<>();
    static Semaphore queueLock = new Semaphore(1);
    static Semaphore customerAvailable = new Semaphore(0); // signals when customer is available
    static Semaphore managerAvailable = new Semaphore(1); // signals when manager is available
    static Semaphore managerDone = new Semaphore(0);  
    static int numofCust=50;
    


    static class Teller extends Thread {
        int id;

        Teller(int id) {
            this.id = id;
        }

        public void run() {
            try {
                while (true) {


                    
                    System.out.println("Teller "+this.id+ "[]: is ready to serve");
                    System.out.println("Teller "+this.id+ "[]: is waiting for a customer");


                    if(numofCust==0)
                    {
                        System.out.println("No more customers");
                        System.out.println("The bank is closed for the day");

                        System.exit(0);

                    }

                    // Wait for a customer
                    customerAvailable.acquire();

                    queueLock.acquire();
                    Customer customer = customerQueue.poll();
                    queueLock.release();

                       

                    customer.chooseTeller(this);

                    // Tell the customer "Hi"
                    System.out.println("Teller " + id + "[Customer "+customer.id+"] Hi, Customer ");

                    // customer to reply
                    customer.greeted.release();  // Let customer reply
                   

                    // Wait for customer to reply
                    customer.replied.acquire();


                    //do action
                    Understand(customer);


                    //done
                    System.out.println("Teller " + id + "[Customer "+customer.id+"]: Done talking to customer ");
                
                
                }
            } catch (Exception e) {
                System.err.println("Teller error: " + e);
            }
        }

        private void Understand (Customer c)
        {
            try {

            if (c.deposit){
            System.out.println("Teller "+this.id+ " ok customer you want to deposit");


            }
            if (c.withdraw){
            System.out.println("Teller "+this.id+" ok customer you want to withdraw getting the manager ");
           

            managerAvailable.acquire();//wait for manager
            Manager m= new Manager(c);
            m.start(); // start manager thread
            managerDone.acquire(); 

            System.out.println("Teller "+this.id+ "[Customer "+c.id+"]"+": handling deposit transaction ");
            System.out.println("Teller "+this.id+ "[Customer "+c.id+"]"+": going to safe ");
            System.out.println("Teller "+this.id+ "[Customer "+c.id+"]"+": entering safe ");
            System.out.println("Teller "+this.id+ "[Customer "+c.id+"]"+": leaving safe ");
            System.out.println("Teller "+this.id+ "[Customer "+c.id+"]"+":giving money ");



            }
        } catch (Exception e) {
            System.err.println("Teller error: " + e);
        }
        }







    }




    static class Manager extends Thread {
        Customer customer;
    
        Manager(Customer c) {
            this.customer = c;
            
        }
    
        public void run() {
            try {
                System.out.println("Manager: Approving money for Customer " + customer.id);
                Math.random();
                int randomNum = (int)(Math.random() * 30);
                
                Thread.sleep(randomNum); 
                managerDone.release(); // signal teller that manager is done
                managerAvailable.release(); // manager is now available 
            } catch (Exception e) {
                System.err.println("Manager error: " + e);
            } 
        }
    }
    



    static class Customer extends Thread {
        int id;
        Semaphore greeted = new Semaphore(0); // Wait for teller to greet
        Semaphore replied = new Semaphore(0); 
        boolean deposit=false; 
        boolean withdraw=false;
        Customer(int id) {
            this.id = id;
            
        }


        public void customerActions()
        {

            System.out.println("Customer "+this.id+"[]: Going to the bank");
            System.out.println("Customer "+this.id+"[]: Entering the bank");
            System.out.println("Customer "+this.id+"[]: Getting in line");




        }





        public void Decision()
        {
            Math.random();
            int randomNum = (int)(Math.random() * 2); // 0 to 1

            if (randomNum==0)
            {
                System.out.println("Customer "+this.id+"[]: wants to deposit ");
            deposit=true;}
            else if (randomNum==1){
                System.out.println("Customer "+this.id+"[]: wants to withdraw ");

            withdraw=true;
            }

        }


        public void chooseTeller(Teller t)
        {
            System.out.println("Customer "+this.id+ "[Teller "+t.id+"]: Chooses teller ");
        }

        public void run() {
            try {


                Decision();
                 customerActions();

                
                queueLock.acquire();
                customerQueue.add(this);//quue add
                queueLock.release();

                // Signal a teller that a customer is waiting
                customerAvailable.release();

                // Wait for the teller to greet
                greeted.acquire();

                //  respond
                if(deposit)
                System.out.println("Customer " + id + "[]: Hello, Teller i would like to deposit ");
                else if (withdraw)
                System.out.println("Customer " + id + "[]: Hello, Teller i would like to withdraw ");





                // Tell the teller that the customer is done replying
                replied.release();
            } catch (Exception e) {
                System.err.println("Customer error: " + e);
            }
        }
    }

    public static void main(String[] args) {
        int numTellers = 3;
        int numCustomers = 50;

        // Start tellers
        for (int i = 0; i < numTellers; i++) {
            new Teller(i).start();
        }

        // Start customers
        for (int i = 0; i < numCustomers; i++) {
            new Customer(i).start();
            numofCust--;
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}

           
        }
    }
}
