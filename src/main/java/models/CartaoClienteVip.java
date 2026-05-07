package models;


public class CartaoClienteVip extends CartaoCliente {

  private final Double desconto;

  public CartaoClienteVip() {
    super();
    this.desconto = 0.7; // Desconto padrão (10%)
  }

  public CartaoClienteVip(String titular) {
    super(titular);
    this.desconto = 0.7;
  }

  public CartaoClienteVip(int id, String titular, String numeroCartao, double pontos, int idCores, Double desconto) {
    super(id, titular, numeroCartao, pontos, idCores);
    this.desconto = desconto;
  }

  @Override
  public Double getDesconto() {
    return this.desconto;
  }

  @Override
  public void debitarPontos(double pontos) {
    double taxa = 0.02; // 2% de taxa
    double taxaEfetiva = taxa * (1 - this.desconto);
    double totalComTaxa = pontos + (1 + taxaEfetiva);

    if (this.getPontos() >= totalComTaxa) {
      double novosPontos = this.getPontos() - totalComTaxa;
      this.setPontos(round2(novosPontos));
    } else {
      System.out.println("Não há pontos suficientes para débito.");
    }
  }


  private double round2(double value) {
    return Math.round(value * 100.0) / 100.0;
  }

  @Override
  public void transferirPontos(CartaoCliente destinatario, double pontos) {
    double taxa = 0.02;
    double taxaEfetiva = taxa * (1 - this.getDesconto());

    double custo = pontos * (1 + taxaEfetiva);
    custo = round2(custo);

    if (this.getPontos() >= custo) {
      this.setPontos(round2(this.getPontos() - custo));
      destinatario.setPontos(round2(destinatario.getPontos() + pontos));
    } else {
      System.out.println("Não há pontos suficientes no cartão original para transferência.");
    }
  }

  @Override
  public String toString() {
    return "CartaoClienteVip{" + "id=" + this.getId() + ", titular='" + this.getTitular() + ", numeroCartao=" + this
      .getNumeroCartao() + ", pontos=" + this.getPontos() + ", desconto" + this.desconto + '}';
  }
}
