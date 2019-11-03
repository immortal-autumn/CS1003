import java.io.IOException;
import java.sql.SQLException;

public class W07Practical {
    public static void main(String[] args) throws SQLException, IOException{
        if(args.length == 3){
            JDBCsetting csetting = new JDBCsetting(args[0],args[1],args[2]);
        }
        else {
            if(args.length == 2){
                JDBCsetting csetting = new JDBCsetting(args[0], args[1]);
            }
            else {
                System.out.println("Usage: java -cp sqlite-jdbc.jar:. W07Practical <db_file> <action> [input_file]");
                System.exit(-1);
            }
        }
    }
}
