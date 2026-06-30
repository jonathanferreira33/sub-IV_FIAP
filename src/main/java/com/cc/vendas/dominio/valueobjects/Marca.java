package com.cc.vendas.dominio.valueobjects;

import com.cc.vendas.dominio.excecao.RegraNegocioException;

import java.util.Set;

public record Marca(String valor) {

    private static final Set<String> MARCAS_PERMITIDAS = Set.of(
            "AGRALE", "AUDI", "BENTLEY", "BMW", "CHANGAN", "CHERY", "GM", "CHEVROLET", "CHRYSLER", "CITROËN", "CITROEN",
            "EFFA",  "FERRARI", "FIAT", "FORD", "GEELY", "HAFEI", "HONDA", "HYUNDAI", "IVECO", "JAC MOTORS", "JAGUAR",
            "JEEP", "JINBEI", "KIA", "LAMBORGHINI", "LAND ROVER", "LEXUS", "LIFAN", "MAHINDRA", "MASERATI",
            "MERCEDES-BENZ", "MERCEDES BENZ", "MG MOTORS", "MINI", "MITSUBISHI", "NISSAN", "PEUGEOT", "PORSCHE", "RAM",
            "RENAULT", "SMART", "SSANGYONG", "SUBARU", "SUZUKI", "TOYOTA", "TROLLER", "VOLKSWAGEN", "VW", "VOLVO"
    );

    public Marca {
        if (valor == null || valor.isBlank()) {
            throw new RegraNegocioException("O marca é obrigatório");
        }

        String valorNormalizado = valor.replace(" ", "").toUpperCase();

        if (!MARCAS_PERMITIDAS.contains(valorNormalizado)) {
            throw new RegraNegocioException("A marca '" + valor + "' não é permitida pelo sistema");
        }

        valor = valorNormalizado;
    }

    @Override
    public String toString() {
        return valor;
    }
}
