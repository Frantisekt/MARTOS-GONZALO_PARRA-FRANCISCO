package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DaoOdontologoH2 implements IDao<Odontologo> {
    private static final Logger logger = Logger.getLogger(DaoOdontologoH2.class);
    private static final String INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT, ?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoARetornar = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement prst = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            prst.setInt(1, odontologo.getMatricula());
            prst.setString(2, odontologo.getNombre());
            prst.setString(3, odontologo.getApellido());
            prst.executeUpdate();
            connection.commit();

            //Recuperar la Key generada
            ResultSet rs = prst.getGeneratedKeys();
            Integer id = null;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            odontologoARetornar = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(),
                    odontologo.getApellido());

            logger.info("odontologo persistido " + odontologoARetornar);

        } catch (Exception e) {
            logger.error(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage());
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    logger.error(e.getMessage());
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return odontologoARetornar;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologo = null;

        try {
            connection = H2Connection.getConnection();
            Statement statemnent = connection.createStatement();
            ResultSet rs = statemnent.executeQuery(SELECT_ALL);

            while (rs.next()) {
                Integer id = rs.getInt(1);
                Integer matricula = rs.getInt(2);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                odontologo = new Odontologo(id, matricula, nombre, apellido);
                logger.info(odontologo);
                odontologos.add(odontologo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
        return odontologos;
    }
}
