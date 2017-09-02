import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

/** Created by Reid Nolan on 3/3/2017
 * Validator Class
 * Class for validating user input
 * @author Reid Nolan
 * @since 03/03/2017
 * @version 1.03
 */
class Validator
{
    /**
     * returns validated user input String
     * @param kPROMPT kPROMPT
     * @return inputString
     */
    static String getString(final String kPROMPT)
    {
        //declare local variable
        String inputString = "";
        //create new scanner
        Scanner kSC = new Scanner(System.in);
        //loop until string is valid
        boolean stringIsValid = false;
        while(!stringIsValid)
        {
            //prompt for input
            System.out.print(kPROMPT);
            //assign next scanner input string to variable
            inputString = kSC.nextLine();
            //verify input is entered
            if (inputString.isEmpty())
            {/*do nothing*/}
            else
            {
                //set boolean to true to exit loop
                stringIsValid = true;
            }
        }
        return inputString;
    }

    /**
     * returns validated user input BigDecimal
     * @param kPROMPT kPROMPT
     * @return inputBigDecimal
     */
    static BigDecimal getBigDecimal(String kPROMPT)
    {
        //declare local variable
        BigDecimal inputBigDecimal = BigDecimal.ZERO;
        //loop until double is valid
        boolean bigDecimalIsValid = false;
        while(!bigDecimalIsValid)
        {
            try
            {
                //create new scanner
                final Scanner kSC = new Scanner(System.in);
                //prompt for input
                System.out.print(kPROMPT);
                //assign next scanner input double to variable
                inputBigDecimal = kSC.nextBigDecimal();
                //set boolean to true to exit loop
                bigDecimalIsValid= true;
            }
            catch(InputMismatchException IMEx)
            {/*do nothing*/}
        }
        return inputBigDecimal;
    }

    /**
     * returns validated user input double
     * @param kPROMPT kPROMPT
     * @return inputDouble
     */
    static Double getDouble(String kPROMPT)
    {
        //declare local variable
        Double inputDouble = 0.0;
        //loop until double is valid
        boolean doubleIsValid = false;
        while(!doubleIsValid)
        {
            try
            {
                //create new scanner
                Scanner kSC = new Scanner(System.in);
                //prompt for input
                System.out.print(kPROMPT);
                //assign next scanner input double to variable
                inputDouble = kSC.nextDouble();
                //set boolean to true to exit loop
                doubleIsValid = true;
            }
            catch(InputMismatchException IMEx)
            {/*do nothing*/}
        }
        return inputDouble;
    }

    /**
     * returns validated user input int
     * @param kPROMPT kPROMPT
     * @return inputInt
     */
    static int getInt(String kPROMPT)
    {
        //declare local variable
        int inputInt = 0;
        //loop until int is valid
        boolean intIsValid = false;
        while(!intIsValid)
        {
            try
            {
                //create new scanner
                Scanner kSC = new Scanner(System.in);
                //prompt for input
                System.out.print(kPROMPT);
                //assign next scanner input int to variable
                inputInt = kSC.nextInt();
                //set boolean to true to exit loop
                intIsValid = true;
            }
            catch(InputMismatchException IMEx)
            {/*do nothing*/}
        }
        return inputInt;
    }
}