package app.persistence;

import app.entities.Item;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public static List<Item> getAllItems(ConnectionPool connectionPool) throws DatabaseException {
        List<Item> itemList = new ArrayList<>();

        String sql = "SELECT * FROM Item";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int itemHeight = rs.getInt("item_height");
                int itemWidth = rs.getInt("item_width");
                int itemTypeId = rs.getInt("item_type_id");
                String itemPackageType = rs.getString("item_package_type");
                int itemCostPrice = rs.getInt("item_cost_price");

                Item item = new Item(itemId, itemName, itemHeight, itemWidth, itemTypeId, itemPackageType, itemCostPrice);
                itemList.add(item);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get all items", e);
        }
        return itemList;
    }

    public static Item getOneItemById(ConnectionPool connectionPool, int oneItemId) throws DatabaseException {
        String sql = "SELECT * FROM item where item_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            // get item by id
            ps.setInt(1, oneItemId);
            ResultSet rs = ps.executeQuery();

            // if the item exist give me an item object
            if (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int itemHeight = rs.getInt("item_height");
                int itemWidth = rs.getInt("item_width");
                int itemType = rs.getInt("item_type");
                String itemPackageType = rs.getString("item_package_type");
                int itemCostPrice = rs.getInt("item_cost_price");

                return new Item(itemId, itemName, itemHeight, itemWidth, itemType, itemPackageType, itemCostPrice);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get the item by id", e);
        }
        // if the item do not exist return null
        return null;
    }

    public static List<Item> getAllTypeItems(ConnectionPool connectionPool) throws DatabaseException {
        List<Item> typeItemList = new ArrayList<>();

        String sql = "SELECT * FROM Item WHERE item_type = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int itemHeight = rs.getInt("item_height");
                int itemWidth = rs.getInt("item_width");
                int itemTypeId = rs.getInt("item_type");
                String itemPackageType = rs.getString("item_package_type");
                int itemCostPrice = rs.getInt("item_cost_price");

                Item item = new Item(itemId, itemName, itemHeight, itemWidth, itemTypeId, itemPackageType, itemCostPrice);
                typeItemList.add(item);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get all items", e);
        }
        return typeItemList;
    }

    public static Item getItemWithoutDimensionsById(ConnectionPool connectionPool, int oneItemId) throws DatabaseException {
        String sql = "SELECT * FROM item where item_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            // get item by id
            ps.setInt(1, oneItemId);
            ResultSet rs = ps.executeQuery();

            // if the item exist give me an item object
            if (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                String itemType = rs.getString("item_type");
                String itemPackageType = rs.getString("item_package_type");
                int itemCostPrice = rs.getInt("item_cost_price");

                return new Item(itemId, itemName, itemType,itemPackageType, itemCostPrice);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get the item by id", e);
        }
        // if the item do not exist return null
        return null;
    }

}