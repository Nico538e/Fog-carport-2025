package app.services;

import app.entities.Item;
import app.entities.ItemVariant;
import app.entities.Order;
import app.entities.OrderLine;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ItemMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private static final int POSTS = 1;   // Stolper
    private static final int BEAMS = 2;   // Remme
    private static final int RAFTERS = 2; // Spær

    private final ConnectionPool connectionPool;
    private final List<OrderLine> orderLines = new ArrayList<>();

    public Calculator(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void calculate(Order order) throws DatabaseException {
        calcPosts(order);
        calcBeams(order);
        calcRafters(order);
    }

    private void calcPosts(Order order) throws DatabaseException {
        int length = order.getLength();

        int quantity = calcPostQuantity(length);

        List<ItemVariant> variants = ItemMapper.getVariantsByItemTypeAndMinLength(POSTS, 300, connectionPool);
        if (variants.isEmpty()) throw new DatabaseException("Ingen stolpe-varianter fundet");

        ItemVariant variant = variants.get(0);
        Item item = variant.getItem();

        OrderLine orderLine = new OrderLine(
                0,
                order.getOrderId(),
                order.getUserId(),
                item.getItemId(),
                item.getItemCostPrice(),
                variant,
                order,
                "Stolper nedgraves 90 cm. i jorden – placeret 2 for og bag, resten med 310 cm afstand langs længden",
                quantity
        );
        orderLines.add(orderLine);
    }

    private int calcPostQuantity(int length) {
        return 2 * (2 + (length - 130) / 310); // 310 cm afstand mellem stolper
    }

    private void calcBeams(Order order) throws DatabaseException {
        int length = order.getLength();
        int quantity = 2; // én på hver side

        List<ItemVariant> variants = ItemMapper.getVariantsByItemTypeAndMinLength(BEAMS, length, connectionPool);
        if (variants.isEmpty()) throw new DatabaseException("Ingen remme-varianter fundet");

        ItemVariant variant = variants.get(0);
        Item item = variant.getItem();

        OrderLine orderLine = new OrderLine(
                0,
                order.getOrderId(),
                order.getUserId(),
                item.getItemId(),
                item.getItemCostPrice(),
                variant,
                order,
                "Remme monteres i begge sider, hele carportens længde",
                quantity
        );
        orderLines.add(orderLine);
    }

    private void calcRafters(Order order) throws DatabaseException {
        int length = order.getLength();
        int width = order.getWidth();

        int quantity = (length / 55) + 1; // spær for hver 55 cm

        List<ItemVariant> variants = ItemMapper.getVariantsByItemTypeAndMinLength(RAFTERS, width, connectionPool);
        if (variants.isEmpty()) throw new DatabaseException("Ingen spær-varianter fundet");

        ItemVariant variant = variants.get(0);
        Item item = variant.getItem();

        OrderLine orderLine = new OrderLine(
                0,
                order.getOrderId(),
                order.getUserId(),
                item.getItemId(),
                item.getItemCostPrice(),
                variant,
                order,
                "Spær monteres med ca. 55 cm mellemrum, tværs over carportens bredde",
                quantity
        );
        orderLines.add(orderLine);
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public int getTotalPrice(List<OrderLine> orderLines){
        int totalPrice = 0;

        for(OrderLine ol: orderLines){
            totalPrice += ol.getItemPackageAmount() * ol.getCostPrice();
        }
        return totalPrice;
    }
}