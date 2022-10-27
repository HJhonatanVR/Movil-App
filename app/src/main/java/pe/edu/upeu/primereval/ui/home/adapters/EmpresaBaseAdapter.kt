package pe.edu.upeu.primereval.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upeu.primereval.databinding.ItemEmpresaBinding
import pe.edu.upeu.primereval.domain.model.Empresa
import pe.edu.upeu.primereval.utils.load

abstract class EmpresaBaseAdapter (
    private val layoutId : Int
): RecyclerView.Adapter<EmpresaBaseAdapter.BichoViewHolder>() {

    class BichoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemEmpresaBinding.bind(itemView)

        fun render(bicho: Empresa) {

            binding.tvTile.text = bicho.title
            binding.tvDescription.text = bicho.description
            binding.ivImage.load(bicho.image)

        }
    }

    protected val diffCallback = object : DiffUtil.ItemCallback<Empresa>(){
        override fun areItemsTheSame(oldItem: Empresa, newItem: Empresa): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Empresa, newItem: Empresa): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ : AsyncListDiffer<Empresa>

    open var bichos : List<Empresa>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BichoViewHolder {
        return BichoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )
    }

    protected var onItemClickListener : ((Empresa) -> Unit)? = null
    protected var onDeleteClickListener : ((Empresa) -> Unit)? = null

    fun setItemClickListener(listener: (Empresa) -> Unit){
        onItemClickListener = listener
    }

    fun setDeleteClickListener(listener: (Empresa) -> Unit){
        onDeleteClickListener = listener
    }

    override fun getItemCount(): Int {
        return bichos.size
    }

}