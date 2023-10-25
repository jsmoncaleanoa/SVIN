package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final String INSERT_CLIENTE_SQL = "INSERT INTO Cliente (nombre, direccion, email) VALUES (?, ?, ?)";
    private final String SELECT_CLIENTE_BY_ID = "SELECT idCliente, nombre, direccion, email FROM Cliente WHERE idCliente = ?";
    private final String SELECT_ALL_CLIENTES = "SELECT * FROM Cliente";
    private final String UPDATE_CLIENTE_SQL = "UPDATE Cliente SET nombre = ?, direccion = ?, email = ? WHERE idCliente = ?";
    private final String DELETE_CLIENTE_SQL = "DELETE FROM Cliente WHERE idCliente = ?";
    
    private final DatabaseConfig databaseConfig;

    public ClienteDAO() {
        databaseConfig = new DatabaseConfig();
    }

    public void insertCliente(Cliente cliente) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTE_SQL)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getDireccion());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente selectCliente(int id) {
        Cliente cliente = null;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String email = resultSet.getString("email");
                cliente = new Cliente(id, nombre, direccion, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public List<Cliente> selectAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENTES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("idCliente");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String email = resultSet.getString("email");
                clientes.add(new Cliente(id, nombre, direccion, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public boolean updateCliente(Cliente cliente) {
        boolean rowUpdated = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENTE_SQL)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getDireccion());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setInt(4, cliente.getIdCliente());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteCliente(int id) {
        boolean rowDeleted = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENTE_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
