import com.almworks.sqlite4java.*;
import java.io.File;

public class MakeDatabase {

    public static void main(String[] args) {
	SQLiteConnection db = new SQLiteConnection(new File("./database"));

	String s = "CREATE TABLE Quote ("
	    + "ID varchar(30), Ticker varchar(5), Timestamp DATETIME," +
	    "Mood varchar(30), Keyword varchar(30), Volume int, Adj_close Decimal(4,2) );";
	try {
	    db.open(true);
	    SQLiteStatement st = db.prepare(s);
	    st.step();
	    st.dispose();
	} catch (SQLiteException ex) {}
    }
}
