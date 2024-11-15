import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.gs2kotlin.R

class MainActivity : AppCompatActivity(){

    private lateinit var dicasRecyclerView: RecyclerView
    private lateinit var dicasAdapter: DicaAdapter
    private lateinit var searchView: SearchView
    private lateinit var dbHelper: Database
    private var listaDicas: List<Dica> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.TemaEcoDicas)
        setContentView(R.layout.activity_main)

        dbHelper = Database(this)
        listaDicas = dbHelper.obterTodasDicas()

        if (listaDicas.isEmpty()) {
            popularDadosIniciais()
            listaDicas = dbHelper.obterTodasDicas()
        }

        dicasRecyclerView = findViewById(R.id.dicasRecyclerView)
        searchView = findViewById(R.id.searchDicas)

        dicasAdapter = DicaAdapter(listaDicas)
        dicasRecyclerView.adapter = dicasAdapter
        dicasRecyclerView.layoutManager = LinearLayoutManager(this)

        dicasAdapter.onItemClick = { dica ->
            Toast.makeText(this, "Dica: ${dica.titulo}", Toast.LENGTH_SHORT).show()
        }

        configurarPesquisa()
    }

    private fun popularDadosIniciais() {
        val dicasIniciais = listOf(
            Dica(0, "Desligue aparelhos que não estão em uso", "Aparelhos eletrônicos consomem energia mesmo em modo de espera. Desconecte quando não for usar."),
            Dica(0, "Use lâmpadas LED", "Lâmpadas LED são mais eficientes e duram mais que as incandescentes."),
        )
        for (dica in dicasIniciais) {
            dbHelper.adicionarDica(dica)
        }
    }

    private fun configurarPesquisa() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val listaFiltrada = listaDicas.filter { dica ->
                    dica.titulo.contains(newText ?: "", ignoreCase = true) ||
                            dica.descricao.contains(newText ?: "", ignoreCase = true)
                }
                dicasAdapter.atualizarLista(listaFiltrada)
                return true
            }
        })
    }
}
