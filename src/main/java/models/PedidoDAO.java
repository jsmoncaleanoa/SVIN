package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private final String INSERT_PEDIDO_SQL = "INSERT INTO Pedido (fecha, estado, idCliente, idEmpleado) VALUES (?, ?, ?, ?)";
    private final String SELECT_PEDIDO_BY_ID = "SELECT idPedido, fecha, estado, idCliente, idEmpleado FROM Pedido WHERE idPedido = ?";
    private final String SELECT_ALL_PEDIDOS = "SELECT * FROM Pedido";
    private final String UPDATE_PEDIDO_SQL = "UPDATE Pedido SET fecha = ?, estado = ?, idCliente = ?, idEmpleado = ? WHERE idPedido = ?";
    private final String DELETE_PEDIDO_SQL = "DELETE FROM Pedido WHERE idPedido = ?";
    
    private final DatabaseConfig databaseConfig;

    public PedidoDAO() {
        databaseConfig = new DatabaseConfig();
    }

    public void insertPedido(Pedido pedido) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PEDIDO_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, pedido.getFecha());
            preparedStatement.setString(2, pedido.getEstado());
            preparedStatement.setInt(3, pedido.getIdCliente());
            preparedStatement.setInt(4, pedido.getIdEmpleado());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                pedido.setIdPedido(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pedido selectPedido(int id) {
        Pedido pedido = null;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PEDIDO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Date fecha = resultSet.getDate("fecha");
                String estado = resultSet.getString("estado");
                int idCliente = resultSet.getInt("idCliente");
                int idEmpleado = resultSet.getInt("idEmpleado");
                pedido = new Pedido(id, fecha, estado, idCliente, idEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    public List<Pedido> selectAllPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PEDIDOS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("idPedido");
                Date fecha = resultSet.getDate("fecha");
                String estado = resultSet.getString("estado");
                int idCliente = resultSet.getInt("idCliente");
                int idEmpleado = resultSet.getInt("idEmpleado");
                pedidos.add(new Pedido(id, fecha, estado, idCliente, idEmpleado));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public boolean updatePedido(Pedido pedido) {
        boolean rowUpdated = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PEDIDO_SQL)) {
            preparedStatement.setDate(1, pedido.getFecha());
            preparedStatement.setString(2, pedido.getEstado());
            preparedStatement.setInt(3, pedido.getIdCliente());
            preparedStatement.setInt(4, pedido.getIdEmpleado());
            preparedStatement.setInt(5, pedido.getIdPedido());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deletePedido(int id) {
        boolean rowDeleted = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PEDIDO_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
