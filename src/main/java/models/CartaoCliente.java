package models;

import java.util.Random;

public class CartaoCliente {

    private int id;
    private String titular;
    private final String numeroCartao;
    private double pontos;
    private int id_cores;
    private final CardGradient cores;

    public CartaoCliente() {
        this.id = 0;
        this.titular = "";
        this.numeroCartao = String.format("%d-%d-%d-%d", gerarNum(), gerarNum(), gerarNum(), gerarNum());
        this.pontos = 0;
        this.id_cores = 0;
        this.cores = CardGradient.gradientForCard(new Random().nextInt(7));
    }

    public CartaoCliente(String titular) {
        this.titular = titular;
        this.numeroCartao = String.format("%d-%d-%d-%d", gerarNum(), gerarNum(), gerarNum(), gerarNum());
        this.pontos = 100; // retirar os pontos
        this.id_cores = new Random().nextInt(7);
        this.cores = CardGradient.gradientForCard(this.id_cores);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    public double getPontos() {
        return this.pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    public int getIdCores() {
        return this.id_cores;
    }

    public void setIdCores(int id_cores) {
        this.id_cores = id_cores;
    }

    public CardGradient getCores() {
        return this.cores;
    }

    @Override
    public String toString() {
        return "" + titular;
    }

    public void debitarPontos(double pontos) {
        if (this.pontos >= pontos) { // Verifica se há pontos suficientes para debitar
            this.pontos -= pontos; // Subtrai os pontos do total
        } else {
            System.out.println("Não há pontos suficientes para débito.");
        }
    }

    public void transferirPontos(CartaoCliente destinatario) {
        transferirPontos(destinatario, this.pontos); // Transfere todos os pontos
                                                    // disponíveis
    }

    public void transferirPontos(CartaoCliente destinatario, double pontos) {
        if (this.pontos >= pontos) { // Verifica se o remetente tem pontos suficientes
            this.debitarPontos(pontos); // Debita os pontos do remetente
            destinatario.setPontos(destinatario.getPontos() + pontos); // Adiciona os
                                                                      // pontos ao
                                                                      // destinatário
        } else {
            System.out.println("Não há pontos suficientes no cartão original para transferência.");
        }
    }

    public void mostrarPontos() {
        System.out.println("Pontos disponíveis: " + getPontos());
    }

    private int gerarNum() {
        int min = 0;
        int max = 9999;
        int range = max - min + 1;

        return (int) (Math.random() * range) + min;
    }


}
