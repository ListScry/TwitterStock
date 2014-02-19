package YQL;

/**
 * Created by Stefan Anders Mellem on 2/18/14.
 */
public class YQLHistoricalData {
    public String Symbol;
    public String Date;
    public String Open;
    public String High;
    public String Low;
    public String Close;
    public String Volume;
    public String Adj_Close;

    public String toString(){
        return Symbol+Date+" "+Symbol+" "+Date+" "+High+" "+Low+" "+Open+" "+Close+" "+Volume+" "+Adj_Close;
    }
}
