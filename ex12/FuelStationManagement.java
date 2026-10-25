import java.util.*;

public class FuelStationManagement {

    // ---------- Model classes ----------
    static class Fuel {
        String name;
        double pricePerLitre;
        double stock; // in litres

        Fuel(String name, double pricePerLitre, double stock) {
            this.name = name;
            this.pricePerLitre = pricePerLitre;
            this.stock = stock;
        }
    }

    static class Sale {
        int billNo;
        String customer, vehicleNo, fuelName;
        double litres, amount;

        Sale(int billNo, String customer, String vehicleNo, String fuelName, double litres, double amount) {
            this.billNo = billNo;
            this.customer = customer;
            this.vehicleNo = vehicleNo;
            this.fuelName = fuelName;
            this.litres = litres;
            this.amount = amount;
        }
    }

    // ---------- Data ----------
    static List<Fuel> fuels = new ArrayList<>();
    static List<Sale> sales = new ArrayList<>();
    static int nextBillNo = 1001;
    static Scanner sc = new Scanner(System.in);

    // ---------- Input helpers ----------
    static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    static double readDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v <= 0) {
                    System.out.println("Value must be greater than 0.");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    static String readString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    // ---------- Features ----------
    static void viewStock() {
        System.out.println("\n--------- FUEL STOCK & PRICES ---------");
        System.out.printf("%-4s %-10s %-12s %-12s%n", "No", "Fuel", "Price/L (Rs)", "Stock (L)");
        for (int i = 0; i < fuels.size(); i++) {
            Fuel f = fuels.get(i);
            System.out.printf("%-4d %-10s %-12.2f %-12.2f%n", i + 1, f.name, f.pricePerLitre, f.stock);
        }
    }

    static Fuel chooseFuel() {
        viewStock();
        int ch = readInt("Select fuel number: ");
        if (ch < 1 || ch > fuels.size()) {
            System.out.println("Invalid fuel choice.");
            return null;
        }
        return fuels.get(ch - 1);
    }

    static void sellFuel() {
        Fuel f = chooseFuel();
        if (f == null) return;

        String customer = readString("Customer name: ");
        String vehicle = readString("Vehicle number: ");
        double litres = readDouble("Litres required: ");

        if (litres > f.stock) {
            System.out.println("Insufficient stock! Available: " + f.stock + " L");
            return;
        }

        double amount = litres * f.pricePerLitre;
        f.stock -= litres;
        Sale s = new Sale(nextBillNo++, customer, vehicle, f.name, litres, amount);
        sales.add(s);
        printBill(s, f.pricePerLitre);
    }

    static void printBill(Sale s, double rate) {
        System.out.println("\n=========== FUEL BILL ===========");
        System.out.println("Bill No    : " + s.billNo);
        System.out.println("Customer   : " + s.customer);
        System.out.println("Vehicle No : " + s.vehicleNo);
        System.out.println("Fuel       : " + s.fuelName);
        System.out.printf("Rate       : Rs %.2f / L%n", rate);
        System.out.printf("Quantity   : %.2f L%n", s.litres);
        System.out.printf("Total      : Rs %.2f%n", s.amount);
        System.out.println("=================================");
    }

    static void addStock() {
        Fuel f = chooseFuel();
        if (f == null) return;
        double litres = readDouble("Litres to add: ");
        f.stock += litres;
        System.out.println("Stock updated. New stock of " + f.name + ": " + f.stock + " L");
    }

    static void updatePrice() {
        Fuel f = chooseFuel();
        if (f == null) return;
        f.pricePerLitre = readDouble("New price per litre: ");
        System.out.println("Price updated for " + f.name + ".");
    }

    static void salesReport() {
        if (sales.isEmpty()) {
            System.out.println("\nNo sales recorded yet.");
            return;
        }
        System.out.println("\n------------------ SALES REPORT ------------------");
        System.out.printf("%-8s %-12s %-12s %-8s %-10s %-10s%n",
                "Bill", "Customer", "Vehicle", "Fuel", "Litres", "Amount");
        double totalLitres = 0, totalAmount = 0;
        for (Sale s : sales) {
            System.out.printf("%-8d %-12s %-12s %-8s %-10.2f %-10.2f%n",
                    s.billNo, s.customer, s.vehicleNo, s.fuelName, s.litres, s.amount);
            totalLitres += s.litres;
            totalAmount += s.amount;
        }
        System.out.println("---------------------------------------------------");
        System.out.printf("Total litres sold : %.2f L%n", totalLitres);
        System.out.printf("Total revenue     : Rs %.2f%n", totalAmount);
    }

    // ---------- Main ----------
    public static void main(String[] args) {
        // Default fuel data
        fuels.add(new Fuel("Petrol", 102.50, 5000));
        fuels.add(new Fuel("Diesel", 94.30, 5000));
        fuels.add(new Fuel("CNG", 76.00, 3000));

        System.out.println("===== FUEL STATION MANAGEMENT SYSTEM =====");

        while (true) {
            System.out.println("\n1. View Stock & Prices");
            System.out.println("2. Sell Fuel (Generate Bill)");
            System.out.println("3. Add Stock");
            System.out.println("4. Update Fuel Price");
            System.out.println("5. Sales Report");
            System.out.println("6. Exit");

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: viewStock(); break;
                case 2: sellFuel(); break;
                case 3: addStock(); break;
                case 4: updatePrice(); break;
                case 5: salesReport(); break;
                case 6:
                    System.out.println("Thank you! Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
