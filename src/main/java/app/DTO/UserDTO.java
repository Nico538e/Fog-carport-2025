package app.DTO;

public class UserDTO {
    private int orderId;
    private String userName;

    public UserDTO(int orderId, String userName) {
        this.orderId = orderId;
        this.userName = userName;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "orderId=" + orderId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
