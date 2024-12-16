import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

        public String getId() {
            return id;
        }

        public String getModel() {
            return model;
        }

        public String getBrand() {
            return brand;
        }

        public double getPrice() {
            return price;
        }

        public String getFuelType() {
            return fuelType;
        }

        public Date getRentalDate() {
            return rentalDate;
        }

        public String getFormattedRentalDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(rentalDate);
        }
    }

    private static List<Car> carList = new ArrayList<>();
    private static DefaultTableModel tableModel;
    private static boolean hasRentedCar = false;

    public static void main(String[] args) {
        populateInitialData();
        SwingUtilities.invokeLater(CarRentalUserPanel::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Car Rental User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setLayout(new BorderLayout());

        String[] columnNames = {"Car ID", "Model", "Brand", "Price", "Fuel Type", "Rental Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton filterButton = new JButton("Filter Cars");
        JButton rentButton = new JButton("Rent Car");
        buttonPanel.add(filterButton);
        buttonPanel.add(rentButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        filterButton.addActionListener(e -> openFilterDialog(frame));

        rentButton.addActionListener(e -> rentSelectedCar(carTable));

        refreshTable();

        frame.setVisible(true);
    }

    private static void openFilterDialog(JFrame parentFrame) {

        JDialog dialog = new JDialog(parentFrame, "Filter Cars", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JTextField brandField = new JTextField();
        JTextField maxPriceField = new JTextField();
        JTextField fuelTypeField = new JTextField();

        dialog.add(new JLabel("Brand:"));
        dialog.add(brandField);
        dialog.add(new JLabel("Max Price:"));
        dialog.add(maxPriceField);
        dialog.add(new JLabel("Fuel Type:"));
        dialog.add(fuelTypeField);

        JButton filterButton = new JButton("Apply Filter");
        JButton cancelButton = new JButton("Cancel");
        dialog.add(filterButton);
        dialog.add(cancelButton);

        filterButton.addActionListener(e -> {
            String brand = brandField.getText().trim();
            String maxPriceText = maxPriceField.getText().trim();
            String fuelType = fuelTypeField.getText().trim();

            double maxPrice = Double.MAX_VALUE;
            if (!maxPriceText.isEmpty()) {
                try {
                    maxPrice = Double.parseDouble(maxPriceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid price.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            double finalMaxPrice = maxPrice;
            List<Car> filteredCars = carList.stream()
                    .filter(car -> (brand.isEmpty() || car.getBrand().equalsIgnoreCase(brand)))
                    .filter(car -> car.getPrice() <= finalMaxPrice)
                    .filter(car -> (fuelType.isEmpty() || car.getFuelType().equalsIgnoreCase(fuelType)))
                    .collect(Collectors.toList());

            refreshTable(filteredCars);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private static void rentSelectedCar(JTable carTable) {
        if (hasRentedCar){
            JOptionPane.showMessageDialog(carTable, "You can only rent one car at a time.","Error", JOptionPane.WARNING_MESSAGE);
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

    private static void populateInitialData() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            carList.add(new Car("1", "S Class", "Mercedes", 79357.99, "Diesel", sdf.parse("2024-12-01")));
            carList.add(new Car("2", "Civic", "Honda", 24971.99, "Gasoline", sdf.parse("2024-11-20")));
            carList.add(new Car("3", "Corolla", "Toyota", 1946.99, "Gasoline", sdf.parse("2024-10-15")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}