package utils;

public class NewOrderSerializationClass {

    private String firstName;

    private String lastName;

    private String address;

    private int metroStation;

    private String phone;

    private String rentTime;

    private String deliveryDate;

    private String comment;
    private String colour;

    public NewOrderSerializationClass(String colour) {
        this.colour = colour;
    }

    public NewOrderSerializationClass() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(int metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
