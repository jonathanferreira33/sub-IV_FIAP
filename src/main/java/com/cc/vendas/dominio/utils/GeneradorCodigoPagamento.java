package com.cc.vendas.dominio.utils;

import java.util.Random;

public class GeneradorCodigoPagamento {

    private static final Random random = new Random();

    public static String gerarCodigo() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            char letra = (char) ('A' + random.nextInt(26));
            sb.append(letra);
        }

        sb.append("-");

        for (int i = 0; i < 4; i++) {
            int numero = random.nextInt(10);
            sb.append(numero);
        }

        return sb.toString();
    }
}
