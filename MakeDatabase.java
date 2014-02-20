import com.almworks.sqlite4java.*;

public static void main(String[] args) {
    SQLiteConnection db = new SQLiteConnection(new File("./database"));
    db.open(true);

    String s = "CREATE TABLE Quote ("
	+ "ID varchar(30), Ticker varchar(5), Timestamp DATETIME," +
       	"Mood varchar(30), Keyword varchar(30), Volume int, Adj_close Decimal(4,2) );";
    SQLiteSTatement st = db.prepare(s);
}
