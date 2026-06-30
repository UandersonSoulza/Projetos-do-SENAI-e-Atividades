package com.mycompany.stockcontrol.util;

public class Validador {

    // Verifica se um texto está vazio ou nulo
    public static boolean isCampoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    // Verifica se o texto digitado é um número inteiro válido (para Quantidade)
    public static boolean isNumeroInteiroValido(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Verifica se o texto digitado é um número decimal válido (para Preço)
    public static boolean isNumeroDecimalValido(String texto) {
        try {
            // Troca vírgula por ponto caso o usuário digite "10,50" em vez de "10.50"
            Double.parseDouble(texto.replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}