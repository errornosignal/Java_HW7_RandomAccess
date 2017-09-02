/** Created by Reid Nolan on 5/3/2017
 * RandomAccess Class
 * Class for random-access manipulation of records in a database
 * @author Reid Nolan
 * @since 05/03/2017
 * @version 1.0
 */
public class RandomAccess
{
    private static ProductDAO productDAO = new ProductDAOImpl();

    /**
     * Main method for RandomAccess Class
     * @param args args
     */
    public static void main(String[] args)
    {
        //display program header and main menu
        System.out.println("Reid_Nolan_HW7_[RandomAccess]");
        System.out.println();
        System.out.println("-----MAIN MENU-----");
        System.out.println("- s)how all records");
        System.out.println("- f)ind record");
        System.out.println("- a)dd record");
        System.out.println("- p)rice change");
        System.out.println("- q)uantity change");
        System.out.println("- e)xit");
        System.out.println();

        while (true)
        {
            //initialize variable to user input
            final String kCOMMAND = Validator.getString("Selection> ").toLowerCase();
            if (kCOMMAND.equalsIgnoreCase("s"))
            {
                readAllRecords(); //read and output all records in database
            }
            else
                if (kCOMMAND.equals("f"))
                {
                    searchRecord(); //search for a user specified record
                }
                else
                    if (kCOMMAND.equals("a"))
                    {
                        addRecord(); //add a record to the database
                    }
                    else
                        if (kCOMMAND.equals("p"))
                        {
                            updatePrice(); //update the price value of a user specified record
                        }
                        else
                            if (kCOMMAND.equals("q"))
                            {
                                updateQuantity(); //update the quantity value of a user specified record
                            }
                            else
                                if (kCOMMAND.equals("e"))
                                {
                                    //exit program
                                    System.out.println("\nExiting Program....");
                                    break;
                                }
                                else
                                {/*do nothing*/}
        }
    }

    /**
     * read and output all records in database
     */
    private static void readAllRecords()
    {
        productDAO.readAllRecords();
    }

    /**
     * search for a specific record
     */
    private static void searchRecord()
    {
        productDAO.searchRecord(null, "", false);
    }

    /**
     * add a record to the database
     */
    private static void addRecord()
    {
        productDAO.addRecord();
    }

    /**
     * //update the price value of a user specified record
     */
    private static void updatePrice()
    {
        productDAO.updatePrice();
    }

    /**
     * //update the quantity value of a user specified record
     */
    private static void updateQuantity()
    {
        productDAO.updateQuantity();
    }
}