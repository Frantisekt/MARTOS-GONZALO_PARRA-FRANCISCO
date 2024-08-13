package test;

import dao.impl.DaoOdontologoMemoria;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class OdontologoServiceMemoriaTest {
    static Logger logger = Logger.getLogger(OdontologoServiceMemoriaTest.class);
    OdontologoService odontologoService = new OdontologoService(new DaoOdontologoMemoria());

    @Test
    @DisplayName("Testear que un odontologo se guarde en la bd.")
    void caso1(){
        Odontologo odontologo = new Odontologo(4,654123, "German", "Gutierrez");
        Odontologo odontologoDesdeBD = odontologoService.guardarOdontologo(odontologo);
        // probamos el error forzado para que no se cargue
        // int num = 4/0;
        assertNotNull(odontologoDesdeBD.getId());
    }


    @Test
    @DisplayName("Teste que s listen los odontologos")
    void caso2() {
        // se agregan odontólogos a la lista en memoria
        Odontologo odontologo1 = new Odontologo(1, 12345, "Juan", "Perez");
        Odontologo odontologo2 = new Odontologo(2, 67890, "Maria", "Gomez");
        Odontologo odontologo3 = new Odontologo(3, 54321, "Carlos", "Lopez");

        odontologoService.guardarOdontologo(odontologo1);
        odontologoService.guardarOdontologo(odontologo2);
        odontologoService.guardarOdontologo(odontologo3);

        // jalar todos los odontólogos de la lista en memoria
        List<Odontologo> odontologosEsperados = Arrays.asList(odontologo1, odontologo2, odontologo3);
        List<Odontologo> odontologos = odontologoService.buscarTodos();

        System.out.println("Lista de Odontólogos:");
        for (Odontologo odontologo : odontologos) {
            System.out.println(odontologo);
        }

        // comprobar que la lista contenga exactamente los odontólogos que se agregaron
        assertEquals(odontologosEsperados.size(), odontologos.size(), "El tamaño de la lista debe ser igual");
    }
}
