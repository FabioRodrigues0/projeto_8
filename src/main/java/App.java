import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.LazyColumn;
import fabiorodrigues.bricks.components.Text;
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
    private final StateList<CartaoCliente> listaCartoes = stateList(null);

    {
        setTitle("App");
        // setTheme(BricksTheme.dark()); // descomenta para dark mode
    }

    // ── Effects ───────────────────────────────────────────────────────────────

    // Cria o schema da base de dados no arranque
    private final Effect initDB = effect(() -> DatabaseSchema.create());

    // ── root() ────────────────────────────────────────────────────────────────

    @Override
    public Component root() {
        CartaoCliente cliente2 = new CartaoCliente("Paulo", 102314);
        cliente2.setPontos(100);

        CartaoCliente cliente1 = new CartaoCliente("Maria", 103202);
        cliente1.setPontos(100);

        List<CartaoCliente> ss = List.of(cliente1, cliente2);

        listaCartoes.set(0, cliente1);
        listaCartoes.set(1, cliente2);

        return new Column().padding(20).gap(12).modifier(new Modifier().fillMaxWidth()).children(
                new Text(titulo.get()).modifier(new Modifier().fontSize(24).bold()),
                new Button("Adicionar Cartão").onClick(() -> {
                    titulo.set("Botão clicado!");
                }),
                new LazyColumn<CartaoCliente>().gap(12).padding(16).items(listaCartoes.get())
                        .emptyState(new Text("Nenhuma nota."))
                        .item(cartao -> new Card().padding(16).elevation(2).children(
                                new Text(cartao.getTitular()).fontSize(15),
                                new Text(String.valueOf(cartao.getNumeroCartao())).fontSize(13))));
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args
     *            argumentos da linha de comandos
     */
    public static void main(String[] args) {
        launch(args);
        CartaoCliente cliente1 = new CartaoCliente();

        cliente1.setTitular("João");
        cliente1.setNumeroCartao(100001);
        cliente1.setPontos(100);
        System.out.println(cliente1);
        System.out.println("");

        CartaoCliente cliente2 = new CartaoCliente("Maria", 100002);
        cliente2.setPontos(100);
        System.out.println(cliente2);
        System.out.println("");

        cliente1.debitarPontos(45.5);
        System.out.println(cliente1);

        cliente1.transferirPontos(cliente2);
        System.out.println("1º cliente: " + cliente1); // Exibe informações atualizadas do cliente1
        System.out.println("2º cliente: " + cliente2); // Exibe informações do cliente2

        cliente2.debitarPontos(20);
        System.out.println("2º cliente: " + cliente2);

        System.out.println("1º cliente: " + cliente1); // Exibe informações atualizadas do cliente1
        System.out.println("2º cliente: " + cliente2); // Exibe informações do cliente2
    }
}
