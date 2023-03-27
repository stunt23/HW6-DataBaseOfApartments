package org.example;


import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final String DB_URL_CONNECTION = "jdbc:mysql://localhost:3306/HW6AppartmentsScheme?serverTimezone=Europe/Kiev";
    public static final String DB_USER = "root";//"admin"
    public static final String DB_PASSWORD = "23111990";
    public static Connection conn;
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String aprtRegionNumber = null;
        String aprtAdress = null;
        String aprtAreaMin = null;
        String aprtAreaMax = null;
        String aprtNumerOfRoomsMin = null;
        String aprtNumerOfRoomsMax = null;
        String aprtCostMin = null;
        String aprtCostMax = null;
        try {
             conn =  DriverManager.getConnection(DB_URL_CONNECTION, DB_USER,DB_PASSWORD);
             createTableUseingJDBC();
             insertRandomAppartmentUseingJDBC(999);//Now, there are 999 apartments in the base

            while (true){
                gettingTheApartmentParametersAndShowinTheReasult(aprtRegionNumber, aprtAdress, aprtAreaMin, aprtAreaMax,
                        aprtNumerOfRoomsMin, aprtNumerOfRoomsMax, aprtCostMin, aprtCostMax, sc);

                System.out.println("Press 'q' if you want to quit, press Enter");
                String q = sc.nextLine();
                if(q.equals("q")){
                    break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void gettingTheApartmentParametersAndShowinTheReasult(String aprtRegionNumber, String aprtAdress,
                                                      String aprtAreaMin, String aprtAreaMax,
                                                      String aprtNumerOfRoomsMin, String aprtNumerOfRoomsMax,
                                                      String aprtCostMin, String aprtCostMax,  Scanner sc){
        System.out.print("To get the list of apartments, that you are looking for");
        System.out.println("You have to fill the next form:");
        System.out.println("Write region number, please // 1 OR 2 OR 3");
        aprtRegionNumber = sc.nextLine();
        System.out.println("Write the adress, please //Adress1 OR Adress2 OR Adress3");
        aprtAdress = sc.nextLine();

        System.out.println("Write min area of apartments, please // From 20 t0 100");
        aprtAreaMin = sc.nextLine();
        System.out.println("Write max area of apartments, please // From 20 t0 100");
        aprtAreaMax = sc.nextLine();

        System.out.println("Write min number of rooms, please // From 1 to 4");
        aprtNumerOfRoomsMin = sc.nextLine();
        System.out.println("Write max number of rooms, please // From 1 to 4");
        aprtNumerOfRoomsMax= sc.nextLine();

        System.out.println("Write min cost of the apartment, please");
        aprtCostMin = sc.nextLine();
        System.out.println("Write max cost of the apartment, please");
        aprtCostMax = sc.nextLine();

        try {
            showTheResult(aprtRegionNumber, aprtAdress, aprtAreaMin, aprtAreaMax,
                    aprtNumerOfRoomsMin, aprtNumerOfRoomsMax, aprtCostMin, aprtCostMax);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showTheResult(String aprtRegionNumber, String aprtAdress, String aprtAreaMin, String aprtAreaMax,
                                      String aprtNumerOfRoomsMin, String aprtNumerOfRoomsMax,
                                      String aprtCostMin, String aprtCostMax) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Appartments WHERE aprtRegionNumber = ? AND aprtAdress = ? AND " +
                "aprtArea >= ? AND aprtArea <= ? AND aprtNumerOfRooms >= ? AND aprtNumerOfRooms <= ? AND " +
                "aprtCost >= ? AND aprtCost <= ? ");
        //(aprtRegionNumber, aprtAdress, aprtArea, aprtNumerOfRooms, aprtCost)
        try {
            ps.setInt(1,Integer.valueOf(aprtRegionNumber));
            ps.setString(2, aprtAdress);
            ps.setDouble(3, Double.valueOf(aprtAreaMin));
            ps.setDouble(4, Double.valueOf(aprtAreaMax));
            ps.setInt(5,Integer.valueOf(aprtNumerOfRoomsMin));
            ps.setInt(6,Integer.valueOf(aprtNumerOfRoomsMax));
            ps.setDouble(7, Double.valueOf(aprtCostMin));
            ps.setDouble(8, Double.valueOf(aprtCostMax));

            ResultSet resultSet = ps.executeQuery(); //Operand should contain 1 column(s)
            ResultSetMetaData metaData = resultSet.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.print(metaData.getColumnName(i) + "\t\t");
            }
            System.out.println();
            while (resultSet.next()){
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + "\t\t");
                }
                System.out.println();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
        }
;
    }

    private static void createTableUseingJDBC() throws SQLException {
        Statement st = null;
        try {
            st = conn.createStatement();
            st.execute("DROP TABLE IF EXISTS Appartments");
            st.execute("CREATE TABLE Appartments (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, aprtRegionNumber INT, " +
                    "aprtAdress VARCHAR (20), aprtArea DOUBLE, aprtNumerOfRooms INT, aprtCost DOUBLE)");//
        }  finally {
            st.close();
        }
    }

    private static void insertRandomAppartmentUseingJDBC(int quantity) throws SQLException {
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            Integer aprtRegionNumber = random.nextInt(2)+1;
            String aprtAdress = "Adress" + (random.nextInt(5)+1);

            Double aprtArea = random.nextInt(80)+20 + random.nextDouble(1);
            String twoDigitFormattedDouble = String.format("%.2f",aprtArea);
            aprtArea = Double.parseDouble(twoDigitFormattedDouble.replace(',', '.'));

            Integer aprtNumerOfRooms = random.nextInt(4)+1;

            Double aprtCost = aprtArea * 777;
            twoDigitFormattedDouble = String.format("%.2f",aprtCost);
            aprtCost = Double.parseDouble(twoDigitFormattedDouble.replace(',', '.'));


            Statement st = conn.createStatement();
            st.execute("INSERT INTO Appartments (aprtRegionNumber, aprtAdress, aprtArea, aprtNumerOfRooms, aprtCost) " +
             "VALUES (" + aprtRegionNumber+ ", \"" + aprtAdress + "\", " + aprtArea + ", " + aprtNumerOfRooms + ", " + aprtCost + ")");
        }
    }
}
