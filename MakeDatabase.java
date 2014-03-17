import com.almworks.sqlite4java.*;
import java.io.File;

public class MakeDatabase {

    public static void main(String[] args) {
	SQLiteConnection db = new SQLiteConnection(new File("./database"));

	String s = "CREATE TABLE Quote ("
	    + "ID varchar(30), Ticker varchar(5), BigInt DATETIME," +
	    "Mood varchar(30), Keyword varchar(30), Volume int, Adj_close Decimal(4,2) );";

	//String i = "INSERT INTO Quote VALUES ('alphanotomega', 'CKG', " +
	 //   "'1970-01-01 00:00:01', 'excited', 'violin', 42, 0002.44)";
	String i = "INSERT INTO Quote VALUES (?, ?, " +
	    "2048, 'excited', 'violin', 42, 0002.44)";

	String q = "SELECT ID, Ticker FROM Quote";
	try {
	    db.open(true);
	    SQLiteStatement st;
	    //st = db.prepare(s);
	    //st.step();
	    //st.dispose();
	    st = db.prepare(i);
	    st.bind(1, "rtephenlee").bind(2, "JNS");
	    st.step();
	    st.dispose();
	    st = db.prepare(q);
	    while(st.step()) {
		System.out.println(st.columnString(0) + " " + st.columnString(1));
	    }
	} catch (SQLiteException ex) {
	    ex.printStackTrace();
	}
    }
}
