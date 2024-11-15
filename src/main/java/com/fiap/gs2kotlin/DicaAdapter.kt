import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fiap.gs2kotlin.R

class DicaAdapter(private var listaDicas: List<Dica>) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>() {

    var onItemClick: ((Dica) -> Unit)? = null

    inner class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloDica: TextView = itemView.findViewById(R.id.tituloDica)
        val descricaoDica: TextView = itemView.findViewById(R.id.descricaoDica)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listaDicas[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = listaDicas[position]
        holder.tituloDica.text = dica.titulo
        holder.descricaoDica.text = dica.descricao
    }

    override fun getItemCount(): Int {
        return listaDicas.size
    }

    fun atualizarLista(novaLista: List<Dica>) {
        listaDicas = novaLista
        notifyDataSetChanged()
    }
}
