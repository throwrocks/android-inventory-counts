package rocks.athrow.android_inventory_counts.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * InventoryCount
 * Created by joselopez1 on 3/27/2017.
 */

class InventoryCount extends RealmObject {
    public static final String FIELD_ID = "id";
    public static final String FIELD_LOCATION = "location";
    public static final String FIELD_TAG_NUMBER = "tagNumber";
    public static final String FIELD_SKU = "SKU";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PACK_SIZE = "packSize";
    public static final String FIELD_RECEIVED_DATE = "receivedDate";
    public static final String FIELD_EMPLOYEE_NUMBER = "employeeNumber";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";
    public static final String FIELD_COUNT_TYPE = "countType";
    public static final String FIELD_PALLETS = "pallets";
    public static final String FIELD_CASE_QTY = "caseQty";
    public static final String FIELD_LOOSE_QTY = "looseQty";
    @PrimaryKey
    private String id;
    private String location;
    private String tagNumber;
    private int SKU;
    private String description;
    private String packSize;
    private String receivedDate;
    private String expirationDate;
    private int employeeNumber;
    private String employeeName;
    private int countType;
    private int pallets;
    private int caseQty;
    private int looseQty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getCountType() {
        return countType;
    }

    public void setCountType(int countType) {
        this.countType = countType;
    }

    public int getPallets() {
        return pallets;
    }

    public void setPallets(int pallets) {
        this.pallets = pallets;
    }

    public int getCaseQty() {
        return caseQty;
    }

    public void setCaseQty(int caseQty) {
        this.caseQty = caseQty;
    }

    public int getLooseQty() {
        return looseQty;
    }

    public void setLooseQty(int looseQty) {
        this.looseQty = looseQty;
    }
}
