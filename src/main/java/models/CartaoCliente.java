package models;

public class CartaoCliente {

    private String titular;
    private final String numeroCartao;
    private double pontos;

    public CartaoCliente() {
        this.titular = "";
        this.numeroCartao = String.format("%d-%d-%d-%d", gerarNum(), gerarNum(), gerarNum(), gerarNum());
        this.pontos = 0;
    }

    public CartaoCliente(String titular) {
        this.titular = titular;
        this.numeroCartao = String.format("%d-%d-%d-%d", gerarNum(), gerarNum(), gerarNum(), gerarNum());
        this.pontos = 100; // retirar os pontos
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return "CartaoCliente{" + "titular='" + titular + '\'' + ", numeroCartao=" + numeroCartao + ", pontos=" + pontos + '}';
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
