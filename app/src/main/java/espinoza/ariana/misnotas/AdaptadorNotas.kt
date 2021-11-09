package espinoza.ariana.misnotas

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.widget.*
import java.io.File

class AdaptadorNotas: BaseAdapter {
    var context:Context
    var notas=ArrayList<Nota>()

    constructor(context:Context, notas:ArrayList<Nota>){
        this.context=context
        this.notas=notas
    }

    override fun getCount(): Int {
        return notas.size
    }

    override fun getItem(p0: Int): Any {
        return notas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout, null)
        var nota = notas[p0]


        var titulo = vista.findViewById(R.id.tv_titulo_det) as TextView
        var contenido = vista.findViewById(R.id.tv_contenido_det) as TextView
        val btn_Borrar = vista.findViewById(R.id.btn_borrar) as ImageView

        titulo.setText(nota.titulo)
        contenido.setText(nota.contenido)

        btn_Borrar.setOnClickListener{
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    private fun eliminar(titulo:String){
        if(titulo == ""){
            Toast.makeText(context, "Error, titulo vacio",Toast.LENGTH_SHORT).show()
        } else {
            try{
                val archivo= File(ubicacion(), titulo + ".txt")
                archivo.delete()

                Toast.makeText(context, "Se elimino el archivo", Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion():String{
        val album = File(context?.getExternalFilesDir(null), "notas")
        if(!album.exists()){
            album.mkdir()
        }
        return album.absolutePath
    }
}