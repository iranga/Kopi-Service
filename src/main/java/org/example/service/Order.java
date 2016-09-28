package org.example.service;

/**
 * Created by irangamuthuthanthri on 9/23/16.
 */
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by irangamuthuthanthri on 9/23/16.
 */
@XmlRootElement(name = "Order")
public class Order{
    private String orderId;

    private String drinkName;

    private String additions;

    private double cost;

    private boolean locked;

    private long timestamp;

    public static final NumberFormat currencyFormat = new DecimalFormat("#.##");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");

    public Order() {
        this.orderId = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
//        this.setCost(calculateCost());
        this.timestamp = System.currentTimeMillis();
    }

    public String getAdditions() {
        return additions;
    }

    public void setAdditions(String additions) {
        this.additions = additions;
//        this.setCost(calculateCost());
        this.timestamp = System.currentTimeMillis();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getTimestamp() {
        return dateFormat.format(new Date(timestamp));
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAmountAcceptable(double amount) {
        return amount >= cost;
    }
}

