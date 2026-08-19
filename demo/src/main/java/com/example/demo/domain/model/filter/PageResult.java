package com.example.demo.domain.model.filter;

import java.util.List;

public record PageResult<T>(

    List<T> contenido,

    long totalElementos,

    int totalPaginas,

    int paginaActual,

    int tamaño,

    boolean primero,

    boolean ultimo

) {}