package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DatabaseConfig;

public class PedidoProductoDAO {
    private final String INSERT_PEDIDO_PRODUCTO_SQL = "INSERT INTO pedido_producto (idPedido, idProducto, cantidad) VALUES (?, ?, ?)";
    private final String DELETE_PEDIDO_PRODUCTO_SQL = "DELETE FROM pedido_producto WHERE idPedido = ? AND idProducto = ?";

    private final DatabaseConfig databaseConfig;

    public PedidoProductoDAO() {
        databaseConfig = new DatabaseConfig();
    }

    public void insertPedidoProducto(int idPedido, int idProducto, int cantidad) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PEDIDO_PRODUCTO_SQL)) {
            preparedStatement.setInt(1, idPedido);
            preparedStatement.setInt(2, idProducto);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deletePedidoProducto(int idPedido, int idProducto) {
        boolean rowDeleted = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PEDIDO_PRODUCTO_SQL)) {
            preparedStatement.setInt(1, idPedido);
            preparedStatement.setInt(2, idProducto);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
