import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_BANCO) {

    companion object {
        private const val NOME_BANCO = "ecodicas.db"
        private const val VERSAO_BANCO = 1
        private const val TABELA_DICAS = "dicas"
        private const val COLUNA_ID = "id"
        private const val COLUNA_TITULO = "titulo"
        private const val COLUNA_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val criarTabela = ("CREATE TABLE $TABELA_DICAS ($COLUNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUNA_TITULO TEXT, $COLUNA_DESCRICAO TEXT)")
        db.execSQL(criarTabela)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun adicionarDica(dica: Dica) {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put(COLUNA_TITULO, dica.titulo)
            put(COLUNA_DESCRICAO, dica.descricao)
        }
        db.insert(TABELA_DICAS, null, valores)
        db.close()
    }

    fun obterTodasDicas(): List<Dica> {
        val listaDicas = mutableListOf<Dica>()
        val consulta = "SELECT * FROM $TABELA_DICAS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(consulta, null)
        if (cursor.moveToFirst()) {
            do {
                val dica = Dica(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_TITULO)),
                    descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DESCRICAO))
                )
                listaDicas.add(dica)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaDicas
    }
}
