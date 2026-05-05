
import fabiorodrigues.bricks.data.DB;

/**
 * Define o schema da base de dados. Chamado automaticamente no arranque via Effect em
 * App.java. Adiciona aqui as definições das tabelas.
 */
public class DatabaseSchema {

  private DatabaseSchema() {
  }

  /**
   * Cria as tabelas se não existirem. Seguro para chamar múltiplas vezes — usa CREATE
   * TABLE IF NOT EXISTS.
   */
  public static void create() {
    // Exemplo — descomenta e adapta:
    DB
      .query()
      .createTableIfNotExists("cartao_client")
      .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
      .column("titular", "TEXT UNIQUE")
      .column("numero_cartao", "TEXT NOT NULL UNIQUE")
      .column("pontos", "FLOAT NOT NULL")
      .column("id_cores", "INTEGER NOT NULL")
      .execute();
  }

}
