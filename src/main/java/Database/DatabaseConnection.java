package Database;
import Model.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public final class DatabaseConnection {
    private final Connection con;
    //zamknięcie połączenia z bazą
    //bez zamknięcia serwer się psuje
    public DatabaseConnection(String url, String login, String password) throws SQLException {
        this.con = DriverManager.getConnection(url,login,password);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("ZAMKNIETO BAZE");
                con.close();
            } catch (SQLException e) {
                System.out.println("ERROR !!!!NIE ZAMKNIETO BAZY");
            }
        }));
    }
    public void changeCustomer(long in_id_customer, String in_password, String in_name, String in_surname, String in_phone_number) throws SQLException
    {
        String sql_string = "CALL c##cinema.changeCustomer(?,?,?,?,?)";


        CallableStatement cs = con.prepareCall(sql_string);
        cs.setLong(1,in_id_customer);
        if(in_password.equals(""))
            cs.setNull(2, Types.VARCHAR);
        else
            cs.setString(2,in_password);
        cs.setString(3,in_name);
        cs.setString(4,in_surname);
        cs.setString(5,in_phone_number);

        cs.execute();
        cs.close();
    }
    public boolean changePositionName(long in_id_position, String in_name) // nie używany
    {
        String sql_string = "CALL c##cinema.changePositionName(?,?)";

        try {
            CallableStatement cs = con.prepareCall(sql_string);
            cs.setLong(1,in_id_position);
            cs.setString(2,in_name);

            cs.execute();
            cs.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    public boolean changePositionSalary(long in_id_position, int in_salary) // nie używany
    {
        String sql_string = "CALL c##cinema.changePositionSalary(?,?)";

        try {
            CallableStatement cs = con.prepareCall(sql_string);
            cs.setLong(1,in_id_position);
            cs.setInt(2,in_salary);

            cs.execute();
            cs.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    public boolean changeReservationToTeaken(long in_id_seat, long in_id_customer) // nie używany
    {
        String sql_string = "CALL c##cinema.changeReservationToTeaken(?,?)";

        try {
            CallableStatement cs = con.prepareCall(sql_string);
            cs.setLong(1,in_id_seat);
            cs.setLong(2,in_id_customer);

            cs.execute();
            cs.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    public boolean customerAccountDeactivation(long in_id_customer) throws SQLException //blokuje logowanie
    {
        String sql_string = "CALL c##cinema.customerAccountDeactivation(?)";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.setLong(1,in_id_customer);

        cs.execute();
        cs.close();

        return true;
    }
    public boolean deleteReservation(long in_id_seat, long in_id_customer)
    {
        String sql_string = "CALL c##cinema.deleteReservation(?,?)";

        try {
            CallableStatement cs = con.prepareCall(sql_string);
            cs.setLong(1,in_id_seat);
            cs.setLong(2,in_id_customer);

            cs.execute();
            cs.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    public boolean fireEmployee(long in_id_employee, Timestamp in_fire_date) throws SQLException
    {
        String sql_string = "CALL c##cinema.fireEmployee(?,?)";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.setLong(1,in_id_employee);
        cs.setTimestamp(2,in_fire_date);

        cs.execute();
        cs.close();

        return true;
    }
    public void insertCustomer(String in_login, String in_password, String in_name, String in_surname, String in_phone_number) throws SQLException
    {
        String sql_string = "CALL c##cinema.insertCustomer(?,?,?,?,?)";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.setString(1,in_login);
        cs.setString(2,in_password);
        cs.setString(3,in_name);
        cs.setString(4,in_surname);
        cs.setString(5,in_phone_number);

        cs.execute();
        cs.close();
    }
    public void insertEmployee(String in_login, String in_password, String in_name, String in_surname, String in_phone_number, Timestamp in_hire_date, long in_id_position) throws SQLException
    {
        String sql_string = "CALL c##cinema.insertEmployee(?,?,?,?,?,?,?)";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.setString(1,in_login);
        cs.setString(2,in_password);
        cs.setString(3,in_name);
        cs.setString(4,in_surname);
        cs.setString(5,in_phone_number);
        if(in_hire_date!=null)
            cs.setTimestamp(6,in_hire_date);
        else
            cs.setNull(6, Types.DATE);
        cs.setLong(7,in_id_position);
        cs.execute();
        cs.close();
    }
    public void insertMovie(String in_title, int in_length, String in_distributor, Timestamp in_borrow_date, Timestamp in_return_date, char in_dub_sub_lec, boolean in_is3D) throws SQLException
    {
        String sql_string = "CALL c##cinema.insertMovie(?,?,?,?,?,?,?)";
        Timestamp timestamp = new Timestamp( 2020,2,1,12,0,0,0);


        CallableStatement cs = con.prepareCall(sql_string);
        cs.setString(1,in_title);
        cs.setInt(2,in_length);
        cs.setString(3,in_distributor);
        cs.setTimestamp(4,in_borrow_date);
        cs.setTimestamp(5,in_return_date);
        cs.setString(6, Character.toString( in_dub_sub_lec));
        cs.setBoolean(7,in_is3D);

        cs.execute();
        cs.close();
    }
    public void insertPosition(String in_name, double in_salary) throws SQLException // nie używany
    {
        String sql_string = "CALL c##cinema.insertPosition(?,?)";


        CallableStatement cs = con.prepareCall(sql_string);
        cs.setString(1,in_name);
        cs.setDouble(2,in_salary);

        cs.execute();
        cs.close();
    }
    public void insertResTeakenSeats(char in_row_identifier, int in_column_identifier, long in_id_seance, long in_id_customer) throws SQLException
    {
        String sql_string = "CALL c##cinema.insertResTeakenSeats(?,?,?,?)";


        CallableStatement cs = con.prepareCall(sql_string);
        cs.setString(1, Character.toString(in_row_identifier));
        cs.setInt(2,in_column_identifier);
        cs.setLong(3,in_id_seance);
        cs.setLong(4,in_id_customer);

        cs.execute();
        cs.close();
    }
    public void insertScreeningRoom(int in_amount_of_rows, int in_amount_of_columns) throws SQLException // nie używany
    {
        String sql_string = "CALL c##cinema.insertScreeningRoom(?,?)";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.setInt(1,in_amount_of_rows);
        cs.setInt(2,in_amount_of_columns);

        cs.execute();
        cs.close();
    }
    public void insertSeance(Timestamp in_start_time, Timestamp in_end_time, double in_ticket_price, long in_id_movie, long in_id_screening_room) throws SQLException
    {
        String sql_string = "CALL c##cinema.insertSeance(?,?,?,?,?)";


        CallableStatement cs = con.prepareCall(sql_string);
        cs.setTimestamp(1,in_start_time);
        cs.setTimestamp(2,in_end_time);
        cs.setDouble(3,in_ticket_price);
        cs.setLong(4,in_id_movie);
        cs.setLong(5,in_id_screening_room);

        cs.execute();
        cs.close();
    }
    public void insertTeakenSeats(char in_row_identifier, int in_column_identifier, long in_id_seance, long in_id_transaction, long in_id_customer) throws SQLException
    {
        String sql_string = "CALL c##cinema.insertTeakenSeats(?,?,?,?,?)";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.setString(1, Character.toString(in_row_identifier));
        cs.setInt(2,in_column_identifier);
        cs.setLong(3,in_id_seance);
        if(in_id_transaction!=0)
            cs.setLong(4,in_id_transaction);
        else
            cs.setNull(4, Types.NUMERIC);
        if(in_id_customer!=0)
            cs.setLong(5,in_id_customer);
        else
            cs.setNull(5, Types.NUMERIC);

        cs.execute();
        cs.close();
    }
    public boolean insertTransactions(double in_amount, int in_amount_of_tickets, int in_amount_of_reduced_tickets, long in_id_seance, long in_id_customer) throws SQLException
    {
        String sql_string = "CALL c##cinema.insertTransactions(?,?,?,?,?)";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.setDouble(1, in_amount);
        cs.setInt(2,in_amount_of_tickets);
        cs.setInt(3,in_amount_of_reduced_tickets);
        cs.setLong(4,in_id_seance);
        if(in_id_customer!=0)
            cs.setLong(5,in_id_customer);
        else
            cs.setNull(5, Types.NUMERIC);

        cs.execute();
        cs.close();

        return true;
    }

    public List<Customers> getCustomersList() //bez loginów i haseł
    {
        List <Customers> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewCustomers"); //bez loginów i haseł

            while (rs.next())
            {
                list.add(new Customers(rs.getLong(1),"","",rs.getString(2),rs.getString(3),rs.getString(4),rs.getBoolean(5)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public List<Employees> getEmployeesList() //bez loginów i haseł
    {
        List <Employees> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewEmployees");

            while (rs.next())
            {
                list.add(new Employees(rs.getLong(1),"","",rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5),rs.getTimestamp(6),rs.getLong(7)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public List<Movies> getMoviesList() //bobiera film jeszcze nie oddane
    {
        List <Movies> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewMovies");

            while (rs.next())
            {
                list.add(new Movies(rs.getLong(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getTimestamp(5),rs.getTimestamp(6), rs.getString(7).charAt(0),rs.getBoolean(8)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public List<Positions> getPositionsList()
    {
        List <Positions> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewPositions");

            while (rs.next())
            {
                list.add(new Positions(rs.getLong(1),rs.getString(2),rs.getDouble(3)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public List<ScreeningRooms> getScreeningRoomsList()
    {
        List <ScreeningRooms> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewScreeningrooms");

            while (rs.next())
            {
                list.add(new ScreeningRooms(rs.getLong(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public List<Seances> getSeancesList() // pobiera seanse których data rozpoczęcia jest późniejsza niż obecna
    {
        List <Seances> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewSeances");

            while (rs.next())
            {
                list.add(new Seances(rs.getLong(1),rs.getTimestamp(2),rs.getTimestamp(3),rs.getDouble(4),rs.getLong(5),rs.getLong(6)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public List<TakenSeats> getTakenSeatsList() // nie używany
    {
        List <TakenSeats> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewTeakenseats");

            while (rs.next())
            {
                    list.add(new TakenSeats(rs.getLong(1),rs.getTimestamp(2),rs.getString(3).charAt(0),rs.getInt(4),rs.getString(5).charAt(0),rs.getLong(6),rs.getLong(7),rs.getLong(8)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    // pobiera seanse których data rozpoczęcia jest późniejsza niż obecna
    public List<TakenSeats> getTakenSeatsListForSeance(long idSeance) //sortwoanie po naszej stronie. Seanse tylko
    {
        List <TakenSeats> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewTeakenseats");

            while (rs.next())
            {
                if(idSeance == rs.getLong(6))
                    list.add(new TakenSeats(rs.getLong(1),rs.getTimestamp(2),rs.getString(3).charAt(0),rs.getInt(4),rs.getString(5).charAt(0),rs.getLong(6),rs.getLong(7),rs.getLong(8)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    // pobiera seanse których data rozpoczęcia jest późniejsza niż obecna
    public List<TakenSeats> getTakenSeatsListForCustomer(long idCustomer) //sortwoanie po naszej stronie. Seanse tylko
    {
        List <TakenSeats> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewTeakenseats");

            while (rs.next())
            {
                if(idCustomer == rs.getLong(8))
                    list.add(new TakenSeats(rs.getLong(1),rs.getTimestamp(2),rs.getString(3).charAt(0),rs.getInt(4),rs.getString(5).charAt(0),rs.getLong(6),rs.getLong(7),rs.getLong(8)));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }


    public List<Transactions> getTransactionsList() // nie używany
    {
        List <Transactions> list= new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery("SELECT * FROM c##cinema.ViewTransactions");

            while (rs.next())
            {
                list.add(new Transactions(rs.getLong(1),rs.getDouble(2),rs.getInt(3),rs.getInt(4),rs.getLong(5),rs.getLong(6)));

            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public long logInCustomer(String in_login, String in_password) throws SQLException //zwarca idCustomer
    {
        long id;
        String sql_string = "begin ? := c##cinema.logInCustomer(?,?); end;";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.registerOutParameter(1,Types.NUMERIC);
        cs.setString(2, in_login);
        cs.setString(3, in_password);
        cs.execute();

        id = cs.getLong(1);
        cs.close();

        return id;

    }

    public long logInEmployee(String in_login, String in_password) throws SQLException //zwraca idEmployee
    {
        long id;
        String sql_string = "begin ? := c##cinema.logInEmployee(?,?); end;";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.registerOutParameter(1,Types.NUMERIC);
        cs.setString(2, in_login);
        cs.setString(3, in_password);
        cs.execute();

        id = cs.getLong(1);
        cs.close();

        return id;

    }

    public boolean isEmployeeAnManager(long in_id_employee) throws SQLException
    {
        boolean id;
        String sql_string = "begin ? := c##cinema.isEmployeeAnManager(?); end;";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.registerOutParameter(1,Types.NUMERIC);
        cs.setLong(2, in_id_employee);
        cs.execute();

        id = cs.getBoolean(1);
        cs.close();

        return id;

    }

    //zwraca id ostatnio dodanej tranzakcji.
    //Łata problem braku dostepu do id tanzakcji (id obługują sekwencje i triggery)
    //Funkcjia od tworzenia tranzakcji nie zwraca tego id, gyż jest problem z jednoczesnym SELECTEM i INSERTEM w jednej funkcji
    public long getTransactionId() throws SQLException{
        long id;
        String sql_string = "begin ? := c##cinema.getTransactionId; end;";

        CallableStatement cs = con.prepareCall(sql_string);
        cs.registerOutParameter(1,Types.NUMERIC);
        cs.execute();

        id = cs.getLong(1);
        cs.close();

        return id;
    }

}
