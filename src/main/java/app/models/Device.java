package app.models;

public class Device {

    private int id;
    private String brand;
    private String type;
    private String model;
    private String serialNumber;
    private String defect;
    private int ownerId;
    private int repairId;
    private String completeness;
    private String appearance;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public String getCompleteness() {
        return completeness;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public void print() {
        System.out.printf("%-15s - %s\n", "brand", brand);
        System.out.printf("%-15s - %s\n", "type", type);
        System.out.printf("%-15s - %s\n", "model", model);
        System.out.printf("%-15s - %s\n", "serialNumber", serialNumber);
        System.out.printf("%-15s - %s\n", "defect", defect);
    }

    /**
     * Нужно что-бы в ListView отображались id устройства
     * @return device id
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
