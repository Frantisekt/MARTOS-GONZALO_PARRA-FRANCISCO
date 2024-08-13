package dao.impl;

import dao.IDao;
import model.Odontologo;

import java.util.ArrayList;
import java.util.List;

public class DaoOdontologoMemoria implements IDao<Odontologo> {

    private List<Odontologo> odontologos;

    public DaoOdontologoMemoria() {
        // Inicializar la lista de odontólogos
        odontologos = new ArrayList<>();
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologos.add(odontologo);
        return odontologo;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return odontologos;
    }
}