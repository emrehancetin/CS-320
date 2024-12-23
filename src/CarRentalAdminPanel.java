import Data.Data;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class CarRentalAdminPanel {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Car Table Example");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Car[] carAll = Data.getCars();
        AtomicReference<ArrayList<Car>> cars = new AtomicReference<>(new ArrayList<>(Arrays.asList(carAll)));
        String[] columnNames = {"ID", "Brand", "Model","Fuel"};
        Object[][] tableData = new Object[cars.get().size()][5];
        String brandNew = "";
        String modelNew = "";
        String fuelTypeNew = "";
        for(int i = 0; i < carAll.length; i++){
            for (Models m : Data.getModels()) {
                if (m.getID() == carAll[i].getModelID()) {
                    modelNew = m.getName();
                    break;
                }
            } for (Brands b : Data.getBrands()) {
                if (b.getId() == carAll[i].getBrandID()) {
                    brandNew = b.getName();
                    break;
                }
            }
            for (Fuel f : Data.getFuels()) {
                if (f.getID() == carAll[i].getFuelID()) {
                    fuelTypeNew = f.getName();
                    break;
                }
            }
            tableData[i][0] = carAll[i].getID();
            tableData[i][1] = brandNew;
            tableData[i][2] = modelNew;
            tableData[i][3] = fuelTypeNew;
        }
        AtomicReference<DefaultTableModel> tableModel = new AtomicReference<>(new DefaultTableModel(tableData, columnNames));
        JTable table = new JTable(tableModel.get());
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel buttonPanel = new JPanel();

        JButton addCarButton = new JButton("Yeni RentingCar Ekle");
        JButton rentedCarsButton = new JButton("Rent Edilen Arabalar");

        addCarButton.addActionListener(e -> {
            JFrame addCarFrame = new JFrame("Yeni RentingCar Ekle");
            addCarFrame.setSize(400, 400);
            addCarFrame.setLayout(new GridLayout(4, 2));


            Fuel[] fuels = new Fuel[0];
            try {
                fuels = Data.getFuels();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            String[] fuelNames = new String[fuels.length];
            for (int i = 0; i < fuels.length; i++) {
                fuelNames[i] = fuels[i].getName();
            }
            Models[] models = new Models[0];
            try {
                models = Data.getModels();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            String[] modelNames = new String[models.length];
            for (int i = 0; i < modelNames.length; i++) {
                modelNames[i] = models[i].getName();
            }
            Brands[] brands = new Brands[0];
            try {
                brands = Data.getBrands();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            String[] brandNames = new String[brands.length];
            for (int i = 0; i < brandNames.length; i++) {
                brandNames[i] = brands[i].getName();
            }

            JLabel brandLabel = new JLabel("Marka:");
            JComboBox<String> brandDropdown = new JComboBox<>(brandNames);

            JLabel modelLabel = new JLabel("Model:");
            JComboBox<String> modelDropdown = new JComboBox<>(modelNames);

            JLabel fuelLabel = new JLabel("Yakıt Türü:");
            JComboBox<String> fuelDropdown = new JComboBox<>(fuelNames);

            JButton saveButton = new JButton("Kaydet");
            saveButton.addActionListener(exc -> {
                String selectedBrand = (String) brandDropdown.getSelectedItem();
                String selectedModel = (String) modelDropdown.getSelectedItem();
                String selectedFuel = (String) fuelDropdown.getSelectedItem();
                int brandid = 0;
                try {
                    for (Brands brand : Data.getBrands()) {
                        if (brand.getName().equals(selectedBrand)) {
                            brandid = brand.getId();
                            break;
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                int modelid = 0;
                try {
                    for (Models model : Data.getModels()) {
                        if (model.getName().equals(selectedModel)) {
                            modelid = model.getID();
                            break;
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                int fuelid = 0;
                try {
                    for (Fuel fuel : Data.getFuels()) {
                        if (fuel.getName().equals(selectedFuel)) {
                            fuelid = fuel.getID();
                            break;
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Car newCar = new Car( brandid, modelid, fuelid);
                try {
                    Data.addCar(newCar);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(addCarFrame,
                        "Yeni Araba Eklendi:\nMarka: " + selectedBrand +
                                "\nModel: " + selectedModel +
                                "\nYakıt Türü: " + selectedFuel);
                DefaultTableModel tableModel2 = (DefaultTableModel) table.getModel();
                ArrayList<Car> newCars;
                try {
                    Car[] newCarAll = Data.getCars();
                    newCars = new ArrayList<>(Arrays.asList(newCarAll));
                    cars.set(newCars);
                    tableModel2.addRow(new Object[]{newCarAll[newCarAll.length - 1].getID(), selectedBrand, selectedModel, selectedFuel});
                    tableModel.set(tableModel2);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }





            });

            brandDropdown.addActionListener(ex -> {
                String selectedBrand = (String) brandDropdown.getSelectedItem();

                String[] updatedModels = new String[]{};
                try {
                    for (Brands brand : Data.getBrands()) {
                        if (brand.getName().equals(selectedBrand)) {
                            ArrayList<Models> matchingModels = new ArrayList<>();
                            for (Models model : Data.getModels()) {
                                if (model.getBrandID() == brand.getId()) {
                                    matchingModels.add(model);
                                }
                            }
                            updatedModels = matchingModels.stream().map(Models::getName).toArray(String[]::new);
                            break;
                        }
                    }
                } catch (Exception exce) {
                    exce.printStackTrace();
                    JOptionPane.showMessageDialog(addCarFrame, "An error occurred while updating models.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                modelDropdown.removeAllItems();
                for (String model : updatedModels) {
                    modelDropdown.addItem(model);
                }
            });
            modelDropdown.addActionListener(excep -> {
                String selectedModel = (String) modelDropdown.getSelectedItem();
                String selectedBrand = (String) brandDropdown.getSelectedItem();


                String[] updatedFuels = new String[]{};
                try {
                    for (Brands brand : Data.getBrands()) {
                        if (brand.getName().equals(selectedBrand)) {
                            int brandID = brand.getId();

                            for (Models model : Data.getModels()) {
                                if (model.getName().equals(selectedModel) && model.getBrandID() == brandID) {
                                    ArrayList<Fuel> matchingFuels = new ArrayList<>();
                                    for (Car car : Data.getCars()) {
                                        if (car.getBrandID() == brandID && car.getModelID() == model.getID()) {
                                            for (Fuel fuel : Data.getFuels()) {
                                                if (fuel.getID() == car.getFuelID() && !matchingFuels.contains(fuel)) {
                                                    matchingFuels.add(fuel);
                                                }
                                            }
                                        }
                                    }
                                    updatedFuels = matchingFuels.stream().map(Fuel::getName).toArray(String[]::new);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addCarFrame, "An error occurred while updating fuels.", "Error", JOptionPane.ERROR_MESSAGE);
                }


                fuelDropdown.removeAllItems();
                for (String fuel : updatedFuels) {
                    fuelDropdown.addItem(fuel);
                }
            });


            addCarFrame.add(brandLabel);
            addCarFrame.add(brandDropdown);
            addCarFrame.add(modelLabel);
            addCarFrame.add(modelDropdown);
            addCarFrame.add(fuelLabel);
            addCarFrame.add(fuelDropdown);
            addCarFrame.add(saveButton);


            addCarFrame.setVisible(true);
        });
        rentedCarsButton.addActionListener(e -> showRentedCars());
        buttonPanel.add(addCarButton);
        JButton deleteCarButton = new JButton("Araba Sil");
        buttonPanel.add(deleteCarButton);


        deleteCarButton.addActionListener(e -> {
            JFrame deleteCarFrame = new JFrame("Araba Sil");
            deleteCarFrame.setSize(300, 150);
            deleteCarFrame.setLayout(new GridLayout(2, 2));

            JLabel idLabel = new JLabel("Silmek istediğiniz Araba ID:");
            JTextField idField = new JTextField();
            JButton deleteButton = new JButton("Sil");
            JButton cancelButton = new JButton("İptal");

            deleteCarFrame.add(idLabel);
            deleteCarFrame.add(idField);
            deleteCarFrame.add(deleteButton);
            deleteCarFrame.add(cancelButton);

            deleteButton.addActionListener(event -> {
                String carIDText = idField.getText().trim();
                try {
                    int carID = Integer.parseInt(carIDText);

                    // Veri kaynağından arabayı sil
                    boolean isDeleted = Data.deleteCarByID(carID);
                    if (isDeleted) {
                        // Tabloyu güncelle
                        DefaultTableModel tableModel2 = (DefaultTableModel) table.getModel();
                        for (int i = 0; i < tableModel2.getRowCount(); i++) {
                            if ((int) tableModel2.getValueAt(i, 0) == carID) {
                                tableModel2.removeRow(i);
                                break;
                            }
                        }
                        tableModel.set(tableModel2);
                        JOptionPane.showMessageDialog(deleteCarFrame, "Araba başarıyla silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        deleteCarFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(deleteCarFrame, "ID bulunamadı. Silme işlemi başarısız.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(deleteCarFrame, "Geçerli bir ID giriniz.", "Hata", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(deleteCarFrame, "Bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });

            cancelButton.addActionListener(event -> deleteCarFrame.dispose());

            deleteCarFrame.setVisible(true);
        });
        buttonPanel.add(rentedCarsButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void openAddCarScreen(JTable table) throws Exception {

        JFrame addCarFrame = new JFrame("Yeni RentingCar Ekle");
        addCarFrame.setSize(400, 400);
        addCarFrame.setLayout(new GridLayout(4, 2));


        Fuel[] fuels = Data.getFuels();
        String[] fuelNames = new String[fuels.length];
        for (int i = 0; i < fuels.length; i++) {
            fuelNames[i] = fuels[i].getName();
        }
        Models[] models = Data.getModels();
        String[] modelNames = new String[models.length];
        for (int i = 0; i < modelNames.length; i++) {
            modelNames[i] = models[i].getName();
        }
        Brands[] brands = Data.getBrands();
        String[] brandNames = new String[brands.length];
        for (int i = 0; i < brandNames.length; i++) {
            brandNames[i] = brands[i].getName();
        }

        JLabel brandLabel = new JLabel("Marka:");
        JComboBox<String> brandDropdown = new JComboBox<>(brandNames);

        JLabel modelLabel = new JLabel("Model:");
        JComboBox<String> modelDropdown = new JComboBox<>(modelNames);

        JLabel fuelLabel = new JLabel("Yakıt Türü:");
        JComboBox<String> fuelDropdown = new JComboBox<>(fuelNames);

        JButton saveButton = new JButton("Kaydet");
        saveButton.addActionListener(e -> {
            String selectedBrand = (String) brandDropdown.getSelectedItem();
            String selectedModel = (String) modelDropdown.getSelectedItem();
            String selectedFuel = (String) fuelDropdown.getSelectedItem();
            int brandid = 0;
            try {
                for (Brands brand : Data.getBrands()) {
                    if (brand.getName().equals(selectedBrand)) {
                        brandid = brand.getId();
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            int modelid = 0;
            try {
                for (Models model : Data.getModels()) {
                    if (model.getName().equals(selectedModel)) {
                        modelid = model.getID();
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            int fuelid = 0;
            try {
                for (Fuel fuel : Data.getFuels()) {
                    if (fuel.getName().equals(selectedFuel)) {
                        fuelid = fuel.getID();
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            Car newCar = new Car( brandid, modelid, fuelid);
            try {
                Data.addCar(newCar);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(addCarFrame,
                    "Yeni Araba Eklendi:\nMarka: " + selectedBrand +
                            "\nModel: " + selectedModel +
                            "\nYakıt Türü: " + selectedFuel);
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.addRow(new Object[]{newCar.getID(), selectedBrand, selectedModel, selectedFuel});

        });

        brandDropdown.addActionListener(e -> {
            String selectedBrand = (String) brandDropdown.getSelectedItem();

            String[] updatedModels = new String[]{};
            try {
                for (Brands brand : Data.getBrands()) {
                    if (brand.getName().equals(selectedBrand)) {
                        ArrayList<Models> matchingModels = new ArrayList<>();
                        for (Models model : Data.getModels()) {
                            if (model.getBrandID() == brand.getId()) {
                                matchingModels.add(model);
                            }
                        }
                        updatedModels = matchingModels.stream().map(Models::getName).toArray(String[]::new);
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addCarFrame, "An error occurred while updating models.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            modelDropdown.removeAllItems();
            for (String model : updatedModels) {
                modelDropdown.addItem(model);
            }
        });
        modelDropdown.addActionListener(e -> {
            String selectedModel = (String) modelDropdown.getSelectedItem();
            String selectedBrand = (String) brandDropdown.getSelectedItem();


            String[] updatedFuels = new String[]{};
            try {
                for (Brands brand : Data.getBrands()) {
                    if (brand.getName().equals(selectedBrand)) {
                        int brandID = brand.getId();

                        for (Models model : Data.getModels()) {
                            if (model.getName().equals(selectedModel) && model.getBrandID() == brandID) {
                                ArrayList<Fuel> matchingFuels = new ArrayList<>();
                                for (Car car : Data.getCars()) {
                                    if (car.getBrandID() == brandID && car.getModelID() == model.getID()) {
                                        for (Fuel fuel : Data.getFuels()) {
                                            if (fuel.getID() == car.getFuelID() && !matchingFuels.contains(fuel)) {
                                                matchingFuels.add(fuel);
                                            }
                                        }
                                    }
                                }
                                updatedFuels = matchingFuels.stream().map(Fuel::getName).toArray(String[]::new);
                                break;
                            }
                        }
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addCarFrame, "An error occurred while updating fuels.", "Error", JOptionPane.ERROR_MESSAGE);
            }


            fuelDropdown.removeAllItems();
            for (String fuel : updatedFuels) {
                fuelDropdown.addItem(fuel);
            }
        });


        addCarFrame.add(brandLabel);
        addCarFrame.add(brandDropdown);
        addCarFrame.add(modelLabel);
        addCarFrame.add(modelDropdown);
        addCarFrame.add(fuelLabel);
        addCarFrame.add(fuelDropdown);
        addCarFrame.add(saveButton);


        addCarFrame.setVisible(true);
    }
    private static void showRentedCars() {

        JFrame rentedCarsFrame = new JFrame("Rent Edilen Arabalar");
        rentedCarsFrame.setSize(1800, 600);
        rentedCarsFrame.setLayout(new BorderLayout());

        String[] rentedColumnNames = {"Rent ID", "User ID", "Car Name", "Brand", "Model", "Fuel Type", "Color", "Starting Time", "Finish Time"};
        ArrayList<Object[]> rentedCarsData = new ArrayList<>();

        try {
            for (Rent rent : Data.getRents()) {
                for (RentingCars rentingCar : Data.getRentingCars()) {
                    if (rent.getRentingCarID() == rentingCar.getID()) {
                        String brand = "";
                        String model = "";
                        String fuelType = "";
                        String carName = rentingCar.getName();
                        String color = rentingCar.getColor();

                        for (Car car : Data.getCars()) {
                            if (car.getID() == rentingCar.getCarID()) {
                                for (Brands b : Data.getBrands()) {
                                    if (b.getId() == car.getBrandID()) {
                                        brand = b.getName();
                                        break;
                                    }
                                }
                                for (Models m : Data.getModels()) {
                                    if (m.getID() == car.getModelID()) {
                                        model = m.getName();
                                        break;
                                    }
                                }
                                for (Fuel f : Data.getFuels()) {
                                    if (f.getID() == car.getFuelID()) {
                                        fuelType = f.getName();
                                        break;
                                    }
                                }
                                break;
                            }
                        }

                        rentedCarsData.add(new Object[]{
                                rent.getID(),
                                rent.getUserID(),
                                carName,
                                brand,
                                model,
                                fuelType,
                                color,
                                rent.getStartingTime(),
                                rent.getFinishTime()
                        });
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while fetching rented cars.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        Object[][] rentedCarsTableData = rentedCarsData.toArray(new Object[0][]);
        DefaultTableModel rentedTableModel = new DefaultTableModel(rentedCarsTableData, rentedColumnNames);
        JTable rentedCarsTable = new JTable(rentedTableModel);

        JScrollPane scrollPane = new JScrollPane(rentedCarsTable);
        rentedCarsFrame.add(scrollPane, BorderLayout.CENTER);

        rentedCarsFrame.setVisible(true);
    }

}

