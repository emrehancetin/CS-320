package Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarRentalUserPanel {

    static class Car {
        private String id;
        private String model;
        private String brand;
        private double price;
        private String fuelType;
        private Date rentalDate;

        public Car(String id, String model, String brand, double price, String fuelType, Date rentalDate) {
            this.id = id;
            this.model = model;
            this.brand = brand;
            this.price = price;
            this.fuelType = fuelType;
            this.rentalDate = rentalDate;
        }

        public String getId() { return id; }
        public String getModel() { return model; }
        public String getBrand() { return brand; }
        public double getPrice() { return price; }
        public String getFuelType() { return fuelType; }
        public Date getRentalDate() { return rentalDate; }

        public String getFormattedRentalDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(rentalDate);
        }
    }

    private static List<Car> carList = new ArrayList<>();
    private static DefaultTableModel tableModel;
    private static boolean hasRentedCar = false;
    private static Connection connection; // Database connection object

    public static void start() {
        setupDatabaseConnection();
        populateInitialDataFromDatabase();
        SwingUtilities.invokeLater(CarRentalUserPanel::createAndShowGUI);
    }

    // Establish the database connection
    private static void setupDatabaseConnection() {
        String url = "jdbc:postgresql://localhost:5432/cs320";
        String user = "postgres";
        String password = "073416";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    // Populate data from the PostgreSQL database
    private static void populateInitialDataFromDatabase() {
        try {
            String query = "SELECT id, model_id,brand_id,fuel_id FROM cars";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String model = resultSet.getString("model_id");
                String brand = resultSet.getString("brand_id");
                //double price = resultSet.getDouble("price");
                String fuelType = resultSet.getString("fuel_id");
                //Date rentalDate = resultSet.getDate("rental_date");

                // Add car to the list
                carList.add(new Car(id, model, brand, 0, fuelType, new Date(01-01-2025)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Car Rental User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        String[] columnNames = {"Car ID", "Model", "Brand", "Price", "Fuel Type", "Rental Date"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton filterButton = new JButton("Filter Cars");
        JButton clearFilterButton = new JButton("Clear All Filters");
        JButton rentButton = new JButton("Rent Car");
        buttonPanel.add(filterButton);
        buttonPanel.add(clearFilterButton);
        buttonPanel.add(rentButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        filterButton.addActionListener(e -> openFilterDialog(frame));

        clearFilterButton.addActionListener(e -> clearAllFilters());

        rentButton.addActionListener(e -> rentSelectedCar(carTable));

        refreshTable();

        frame.setVisible(true);
    }

    private static void refreshTable() {
        refreshTable(carList);
    }

    private static void refreshTable(List<Car> cars) {
        tableModel.setRowCount(0);
        for (Car car : cars) {
            tableModel.addRow(new Object[]{car.getId(), car.getModel(), car.getBrand(), car.getPrice(), car.getFuelType(), car.getFormattedRentalDate()});
        }
    }

    // Rent car functionality
    private static void rentSelectedCar(JTable carTable) {
        if (hasRentedCar) {
            JOptionPane.showMessageDialog(carTable, "You can only rent one car at a time.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = carTable.getSelectedRow();
        if (selectedRow != -1) {
            String carId = (String) tableModel.getValueAt(selectedRow, 0);
            String carModel = (String) tableModel.getValueAt(selectedRow, 1);

            JOptionPane.showMessageDialog(carTable, "You have rented the car: " + carModel + " (ID: " + carId + ")", "Success", JOptionPane.INFORMATION_MESSAGE);
            hasRentedCar = true;
        } else {
            JOptionPane.showMessageDialog(carTable, "Please select a car to rent.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void openFilterDialog(JFrame parentFrame) {
        // Implement filtering dialog
    }

    private static void clearAllFilters() {
        refreshTable(carList);
    }
}
