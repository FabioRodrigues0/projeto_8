import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.core.Effect;
import views.HomeView;
import views.HomeViewModel;

/**
 * Ponto de entrada da aplicação Bricks. UI declarativa com estado reativo e base de dados
 * SQLite integrada.
 */
public class App extends BricksApplication {

  public final HomeViewModel vm = new HomeViewModel();

  {
    setTitle("App");
    setInitialScene(new HomeView(this, vm));
    setSize(820, 800);
    // setTheme(BricksTheme.dark()); // descomenta para dark mode
  }

  // ── Effects ───────────────────────────────────────────────────────────────

  // Cria o schema da base de dados no arranque
  private final Effect initDB = effect(() -> DatabaseSchema.create());

  @Override
  public Component root() {
    return currentScene() != null ? currentScene().render() : new Text("A carregar...");
  }

  /**
   * Ponto de entrada da aplicação.
   *
   * @param args argumentos da linha de comandos
   */
  public static void main(String[] args) {
    launch(args);
  }
}
