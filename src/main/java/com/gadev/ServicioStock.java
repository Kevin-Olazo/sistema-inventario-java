package com.gadev;

public interface ServicioStock {
    void incrementarStock(int cantidad);
    void disminuirStock(int cantidad) throws StockInsuficienteException;
}
