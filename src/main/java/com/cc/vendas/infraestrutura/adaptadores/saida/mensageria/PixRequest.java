package com.cc.vendas.infraestrutura.adaptadores.saida.mensageria;

import java.time.LocalDateTime;
import java.util.UUID;

public record PixRequest(
        UUID id,
        String key,
        LocalDateTime expiration
) {}