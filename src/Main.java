import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * I, Ryan Jay Salapare, 000823653, certify that all code submitted is my own work; that I have not copied it from any other source.
 * I also certify that I have not allowed my work to be copied by others.
 *
 * A Simple Java Program that will attempt to optimize the time it takes to
 * process customers in check-out lines inside a grocery store.
 * The general idea is to always place customers in the best checkout line possible.
 * This will be based on the total number of customers and the number of items in each cart being processed.
 */
public class Main {
    public static void main(String[] args) {
        final int SIMULATION_STEP = 1;   // Every second
        final int PRINT_FREQUENCY = 30;  // Every 30 seconds / Half minute  (Simulation Frequency)
        boolean checker = true;     // Needed for checking

        ArrayList<LinkedQueue<CustomerInfo>> expressQueue = new ArrayList<>();
        ArrayList<LinkedQueue<CustomerInfo>> normalQueue = new ArrayList<>();

        int expressQueueCount;
        int normalQueueCount;

        int expressQueueTimeCount = 0;
        int normalQueueTimeCount = 0;
        int queueLines = 0;

        //final String filename = "src/CustomerData_Example.txt";
        final String filename = "src/CustomerData.txt";

        // Part A
        try{
            Scanner fin = new Scanner(new File(filename));
            expressQueueCount = fin.nextInt();
            normalQueueCount = fin.nextInt();
            int numOfItems = fin.nextInt();
            int numOfCustomers = fin.nextInt();

            for(int i = 0; i < normalQueueCount; i++){
                normalQueue.add(new LinkedQueue<>());
            }
            for(int j = 0; j < expressQueueCount; j++){
                expressQueue.add(new LinkedQueue<>());
            }

            while(fin.hasNext()){
                CustomerInfo newCust = new CustomerInfo(fin.nextInt());
                if(checker){
                    if(!expressQueue.isEmpty() && !normalQueue.isEmpty()){
                        for(int i = 0; i < expressQueue.size(); i++){
                            if(newCust.getNumOfItems() <= numOfItems && expressQueue.get(i).size() == 0){
                                expressQueue.get(i).enqueue(new CustomerInfo(newCust.getNumOfItems()));
                                expressQueue.get(i).peek().setStartTime(expressQueueTimeCount);
                                expressQueueTimeCount += expressQueue.get(i).peek().timeToServeCustomer();
                                break;
                            }
                            for(int j = 0; j < normalQueue.size(); j++){
                                if(normalQueue.get(j).size() == 0){
                                    normalQueue.get(j).enqueue(new CustomerInfo(newCust.getNumOfItems()));
                                    normalQueue.get(j).peek().setStartTime(normalQueueTimeCount);
                                    normalQueueTimeCount += normalQueue.get(j).peek().timeToServeCustomer();

                                    if(j == normalQueue.size() - 1){
                                        checker = false;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                else{
                    int express = expressQueueTimeCount;
                    int normal = normalQueueTimeCount;

                    int expCount = 0;
                    int normCount = 0;

                    for(int i = 0; i < expressQueue.size(); i++){
                        expressQueueTimeCount = 0;
                        for(int j = 0; j < expressQueue.get(i).size(); j++){
                            CustomerInfo cust = expressQueue.get(i).dequeue();
                            cust.setStartTime(expressQueueTimeCount);
                            expressQueueTimeCount += cust.timeToServeCustomer();
                            expressQueue.get(i).enqueue(cust);
                        }

                        if(expressQueueTimeCount < express){
                            express = expressQueueTimeCount;
                            expCount = i;
                        }

                        for(int k = 0; k < normalQueue.size(); k++){
                            normalQueueTimeCount = 0;
                            for(int l = 0; l < normalQueue.get(i).size(); l++){
                                CustomerInfo cust = normalQueue.get(k).dequeue();
                                cust.setStartTime(normalQueueTimeCount);
                                normalQueueTimeCount += cust.timeToServeCustomer();
                                normalQueue.get(k).enqueue(cust);
                            }
                            if(normalQueueTimeCount < normal){
                                normal = normalQueueTimeCount;
                                normCount = k;
                            }
                        }
                    }
                    if(express < normal && newCust.getNumOfItems() <= numOfItems){
                        expressQueue.get(expCount).enqueue(new CustomerInfo(newCust.getNumOfItems()));
                    }else{
                        normalQueue.get(normCount).enqueue(new CustomerInfo(newCust.getNumOfItems()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        System.out.println("PART A - Checkout lines and time estimates for each line");
        int[] estExpress = new int[expressQueue.size()];
        int[] estNormal = new int[normalQueue.size()];

        for(int i = 0; i < expressQueue.size(); i++){
            expressQueueTimeCount = 0;
            for(int j = 0; j < expressQueue.get(i).size(); j++){
                CustomerInfo cust = expressQueue.get(i).dequeue();
                cust.setStartTime(expressQueueTimeCount);
                expressQueueTimeCount += cust.timeToServeCustomer();
                expressQueue.get(i).enqueue(cust);
                if(j == expressQueue.get(i).size() - 1){
                    estExpress[i] = expressQueueTimeCount;
                }
            }
            if(expressQueueTimeCount > queueLines){
                queueLines = expressQueueTimeCount;
            }

            System.out.printf("CheckOut (Express) #%d (Est Time = %d s) = %s \n", i + 1, estExpress[i], expressQueue.get(i));
        }

        for(int i = 0; i < normalQueue.size(); i++){
            normalQueueTimeCount = 0;
            for(int j = 0; j < normalQueue.get(i).size(); j++){
                CustomerInfo cust = normalQueue.get(i).dequeue();
                cust.setStartTime(normalQueueTimeCount);
                normalQueueTimeCount += cust.timeToServeCustomer();
                normalQueue.get(i).enqueue(cust);
                if(j == normalQueue.get(i).size() - 1){
                    estNormal[i] = normalQueueTimeCount;
                }
            }
            if(normalQueueTimeCount > queueLines){
                queueLines = normalQueueTimeCount;
            }

            System.out.printf("CheckOut (Normal ) #%d (Est Time = %d s) = %s \n", i + 1, estNormal[i], normalQueue.get(i));
        }

        System.out.printf("Time to clear store of all customers = %d s\n\n\n", queueLines);

        // Part B: Simulate the removal of customers from each check-out by servicing the customers.
        // It is suggested you set up a loop to calculate the state of the Checkout lines after each second,
        // But only display lines every 30 or 60 seconds simulated.

        System.out.printf("PART B - Number of customers in line after %d seconds\n", PRINT_FREQUENCY);
        System.out.println("  ts       Line 1    Line 2     Line 3     Line 4     Line 5     Line 6     Line 7    Line 8     Line 9     Line 10");

        // t = time
        for(int t = 1; t <= queueLines; t+= SIMULATION_STEP){
            if(t % PRINT_FREQUENCY == 0){
                System.out.printf("%4d", t);
            }
            int expressServed;
            for(int i = 0; i < expressQueue.size(); i++){
                for(int j = 0; j < expressQueue.get(i).size(); j++){
                    CustomerInfo cust = expressQueue.get(i).peek();
                    expressServed = cust.getStartTime() + cust.timeToServeCustomer();
                    if(expressServed == t){
                        expressQueue.get(i).dequeue();
                    }
                }
                if(t % PRINT_FREQUENCY == 0){
                    if(i < expressQueue.size()){
                        System.out.printf("%10d", expressQueue.get(i).size());
                    }
                }
            }

            int normalServed;
            for(int k = 0; k < normalQueue.size(); k++){
                for(int j = 0; j < normalQueue.get(k).size(); j++){
                    CustomerInfo cust = normalQueue.get(k).peek();
                    normalServed = cust.getStartTime() + cust.timeToServeCustomer();
                    if(normalServed == t){
                        normalQueue.get(k).dequeue();
                    }
                }

                if(t % PRINT_FREQUENCY == 0){
                    if(k < normalQueue.size()){
                        System.out.printf("%11d", normalQueue.get(k).size());
                    }

                    if(k == normalQueue.size() - 1){
                        System.out.println();
                    }
                }
            }
        }

    }


}
