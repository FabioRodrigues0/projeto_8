import fabiorodrigues.bricks.components.*;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.core.Effect;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.style.Modifier;

import java.util.List;

import models.CartaoCliente;

/**
 * Ponto de entrada da aplicação Bricks. UI declarativa com estado reativo e
 * base de dados SQLite integrada.
 */
public class App extends BricksApplication {

  // ── Estado ────────────────────────────────────────────────────────────────

  private final State<String> titulo = state("Olá, Bricks!");
  private final StateList<CartaoCliente> listaCartoes = stateList(List.of());
  private final State<String> titular = state("");
  private final State<String> numero = state("");

  {
    setTitle("App");
    // setTheme(BricksTheme.dark()); // descomenta para dark mode
  }

  // ── Effects ───────────────────────────────────────────────────────────────

  // Cria o schema da base de dados no arranque
  private final Effect initDB = effect(() -> DatabaseSchema.create());

  // ── root() ────────────────────────────────────────────────────────────────
  private void criarCartao() {
    CartaoCliente cliente;
    if (titular.get().equals("")) {
      cliente = new CartaoCliente();
    } else {
      cliente = new CartaoCliente(titular.get());
    }
    listaCartoes.add(cliente);
  }

  @Override
  public Component root() {
    return new Column().padding(20).gap(12).modifier(new Modifier().fillMaxWidth()).children(
        new Text(titulo.get()).modifier(new Modifier().fontSize(24).bold()),
        new Button("Adicionar Cartão").onClick(() -> {
          Modal.show(this, modal -> new Column().children(
              new Text("Adicionar Cartão").fontSize(18),
              new TextField().label("Titular:").bindTo(titular),
              new Button("Criar").onClick(() -> {
                criarCartao();
                modal.close();
              }),
              new Button("Cancelar").onClick(() -> modal.close())));
        }),
        new LazyColumn<CartaoCliente>().gap(12).padding(16).items(listaCartoes.get())
            .emptyState(new Text("Nenhuma nota."))
            .item(cartao -> new Row().gap(8).children(
                new Card().gap(8).padding(16).width(280).height(176).elevation(2).children(
                    new Spacer(),
                    new Text(String.valueOf(cartao.getNumeroCartao())).fontSize(13),
                    new Text(cartao.getTitular()).modifier(new Modifier().bold().fontSize(15))
                ),
                new Column().gap(8).children(
                    new Button("Debitar").onClick(() -> {
                    }),
                    new Button("Transferir").onClick(() -> {
                    })))));
  }

  /**
   * Ponto de entrada da aplicação.
   *
   * @param args argumentos da linha de comandos
   */
  public static void main(String[] args) {
    launch(args);
    // CartaoCliente cliente1 = new CartaoCliente();

    // cliente1.setTitular("João");
    // cliente1.setNumeroCartao(100001);
    // cliente1.setPontos(100);
    // System.out.println(cliente1);
    // System.out.println("");

    // CartaoCliente cliente2 = new CartaoCliente("Maria", 100002);
    // cliente2.setPontos(100);
    // System.out.println(cliente2);
    // System.out.println("");

    // cliente1.debitarPontos(45.5);
    // System.out.println(cliente1);

    // cliente1.transferirPontos(cliente2);
    // System.out.println("1º cliente: " + cliente1); // Exibe informações
    // atualizadas do cliente1
    // System.out.println("2º cliente: " + cliente2); // Exibe informações do
    // cliente2

    // cliente2.debitarPontos(20);
    // System.out.println("2º cliente: " + cliente2);

    // System.out.println("1º cliente: " + cliente1); // Exibe informações
    // atualizadas do cliente1
    // System.out.println("2º cliente: " + cliente2); // Exibe informações do
    // cliente2
  }
}
