package models;

public class CartaoCliente {
    private String titular;
    private int numeroCartao;
    private double pontos;

    public CartaoCliente() {
        this.titular = "";
        this.numeroCartao = 0;
        this.pontos = 0;
    }

    public CartaoCliente(String titular, int numeroCartao) {
        this.titular = titular;
        this.numeroCartao = numeroCartao;
        this.pontos = 0; // retirar os pontos
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(int numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }
    @Override
    public String toString() {
        return "CartaoCliente{" + "titular='" + titular + '\'' + ", numeroCartao=" + numeroCartao
                + ", pontos=" + pontos + '}';
    }
    public void debitarPontos(double pontos) {
        if (this.pontos >= pontos) { // Verifica se há pontos suficientes para debitar
            this.pontos -= pontos; // Subtrai os pontos do total
        } else {
            System.out.println("Não há pontos suficientes para débito.");
        }
    }

    public void transferirPontos(CartaoCliente destinatario) {
        transferirPontos(destinatario, this.pontos); // Transfere todos os pontos disponíveis
    }

    public void transferirPontos(CartaoCliente destinatario, double pontos) {
        if (this.pontos >= pontos) { // Verifica se o remetente tem pontos suficientes
            this.debitarPontos(pontos); // Debita os pontos do remetente
            destinatario.setPontos(destinatario.getPontos() + pontos); // Adiciona os pontos ao
                                                                       // destinatário
        } else {
            System.out.println("Não há pontos suficientes no cartão original para transferência.");
        }
    }
    public void mostrarPontos() {
        System.out.println("Pontos disponíveis: " + getPontos());
    }
}
