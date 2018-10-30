public class Device {

    //    private Integer id;
    private String brand;
    private String type;
    private String model;
    private String serialNumber;
    private String problem;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Device(String brand, String type, String model, String serialNumber, String problem) {
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.serialNumber = serialNumber;
        this.problem = problem;
    }

    public void print() {
        System.out.printf("%-15s - %s\n", "brand", brand);
        System.out.printf("%-15s - %s\n", "type", type);
        System.out.printf("%-15s - %s\n", "model", model);
        System.out.printf("%-15s - %s\n", "serialNumber", serialNumber);
        System.out.printf("%-15s - %s\n", "problem", problem);
    }
}
