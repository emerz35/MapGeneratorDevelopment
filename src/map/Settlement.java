package map;

/**
 *
 * @author Tuesday
 */
public class Settlement {
    
    public enum SettlementType{
        VILLAGE,TOWN,CITY,CAPITAL_CITY;
    }
    
    public SettlementType type;
    
    public int x,y;
    
    public Settlement(SettlementType type, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
