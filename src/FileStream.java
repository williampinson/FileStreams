import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileStream {

    RandProductMaker productGUI;
    RandProductSearch searchGUI;
    ArrayList<RandProduct> products = new ArrayList<>();

    File workingDirectory = new File(System.getProperty("user.dir"));
    //Path path = Paths.get(workingDirectory.getPath() + "\\src\\products.bin");
    String filePath = workingDirectory.getPath() + "\\src\\products.bin";

    public static final long RECORD_SIZE = RandProduct.ID_MAX_LENGTH + RandProduct.NAME_MAX_LENGTH + RandProduct.DESCRIPTION_MAX_LENGTH + 8;

    int productCount = 0;

    public FileStream() {
        productGUI = new RandProductMaker(this);
    }
    public void newProduct(String ID, String name, String description, double cost) {
        products.add(new RandProduct(ID, name, description, cost));
        productCount++;
    }
    public void toFile() {

        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            for (RandProduct product : products) {
                file.write(product.getFormattedID());
                file.write(product.getFormattedName());
                file.write(product.getFormattedDescription());
                file.writeDouble(product.getCost());
            }
            file.close();
            System.out.println("File written");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<RandProduct> readToArrayList() {
        ArrayList<RandProduct> products = new ArrayList<>();

        byte[] idBytes = new byte[RandProduct.ID_MAX_LENGTH];
        byte[] nameBytes = new byte[RandProduct.NAME_MAX_LENGTH];
        byte[] descriptionBytes = new byte[RandProduct.DESCRIPTION_MAX_LENGTH];
        byte[] costBytes = new byte[8];

        long index = 0;

        try (RandomAccessFile file = new RandomAccessFile(filePath,"r")) {
            file.seek(0);
            do {
                file.read(idBytes);
                file.read(nameBytes);
                file.read(descriptionBytes);
                file.read(costBytes);

                products.add(new RandProduct(
                        new String(idBytes, StandardCharsets.UTF_8).trim(),
                        new String(nameBytes, StandardCharsets.UTF_8).trim(),
                        new String(descriptionBytes, StandardCharsets.UTF_8).trim(),
                        ByteBuffer.wrap(costBytes).getDouble()
                ));
                index++;
                file.seek(RECORD_SIZE * index);
            } while (file.getFilePointer() < file.length());
            file.close();
            System.out.println("File read");
            for (RandProduct product : products) {
                System.out.println(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
    private RandProduct readProduct(long pos) {

        byte[] idBytes = new byte[RandProduct.ID_MAX_LENGTH];
        byte[] nameBytes = new byte[RandProduct.NAME_MAX_LENGTH];
        byte[] descriptionBytes = new byte[RandProduct.DESCRIPTION_MAX_LENGTH];
        byte[] costBytes = new byte[8];

        try (RandomAccessFile file = new RandomAccessFile(filePath,"r")) {
            file.seek(pos);
            file.read(idBytes);
            file.read(nameBytes);
            file.read(descriptionBytes);
            file.read(costBytes);
            return new RandProduct(
                    new String(idBytes, StandardCharsets.UTF_8).trim(),
                    new String(nameBytes, StandardCharsets.UTF_8).trim(),
                    new String(descriptionBytes, StandardCharsets.UTF_8).trim(),
                    ByteBuffer.wrap(costBytes).getDouble()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void searchProduct(String searchText) {
        searchGUI.displayTA.setText(String.format("%-8s%-20s%-30s%8s\n", "ID", "Name", "Description", "Cost"));
        String subString = searchText.trim().toLowerCase();

        try (RandomAccessFile file = new RandomAccessFile(filePath,"r")) {
            file.seek(0);
            long numRecords = file.length() / RECORD_SIZE;
            for (int index = 0; index < numRecords; index++) {
                RandProduct product = readProduct(index * RECORD_SIZE);
                if (isMatch(subString, product)) {
                    searchGUI.displayTA.append(String.format("%-8s%-20s%-30s%8.2f\n", product.getID(), product.getName(), product.getDescription(), product.getCost()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isMatch(String searchString, RandProduct product) {
        if (product.getID().toLowerCase().contains(searchString)) {
            return true;
        }
        else if (product.getName().toLowerCase().contains(searchString)) {
            return true;
        }
        else if (product.getDescription().toLowerCase().contains(searchString)) {
            return true;
        }
        else if (String.valueOf(product.getCost()).contains(searchString)) {
            return true;
        }
        return false;
    }
    public void newProductSearch() {
        searchGUI = new RandProductSearch(this);
    }
    public int getProductCount() {
        return productCount;
    }
    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
