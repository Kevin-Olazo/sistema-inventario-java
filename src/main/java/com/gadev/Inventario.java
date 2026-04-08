package com.gadev;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventario<T extends ProductoBase> {
    private List<T> items;

    public Inventario() {
        this.items = new ArrayList<>();
    }

    public Inventario(List<T> items) {
        this.items = items;
    }

    // CRUD BASICO

    public boolean agregar(T item){
        if (item != null){
            return items.add(item);
        } else {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }

    public boolean eliminar(T item){
        return items.remove(item);
    }

    public List<T> obtenerTodos(){
        return List.copyOf(items);
    }

    public Optional<T> buscarPorId(int id){
        return items.stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }
}
