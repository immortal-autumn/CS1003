import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class JDBCsetting {

    private String DBpath;
    private String action;
    private String readFile;

    /**
     * Consturctor for query 1--4
     * Prevent the array index out of boundary
     * @param DBpath
     * @param action
     * @throws SQLException
     */
    public JDBCsetting(String DBpath, String action) throws SQLException, IOException{
        this.DBpath = DBpath;
        this.action = action;
        dBconnection();
    }

    /**
     * Constructor is used to find the path of database, define action and read the CSV file.
     * Only available for creat
     * @param DBpath
     * @param action
     * @param readFile
     */
    public JDBCsetting(String DBpath, String action,String readFile) throws SQLException, IOException{
        this.DBpath = DBpath;
        this.action = action;
        this.readFile = readFile;
        dBconnection();
    }

    /**
     * dbConnection is used to connect to Database.
     * @throws SQLException
     */
    private void dBconnection() throws SQLException, IOException{
        Connection connection = null;
        try{
            //connect to database manage system
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBpath);
            action(connection);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            if(connection != null) connection.close();
        }
    }

    /**
     * Creat table is to creat the table in create command
     * If the table exist , program will drop the table and then create a new one
     * @param connection
     * @throws SQLException
     */
    private void createTable(Connection connection) throws SQLException, IOException{
        Statement statement = connection.createStatement();

        //Create a table includes name, sex, tickets ......

        statement.executeUpdate("DROP TABLE IF EXISTS person");
        statement.executeUpdate("CREATE TABLE person (passengerID INT, survived INT, pClass INT, name VARCHAR(100), " +
                "sex VARCHAR(100), age FLOAT, sibSp INT, parch INT, ticket VARCHAR (100), fare FLOAT, cabin VARCHAR(100), " +
                "embarked VARCHAR(100))");

        //read the CSV file and INSERT all data into DB
        List<List_of_CSV> list_of_csv = readFile();
//        System.out.println(list_of_csv);
        for(List_of_CSV s : list_of_csv) {
            insertData(s.getPassengerId(),s.getSurvived(),s.getpClass(),s.getName(),s.getSex(),s.getAge(),s.getSibSp(),
                    s.getParch(),s.getTicket(),s.getFare(), s.getCabin(),s.getEmbarked(),connection);
//            System.out.println(s.getAge());
        }

    }

    /**
     * Insert method to insert all data to database
     * Issue2 happens at 22:39 Tue <-
     * @param passengerID
     * @param survived
     * @param pClass
     * @param name
     * @param sex
     * @param age
     * @param sibSp
     * @param parch
     * @param ticket
     * @param fare
     * @param cabin
     * @param embarked
     * @param connection
     * @throws SQLException
     */
    private void insertData(int passengerID,int survived, int pClass, String name, String sex, Double age,
                             int sibSp, int parch, String ticket, double fare, String cabin, String embarked,
                            Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO person VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setInt(1, passengerID);
        statement.setInt(2, survived);
        statement.setInt(3, pClass);
        statement.setString(4, name);
        statement.setString(5, sex);
        if(age == 0){
            statement.setNull(6, Types.FLOAT);
//            System.out.println(age);
        }
        else {
            statement.setDouble(6, age);
        }
        statement.setInt(7, sibSp);
        statement.setInt(8, parch);
        statement.setString(9, ticket);
        statement.setDouble(10, fare);
        statement.setString(11, cabin);
        statement.setString(12, embarked);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * This method is to read the CSV file and store it into the List<String> ArrayList
     * @return
     * @throws IOException
     */
    private List<List_of_CSV> readFile() throws IOException{
        BufferedReader reader = null;
        List<List_of_CSV> list_of_csv = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(readFile));
            String title = reader.readLine();

            String line = "";
            while((line = reader.readLine()) != null){
                ArrayList<String> fields = new ArrayList();
                String[] elements = line.split(",");

                for(String s : elements){
                    fields.add(s);
                }

                list_of_csv.add(new List_of_CSV(fields));
            }
//            System.out.println(1);
        }
        catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
        finally {
            reader.close();
        }
        return list_of_csv;
    }

    private void createView(Connection connection, String queryName, String columnName) throws SQLException{
        Statement statement = connection.createStatement();
        String drop = "DROP VIEW IF EXISTS " + queryName;
        String query = "CREATE VIEW " + queryName + " AS SELECT " + columnName + " FROM person";
        statement.executeUpdate(drop);
        statement.executeUpdate(query);
    }

    /**
     *This method is for query 2 and the total number of Titanic will be calculated by using while command
     * query2: print out the total number of survivors of the Titanic
     * @param connection
     * @throws SQLException
     */
    private void totalNumberOfTitanic(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        String query_name = "sur_view";
        createView(connection,query_name, "survived");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + query_name);

        int survived = 0;
        while (resultSet.next()){
            survived += resultSet.getInt("survived");
        }
        System.out.println("Number of Survivors");
        System.out.println(survived);
    }

    /**
     * This method is used to print all data
     * All the data will be shown by System.out.println
     * list all the records in the database
     * @param connection
     * @throws SQLException
     */
    private void printAllData(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
        String title = "passengerId, survived, pClass, name, sex, age, sibSp, parch, ticket, fare, cabin, embarked";

        //System.out.println(resultSet.getInt("pClass"));
        System.out.println(title);
        while (resultSet.next()){
            int pClass = resultSet.getInt("pClass");
            int survived = resultSet.getInt("survived");
            int passengerID = resultSet.getInt("passengerId");
            String name = resultSet.getString("name");
            String sex = resultSet.getString("sex");
            String ticket = resultSet.getString("ticket");
            String cabin = resultSet.getString("cabin");
            String embarked = resultSet.getString("embarked");
            Double age = resultSet.getDouble("age");
            double fare = resultSet.getDouble("fare");
            int sibSp = resultSet.getInt("sibSp");
            int parch = resultSet.getInt("parch");

            System.out.println(passengerID + ", " + survived + ", " + pClass + ", " + name + ", " +
                    sex + ", " + (age == 0.0 ? "null" : age) + ", " + sibSp + ", " + parch + ", " + ticket + ", " + fare + ", " + cabin + ", "
                    + embarked);
        }
    }

    /**
     * Some issues occurred in this method<- seems solved wed 16.04
     * Group By and COUNT to print the data
     * query3: list pClass, survived, and a count of passengers in each class and survival category
     * @param connection
     * @throws SQLException
     */
    private void query3(Connection connection) throws SQLException{
        //Issue 1 <- 20.59 Sun
        Statement statement = connection.createStatement();
        String queryName = "query3";
        createView(connection, queryName, "pClass, survived");

//        String query = "SELECT pClass, survived, COUNT (pClass||survived) as counts FROM person GROUP by pClass, survived";
        String query = "SELECT *, COUNT (pClass||survived) as counts FROM " + queryName + " GROUP by pClass, survived";
        ResultSet resultSet;
        resultSet = statement.executeQuery(query);
        System.out.println("pClass, survived, count");

        while (resultSet.next()){

            int counts = resultSet.getInt("counts");
            int pClass = resultSet.getInt("pClass");
            int survived = resultSet.getInt("survived");

            System.out.println(pClass + ", " + survived + ", " + counts);
        }
    }

    /**
     * list sex, survived, and minimum age of passengers in each sex and survival category
     * Issue3 happens in Tue 23:40<-
     * @throws SQLException
     */
    private void query4(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String query4 = "query4";
        createView(connection, query4, "sex, survived");
        String query = "SELECT *, MIN(age) as min_age FROM person GROUP BY sex , survived";
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println("sex, survived, minimum age");

        while(resultSet.next()){
            String sex = resultSet.getString("sex");
            int survived = resultSet.getInt("survived");
            double age = resultSet.getDouble("min_age");

            System.out.println(sex + ", " + survived + ", "+ age);
        }
    }

    /**
     * This action use select to make users can select what they want and shows them
     * @param connection
     * @throws SQLException
     */
    private void addiAction(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT " + action + " FROM person";
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        int count = resultSetMetaData.getColumnCount();
        String type = null;
//        System.out.println(count);

        while (resultSet.next()){
            String result = "";
            for(int i=1;i<=count;i++) {
                type = resultSetMetaData.getColumnTypeName(i);
//                System.out.println(type);
                switch (type) {
                    case "VARCHAR": {
                        String value = resultSet.getString(i);
                        result += " " + value;
                        break;
                    }
                    case "FLOAT": {
                        double value = resultSet.getDouble(i);
                        result += " " + (value == 0.0 ? "null" : value);
                        break;
                    }
                    case "INT": {
                        int value = resultSet.getInt(i);
                        result += " " + (value == 0.0 ? "null" : value);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            System.out.println(result);
        }
    }

    /**
     * This method is the method to use switch to judge the action from args[1]
     * @throws SQLException
     */
    private void action(Connection connection) throws SQLException, IOException {
        try {
            switch (action) {
                case "create": {
                    createTable(connection);
                    System.out.println("OK");
                    break;
                }
                case "query1": {
                    printAllData(connection);
                    break;
                }
                case "query2": {
                    totalNumberOfTitanic(connection);
                    break;
                }
                case "query3": {
                    query3(connection);
                    break;
                }
                case "query4": {
                    query4(connection);
                    break;
                }
                default: {
//                    System.out.println("Usage: java -cp sqlite-jdbc.jar:. W07Practical <db_file> <action> [input_file]");
                    addiAction(connection);
                    break;
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            System.out.print("Input example: ");
            System.out.println("create / query(1-4) / \"name of column\"(Please split the column with ',')");
        }
    }

}
