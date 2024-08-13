package test;

import dao.impl.DaoOdontologoH2;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    static final Logger logger = Logger.getLogger(OdontologoServiceTest.class);

    OdontologoService OdontologoService = new OdontologoService(new DaoOdontologoH2());

    @BeforeAll
    static void crateTablas(){
    Connection connection = null;
        try{
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./odontologos;INIT=RUNSCRIPT FROM 'create.sql'", "sa","sa");
        }catch (Exception e){
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
    @Test
    @DisplayName("Testear que un Odontologo se guarde correctamente en la base de datos.")
    void caso1() {
        //dado
        Odontologo odontologo = new Odontologo(25338, "Roxan", "Lovelace");
        Odontologo odontologoDesdeLaBD = OdontologoService.guardarOdontologo(odontologo);
        // probamos el error forzado para que no se cargue
        // int num = 4/0;
        assertNotNull(odontologoDesdeLaBD.getId());
    }

    @Test
    @DisplayName("Testear que se muestren todos los odontologos de la bd.")
    void caso2() {
        //dado
        List<Odontologo> Odontologos = new ArrayList<>();
        Odontologos = OdontologoService.buscarTodos();
        assertTrue(!Odontologos.isEmpty());
    }
}

