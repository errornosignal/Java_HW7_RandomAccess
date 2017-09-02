/** Created by Reid Nolan on 5/3/2017
 * ProductDAO Interface
 * Interface for ProductDAOImpl for random-access manipulation of records in a database
 * @author Reid Nolan
 * @since 05/03/2017
 * @version 1.0
 */
interface ProductDAO
{
    /**
     * reads and outputs all records in database
     */
    void readAllRecords();

    /**
     * returns a record matching the search parameters
     * @param kPRODUCT_NAME kPRODUCT_NAME
     * @param kCONDITION kCONDITION
     * @param kADD kTRUE
     */
    void searchRecord(final String kPRODUCT_NAME, final String kCONDITION, boolean kADD);

    /**
     * adds a record to the database
     */
    void addRecord();

    /**
     * updates the price value of the record
     */
    void updatePrice();

    /**
     * updates the quantity value of the record
     */
    void updateQuantity();
}