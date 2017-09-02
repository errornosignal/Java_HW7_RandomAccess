import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/** Created by Reid Nolan on 5/3/2017
 * RandomAccessDAOImpl Class
 * RandomAccessDAO Implementation Class for random-access manipulation of records in a database
 * @author Reid Nolan
 * @since 05/03/2017
 * @version 1.00
 */
public class ProductDAOImpl implements ProductDAO
{
    private final String kDATA_FILE = "products.dat"; //file name to read/write from/to
    private final int kSTRING_BYTES = 60; //product string length in bytes
    private final int kRECORD_SIZE_IN_BYTES = 72; //72 = 60(string)+8(double+4(int) bytes written to file per record
    private final int kUTF_STRING_MODIFIER = 2; //UTF adds 2 bytes per string
    private final int kUTF_STRING_SIZE = kSTRING_BYTES + kUTF_STRING_MODIFIER; //total string size in bytes
    private final String kUPDATE_ENTRY_COND = "updateEntry"; //condition argument for searchRecord()
    private boolean matchExists = false;

    /**
     * reads and outputs all records in database
     */
    @Override
    public void readAllRecords()
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile((new File(kDATA_FILE)), "rw"); //open file for read/write

            if (file.length() < kRECORD_SIZE_IN_BYTES) //check file for content
            {
                System.out.println("\nNo records exist in the database."); //prompt user to populate database if no records exist
            }
            else
            {
                final long kNUM_RECORDS = file.length() / kRECORD_SIZE_IN_BYTES; //calculate number of records in database

                file.seek(0); //start at beginning of file

                System.out.println("\nDisplaying all records in database:"); //display all records in database
                String leftAlignFormat = "| %-28s | %16s | %10d |%n";
                System.out.format("+------------------------------+------------------+------------+%n");
                System.out.format("| Product                      | Price            | Qty        |%n");
                System.out.format("+------------------------------+------------------+------------+%n");
                for (int i = 0; i < kNUM_RECORDS; i++)
                {
                    final String kPRODUCT = file.readUTF(); //read product string in record (byte-by-byte)
                    for (int j = 0; j < kSTRING_BYTES - kPRODUCT.length(); j++)
                    {
                        file.readByte();
                    }
                    final double kPRICE = file.readDouble(); //read price double in record
                    final int kQUANTITY = file.readInt(); //read quantity int value in record

                    Product product = new Product(kPRODUCT, kPRICE, kQUANTITY); //create new object

                    System.out.format(leftAlignFormat, product.getProductName(), String.format("$%,.2f", product.getPrice()), product.getQuantity());
                }
                System.out.format("+------------------------------+------------------+------------+%n");
            }

            file.close(); //close database file
            System.out.println();
        }
        catch (IOException IOEx)
        {
            IOEx.getStackTrace();
        }
    }

    /**
     * record search
     * @param kPRODUCT_NAME kPRODUCT_NAME
     * @param kCONDITION kCONDITION
     * @param kADD kTRUE
     */
    @Override
    public void searchRecord(final String kPRODUCT_NAME, final String kCONDITION, boolean kADD)
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile((new File(kDATA_FILE)), "rw"); //open file for read/write
            final long kNUM_RECORDS = file.length() / kRECORD_SIZE_IN_BYTES; //calculate number of records in database
            final String kADD_ENTRY_COND = "addEntry"; //define entry addition condition
            final String kUPDATE_ENTRY_COND = "updateEntry"; //define entry update condition
            String search = kPRODUCT_NAME; //define search,(kPRODUCT_NAME = "")
            matchExists = false;

            file.seek(0); //start at beginning of file

            if (kCONDITION.equals("")) //check for addition or update condition
            {
                search = Validator.getString("Product to search> ").toLowerCase(); //get search from user
            }
            else{/*do nothing*/}

            for (int i = 0; i < kNUM_RECORDS; i++) //loop through records
            {
                final String kPRODUCT = file.readUTF(); //read product string in record (byte-by-byte)
                for (int j = 0; j < kSTRING_BYTES - kPRODUCT.length(); j++)
                {
                    file.readByte();
                }
                final double kPRICE = file.readDouble(); //read price double in record
                int kQUANTITY = file.readInt(); //read quantity int value in record

                Product product = new Product(kPRODUCT, kPRICE, kQUANTITY); //create new object

                if (search.equals(product.getProductName()) && kCONDITION.equals(kUPDATE_ENTRY_COND) && !kADD) //record updated
                {
                    System.out.println("\nRecord updated:"); //display updated record
                    displayRecord(product);
                    matchExists = true;
                    break;
                }
                else if (search.equals(product.getProductName()) && kCONDITION.equals(kADD_ENTRY_COND) && kADD) //disallow duplicate entries
                {
                    System.out.println("\nRecord already exists:"); //display duplicate record
                    displayRecord(product);
                    matchExists = true;
                    break;
                }
                else if(search.equals(product.getProductName()) && (kCONDITION.equals(""))) //match for single record
                {
                    System.out.println("\nMatching record found:"); //display record match
                    displayRecord(product);
                    matchExists = true;
                    break;
                }
                else if (kCONDITION.equals("") && (i == kNUM_RECORDS - 1)) //check against no matches
                {
                    System.out.println("\nNo matching record found."); //display no matching record
                }
                else {/*do nothing*/}
            }

            file.close(); //close database file
            System.out.println();
        }
        catch (IOException IOEx)
        {
            IOEx.getStackTrace();
        }
    }

    /**
     * adds a record to the database
     */
    @Override
    public void addRecord()
    {
        try
        {
            final String kPRODUCT = Validator.getString("New product name> ").toLowerCase(); //get new product name from user
            final double kPRICE = Validator.getDouble("Price> "); //get new product price from user
            final int kQUANTITY = Validator.getInt("Quantity> "); //get new product quantity from user

            Product product = new Product(kPRODUCT, kPRICE, kQUANTITY); //create new object

            final String kADD_ENTRY_COND = "addEntry"; //condition argument for addRecord()
            searchRecord(product.getProductName(), kADD_ENTRY_COND, true); //search for duplicate record

            if(!matchExists) //if duplicate record does not exist, add a new record
            {
                RandomAccessFile file = new RandomAccessFile((new File(kDATA_FILE)), "rw"); //open file for read/write
                final long kFILE_SIZE = file.length(); //get end of file

                file.seek(kFILE_SIZE); //seek to end of file

                file.writeUTF(product.getProductName()); //write product string in record (byte-by-byte)
                for (int j = 0; j < kSTRING_BYTES - product.getProductName().length(); j++)
                {
                    file.writeByte(kSTRING_BYTES);
                }
                file.writeDouble(product.getPrice()); //write price double in record
                file.writeInt(product.getQuantity()); //write quantity int value in record
                file.close(); //close database file

                System.out.println("Record added:"); //display record added
                displayRecord(product);
                System.out.println();
            }
        }
        catch (IOException IOEx)
        {
            IOEx.getStackTrace();
        }
    }

    /**
     * updates the price value of the record
     */
    @Override
    public void updatePrice()
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile((new File(kDATA_FILE)), "rw"); //open file for read/write
            final long kNUM_RECORDS = file.length() / kRECORD_SIZE_IN_BYTES; //calculate number of records in database
            final double kMIN_PRICE = 0.01; //set min price value
            int matchCount = 0; //set counter for matches

            file.seek(0); //start at beginning of file

            String search = Validator.getString("Product to update> ").toLowerCase(); //get new product name from user

            for (int i = 0; i < kNUM_RECORDS; i++) //loop through records
            {
                final String kPRODUCT = file.readUTF(); //read product string in record (byte-by-byte)
                for (int j = 0; j < kSTRING_BYTES - kPRODUCT.length(); j++)
                {
                    file.readByte();
                }
                double price = file.readDouble(); //read price double in record
                final int kQUANTITY = file.readInt(); //read quantity int value in record

                Product product = new Product(kPRODUCT, price, kQUANTITY); //create new object

                if (search.equals(kPRODUCT))
                {
                    System.out.println("\nMatching record found:");
                    displayRecord(product);

                    boolean priceIsValid = false;
                    while(!priceIsValid)
                    {
                        price = Validator.getDouble("Updated Price> "); //check against min price value
                        if(price >= kMIN_PRICE) //check against minimum price value
                        {
                            priceIsValid = true;
                        }
                        else
                        {
                            System.out.println("Value must be $" + kMIN_PRICE + " or greater."); //display invalid value message
                        }
                    }
                    file.seek((kRECORD_SIZE_IN_BYTES * (i)) + (kUTF_STRING_SIZE + (i * 2))); //get price value location in database file
                    file.writeDouble(price); //write product price to database record

                    searchRecord(kPRODUCT, kUPDATE_ENTRY_COND, false); //display updated record
                    matchCount++; //increment match counter
                } else{/*do nothing*/}
            }
            if (matchCount == 0) //if no match found
            {
                System.out.println("\nNo matching records found.\n");
            }

            file.close(); //close database file
        }
        catch (IOException IOEx)
        {
            IOEx.getStackTrace();
        }
    }

    /**
     * updates the quantity value of the record
     */
    @Override
    public void updateQuantity()
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile((new File(kDATA_FILE)), "rw"); //open file for read/write
            final long kNUM_RECORDS = file.length() / kRECORD_SIZE_IN_BYTES;
            final int kDOUBLE_SIZE_IN_BYTES = 8; //set size of double in bytes
            int matchCount = 0; //set counter for matches

            file.seek(0); //start at beginning of file

            String search = Validator.getString("Product to update> ").toLowerCase(); //get new product name from user

            for (int i = 0; i < kNUM_RECORDS; i++) //loop through records
            {
                final String kPRODUCT = file.readUTF(); //read product string in record (byte-by-byte)
                for (int j = 0; j < kSTRING_BYTES - kPRODUCT.length(); j++)
                {
                    file.readByte();
                }

                final double kPRICE = file.readDouble(); //read price double in record
                int quantity = file.readInt(); //read quantity int value in record

                Product product = new Product(kPRODUCT, kPRICE, quantity); //create new object

                if (search.equals(kPRODUCT))
                {
                    System.out.println("\nMatching record found:");
                    displayRecord(product);

                    quantity = Validator.getInt("Updated Quantity> ");

                    file.seek((kRECORD_SIZE_IN_BYTES * (i)) + (kUTF_STRING_SIZE + kDOUBLE_SIZE_IN_BYTES + (i * 2))); //get qty value location in database file
                    file.writeInt(quantity); //write product qty to database record

                    searchRecord(kPRODUCT, kUPDATE_ENTRY_COND, false); //display updated record
                    matchCount++; //increment match counter
                } else{/*do nothing*/}
            }
            if (matchCount == 0) //if no match found
            {
                System.out.println("No matching records found.\n");
            }

            file.close(); //close database file
        }
        catch (IOException IOEx)
        {
            IOEx.getStackTrace();
        }
    }

    /**
     * display tabular formatted records
     * @param product product
     */
    private void displayRecord(Product product)
    {
        String leftAlignFormat = "| %-28s | %16s | %10d |%n";
        System.out.format("+------------------------------+------------------+------------+%n");
        System.out.format("| Product                      | Price            | Qty        |%n");
        System.out.format("+------------------------------+------------------+------------+%n");
        System.out.format(leftAlignFormat, product.getProductName(), String.format("$%,.2f", product.getPrice()), product.getQuantity());
        System.out.format("+------------------------------+------------------+------------+%n");
    }
}