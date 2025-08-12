package com.rentas.xzrentcar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentaDAO {

    private ConectarBD conectarBD;

    public RentaDAO(ConectarBD conectarBD) {
        this.conectarBD = new ConectarBD();
    }

    // es importante realizar proceso de bind de variable al momento de trabajar con
    // sql
    public boolean guardarRenta(InfoRentaModel infoRentaModel) throws SQLException {
        String sql = "INSERT INTO info_renta " +
                "(dni, nombres, modelo, marca, fe_nacimiento, tiene_tc, fe_creacion, usr_creacion, estado) " +
                "VALUES (?,?,?,?,?,?,?,?,?) ";
        try {
            Connection connection = conectarBD.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, infoRentaModel.getDni());
            preparedStatement.setString(2, infoRentaModel.getNombres());
            preparedStatement.setString(3, infoRentaModel.getModelo());
            preparedStatement.setString(4, infoRentaModel.getMarca());
            preparedStatement.setDate(5, infoRentaModel.getFeNacimiento());
            preparedStatement.setString(6, infoRentaModel.getTieneTC());
            preparedStatement.setDate(7, infoRentaModel.getFeCreacion());
            preparedStatement.setString(8, infoRentaModel.getUsrCreacion());
            preparedStatement.setString(9, infoRentaModel.getEstado());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conectarBD.disconnect();
        }
        return true;
    }

    public List<RentaEntity> recuperarRegistro() throws SQLException {
        List<RentaEntity> lstInfoRenta = new ArrayList<>();
        String consulta = "SELECT * FROM info_renta " +
                " WHERE ESTADO = ? ";
        try {
            Connection connection = conectarBD.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            preparedStatement.setString(1, "Activo");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RentaEntity rentaEntity = new RentaEntity();
                rentaEntity.setDni(resultSet.getString("dni"));
                rentaEntity.setNombres(resultSet.getString("nombres"));
                rentaEntity.setModelo(resultSet.getString("modelo"));
                rentaEntity.setMarca(resultSet.getString("marca"));
                rentaEntity.setFeNacimiento(resultSet.getDate("fe_nacimiento"));
                rentaEntity.setTieneTc(resultSet.getString("tiene_tc"));
                lstInfoRenta.add(rentaEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            conectarBD.disconnect();
        }
        return lstInfoRenta;
    }

}