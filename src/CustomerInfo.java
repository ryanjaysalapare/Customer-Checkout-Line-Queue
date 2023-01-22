/**
 * CustomerInfo Class that creates an object of type CustomerInfo
 */
public class CustomerInfo {

    private int numOfItems;
    private int startTime = -1;

    /**
     * Constructor for CustomerInfo Class that creates an object of type CustomerInfo
     * @param numOfItems number of items
     */
    public CustomerInfo(int numOfItems){
        this.numOfItems = numOfItems;
    }

    /**
     * Method that will compute the time to serve a single customer (t = 45 + 5 * ni)
     * Where t = time in seconds that it takes to serve a customer
     * Where ni = the number of items in the customers cart
     * @return The time to serve a single customer
     */
    public int timeToServeCustomer(){
        return 45 + 5 * numOfItems;
    }

    /**
     * Method that will get the number of items in the customers cart.
     * @return The number of items in the customers cart
     */
    public int getNumOfItems(){
        return numOfItems;
    }

    /**
     * Method that gets the start time when a customer enters a checkout queue
     * @return The start time when a customer enters a checkout queue
     */
    public int getStartTime(){
        return startTime;
    }

    /**
     * Method that sets the start time when a customer enters a checkout queue
     * @param startTime The start time when a customer enters a checkout queue
     */
    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    /**
     * String representation of the CustomerInfo object
     * @return String output of Number of items and Total Served Time of a Customer after checking out of a queue
     */
    @Override
    public String toString() {
        return String.format(" Number of items = %d | Total Time Served = %d s", numOfItems, timeToServeCustomer());
    }
}
