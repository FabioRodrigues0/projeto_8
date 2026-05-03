package views;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;

import java.util.List;
import models.CartaoCliente;

public class HomeViewModel extends BricksViewModel {


    // ── Estado ────────────────────────────────────────────────────────────────
    protected final StateList<CartaoCliente> listaCartoes = stateList(List.of());
    protected final State<String> titular = state("");
    protected final State<String> novoTitular = state("");
    protected final State<String> valorDebitar = state("");
    protected final State<CartaoCliente> clientSelecionado = state(null);
    protected final State<String> valorTransferir = state("");


    public void criarCartao() {
        CartaoCliente cliente;

        if (titular.get().equals("")) {
            cliente = new CartaoCliente();

            DB.query()
                .insertInto("cartao_client")
                .value("numero_cartao", cliente.getNumeroCartao())
                .value("ponto", cliente.getPontos())
                .value("id_cores", cliente.getIdCores())
                .execute();
            
        } else {
            cliente = new CartaoCliente(titular.get());                

            DB.query()
                    .insertInto("cartao_client")
                .value("titular", cliente.getTitular())
                .value("numero_cartao", cliente.getNumeroCartao())
                .value("ponto", cliente.getPontos())
                .value("id_cores", cliente.getIdCores())
                .execute();
        }
        listaCartoes.add(cliente);
    }

    public void debitar(int id, double quantidade) {
        CartaoCliente cartao = listaCartoes.get(id);

        cartao.debitarPontos(quantidade);
    }
}
