package app.persistence;

import app.entities.Item;
import app.entities.ItemVariant;
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

    public static List<ItemVariant> getVariantsByItemTypeAndMinLength(int itemTypeId, int minLength, ConnectionPool connectionPool) throws DatabaseException {
        List<ItemVariant> variants = new ArrayList<>();

        String sql = """
        SELECT iv.variant_id, iv.variant_length,
               i.item_id, i.item_name, i.item_height, i.item_width, i.item_type_id, i.item_package_type, i.item_cost_price
        FROM variants iv
        JOIN item i ON iv.item_id = i.item_id
        WHERE i.item_type_id = ? AND iv.variant_length >= ?
        ORDER BY iv.variant_length ASC
        """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemTypeId);
            ps.setInt(2, minLength);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Item først
                Item item = new Item(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getInt("item_height"),
                        rs.getInt("item_width"),
                        rs.getInt("item_type_id"),
                        rs.getString("item_package_type"),
                        rs.getInt("item_cost_price")
                );

                // Variant med item
                ItemVariant variant = new ItemVariant(rs.getInt("variant_id"), item, rs.getInt("variant_length"));

                variants.add(variant);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af item varianter", e);
        }

        return variants;
    }

}