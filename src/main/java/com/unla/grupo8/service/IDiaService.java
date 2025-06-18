package com.unla.grupo8.service;

import java.util.List;

import com.unla.grupo8.entities.Dia;

public interface IDiaService {
    List<Dia> obtenerDiasPorSucursal(Long idSucursal);
}
