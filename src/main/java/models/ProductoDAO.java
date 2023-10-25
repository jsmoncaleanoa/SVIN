package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private final String INSERT_PRODUCTO_SQL = "INSERT INTO Producto (nombre, descripcion, precio) VALUES (?, ?, ?)";
    private final String SELECT_PRODUCTO_BY_ID = "SELECT idProducto, nombre, descripcion, precio FROM Producto WHERE idProducto = ?";
    private final String SELECT_ALL_PRODUCTOS = "SELECT * FROM Producto";
    private final String UPDATE_PRODUCTO_SQL = "UPDATE Producto SET nombre = ?, descripcion = ?, precio = ? WHERE idProducto = ?";
    private final String DELETE_PRODUCTO_SQL = "DELETE FROM Producto WHERE idProducto = ?";
    
    private final DatabaseConfig databaseConfig;

    public ProductoDAO() {
        databaseConfig = new DatabaseConfig();
    }

    public void insertProducto(Producto producto) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTO_SQL)) {
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setBigDecimal(3, producto.getPrecio());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Producto selectProducto(int id) {
        Producto producto = null;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                BigDecimal precio = resultSet.getBigDecimal("precio");
                producto = new Producto(id, nombre, descripcion, precio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    public List<Producto> selectAllProductos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTOS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("idProducto");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                BigDecimal precio = resultSet.getBigDecimal("precio");
                productos.add(new Producto(id, nombre, descripcion, precio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public boolean updateProducto(Producto producto) {
        boolean rowUpdated = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTO_SQL)) {
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setBigDecimal(3, producto.getPrecio());
            preparedStatement.setInt(4, producto.getIdProducto());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteProducto(int id) {
        boolean rowDeleted = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTO_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
