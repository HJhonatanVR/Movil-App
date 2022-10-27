package pe.edu.upeu.primereval.ui.home.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import pe.edu.upeu.primereval.R
import pe.edu.upeu.primereval.domain.model.Empresa
import kotlinx.android.synthetic.main.item_empresa.view.*
import javax.inject.Inject

class EmpresaAdapter @Inject constructor(

) : EmpresaBaseAdapter(R.layout.item_empresa) {

    override val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list : List<Empresa>) = differ.submitList(list)


    override fun onBindViewHolder(holder: BichoViewHolder, position: Int) {
        val bicho = bichos[position]

        holder.render(bicho)

        holder.itemView.btnDelete.setOnClickListener {
            onDeleteClickListener?.let { click ->
                click(bicho)
            }
        }

        holder.itemView.apply {

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(bicho)
                }
            }
        }
    }
}