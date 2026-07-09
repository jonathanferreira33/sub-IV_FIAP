package com.cc.vendas.infraestrutura.adaptadores.saida.mensageria;

import com.cc.vendas.shared.enums.CardType;

import java.util.UUID;

public record CardRequest(
        UUID id,
        String holderName,
        String number,
        String expiration,
        String cvv,
        CardType type
) {}