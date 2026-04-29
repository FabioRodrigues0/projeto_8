package models;

public class CartaoClienteVip extends CartaoCliente {
    private final double desconto;

    public CartaoClienteVip() {
        super();
        this.desconto = 0.1; // Desconto padrão (10%)
    }

    public CartaoClienteVip(String titular, int numeroCartao, double desconto) {
        super(titular, numeroCartao);
        this.desconto = desconto;
    }

    public double getDesconto() {
        return desconto;
    }
}
