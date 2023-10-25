package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private final String INSERT_EMPLEADO_SQL = "INSERT INTO Empleado (nombre, cargo) VALUES (?, ?)";
    private final String SELECT_EMPLEADO_BY_ID = "SELECT idEmpleado, nombre, cargo FROM Empleado WHERE idEmpleado = ?";
    private final String SELECT_ALL_EMPLEADOS = "SELECT * FROM Empleado";
    private final String UPDATE_EMPLEADO_SQL = "UPDATE Empleado SET nombre = ?, cargo = ? WHERE idEmpleado = ?";
    private final String DELETE_EMPLEADO_SQL = "DELETE FROM Empleado WHERE idEmpleado = ?";

    private final DatabaseConfig databaseConfig;

    public EmpleadoDAO() {
        databaseConfig = new DatabaseConfig();
    }

    public void insertEmpleado(Empleado empleado) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLEADO_SQL)) {
            preparedStatement.setString(1, empleado.getNombre());
            preparedStatement.setString(2, empleado.getCargo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Empleado selectEmpleado(int id) {
        Empleado empleado = null;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLEADO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String cargo = resultSet.getString("cargo");
                empleado = new Empleado(id, nombre, cargo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleado;
    }

    public List<Empleado> selectAllEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLEADOS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("idEmpleado");
                String nombre = resultSet.getString("nombre");
                String cargo = resultSet.getString("cargo");
                empleados.add(new Empleado(id, nombre, cargo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public boolean updateEmpleado(Empleado empleado) {
        boolean rowUpdated = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLEADO_SQL)) {
            preparedStatement.setString(1, empleado.getNombre());
            preparedStatement.setString(2, empleado.getCargo());
            preparedStatement.setInt(3, empleado.getIdEmpleado());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteEmpleado(int id) {
        boolean rowDeleted = false;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLEADO_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}

