import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarRentalAdminPanel {

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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(CarRentalAdminPanel::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Car Rental Admin Panel");
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

        JButton addButton = new JButton("Add Car");
        JButton deleteButton = new JButton("Delete Car");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> openAddCarDialog(frame));

        deleteButton.addActionListener(e -> deleteSelectedCar(carTable));

        populateInitialData();

        refreshTable();

        frame.setVisible(true);
    }

    private static void openAddCarDialog(JFrame parentFrame) {
        
        JDialog dialog = new JDialog(parentFrame, "Add New Car", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(7, 2)); 


        JTextField idField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField fuelTypeField = new JTextField();
        JTextField rentalDateField = new JTextField(); 


        dialog.add(new JLabel("Car ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Model:"));
        dialog.add(modelField);
        dialog.add(new JLabel("Brand:"));
        dialog.add(brandField);
        dialog.add(new JLabel("Price:"));
        dialog.add(priceField);
        dialog.add(new JLabel("Fuel Type:"));
        dialog.add(fuelTypeField);
        dialog.add(new JLabel("Rental Date (yyyy-MM-dd):"));
        dialog.add(rentalDateField);


        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        dialog.add(saveButton);
        dialog.add(cancelButton);


        saveButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String model = modelField.getText().trim();
                String brand = brandField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                String fuelType = fuelTypeField.getText().trim();
                String rentalDateString = rentalDateField.getText().trim();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date rentalDate = null;
                try {
                    rentalDate = sdf.parse(rentalDateString);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid date in the format yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                carList.add(new Car(id, model, brand, price, fuelType, rentalDate));
                refreshTable();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid price.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private static void deleteSelectedCar(JTable carTable) {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow != -1) {
            carList.remove(selectedRow);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(carTable, "Please select a car to delete.", "Error", JOptionPane.WARNING_MESSAGE);
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
        tableModel.setRowCount(0);
        for (Car car : carList) {
            tableModel.addRow(new Object[]{car.getId(), car.getModel(), car.getBrand(), car.getPrice(), car.getFuelType(), car.getFormattedRentalDate()});
        }
    }
}
