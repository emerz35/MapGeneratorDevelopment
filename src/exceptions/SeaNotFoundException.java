package exceptions;

/**
 
 * @author Tuesday
 */
public class SeaNotFoundException extends Exception{
    public SeaNotFoundException(){
        super("No Sea point has been found.");
    }
    
    public SeaNotFoundException(String str){
        super(str);
    }
}
