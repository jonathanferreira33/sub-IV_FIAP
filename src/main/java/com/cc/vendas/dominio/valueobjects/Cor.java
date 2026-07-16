package com.cc.vendas.dominio.valueobjects;

import com.cc.vendas.dominio.excecao.RegraNegocioException;

import java.util.Set;

public record Cor(String valor) {

    private static final Set<String> CORES_PERMITIDAS = Set.of(
            "ABOBORA", "ACAFRAO", "AMARELO", "AMBAR", "AMEIXA", "AMENDOA", "AMETISTA",
            "ANIL", "AZUL", "BEGE", "BORDO", "BRANCO", "BRONZE", "CAQUI", "CARAMELO",
            "CARMESIM", "CARMIM", "CASTANHO", "CEREJA", "CHOCOLATE", "CIANO", "CINZA",
            "CINZENTO", "COBRE", "CORAL", "CREME", "DAMASCO", "DOURADO", "ESCARLATE",
            "ESMERALDA", "FERRUGEM", "FUCSIA", "GELO", "GRENA", "GRIS", "INDIGO", "JADE",
            "JAMBO", "LARANJA", "LAVANDA", "LILAS", "LIMAO", "LOIRO", "MAGENTA", "MALVA",
            "MARFIM", "MARROM", "MOSTARDA", "NEGRO", "OCRE", "OLIVA", "OURO", "PESSEGO",
            "PRATA", "PRETO", "PURPURA", "ROSA", "ROXO", "RUBRO", "SALMAO", "SEPIA",
            "TERRACOTA", "TIJOLO", "TURQUESA", "UVA", "VERDE", "VERMELHO", "VINHO", "VIOLETA"
    );

    public Cor {
        if (valor == null || valor.isBlank()) {
            throw new RegraNegocioException("A cor é obrigatória");
        }

    String valorNormalizado = valor.replace(" ", "").toUpperCase();

        if (!CORES_PERMITIDAS.contains(valorNormalizado)) {
            throw new RegraNegocioException("A cor '" + valor + "' não é permitida pelo sistema");
        }

       valor = valorNormalizado;
    }

    @Override
    public String toString() {
        return valor;
    }
}
