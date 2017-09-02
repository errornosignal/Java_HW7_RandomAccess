/** Created by Reid Nolan on 5/03/2017.
 * Product Class
 * Class for storing and retrieving product object data
 * @author Reid Nolan
 * @since 05/03/2017
 * @version 1.0
 */
class Product
{
    private String productName;
    private double price;
    private int quantity;

    /**
     * Constructors for Product Class
     * @param productName productName
     * @param price       price
     * @param quantity    quantity
     */
    Product(String productName, double price, int quantity)
    {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * gets productName variable
     * @return productName
     */
    String getProductName()
    {
        return productName;
    }

    /**
     * gets price variable
     * @return price
     */
    double getPrice()
    {
        return price;
    }

    /**
     * gets quantity variable
     * @return quantity
     */
    int getQuantity()
    {
        return quantity;
    }
}