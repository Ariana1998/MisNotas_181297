package espinoza.ariana.misnotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.content.Intent
import android.widget.Button
import android.content.Context
import java.io.*

class MainActivity : AppCompatActivity() {
    private var notas=ArrayList<Nota>()
    private lateinit var adaptador:AdaptadorNotas
    private val lista: ListView = findViewById(R.id.listview)
    private val fab: Button = findViewById(R.id.fab)
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{
            var intent = Intent(this, AgregarNotaActivity::class.java)
            startActivity(intent)
        }

        leerNotas()

        adaptador= AdaptadorNotas(this,notas)
        lista.adapter=adaptador

    }

    fun leerNotas(){
        notas.clear()
        var carpeta = File(ubicacion().absolutePath)

        if(carpeta.exists()){
            var archivos = carpeta.listFiles()
            if(archivos != null){
                for(archivo in archivos){
                    leerArchivo(archivo)
                }
            }
        }
    }

    fun leerArchivo(archivo: File){
        val fis = FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData = ""

        while(strLine != null){
            myData = myData + strLine
            strLine= br.readLine()
        }
        br.close()
        di.close()
        fis.close()

        var nombre = archivo.name.substring(0,archivo.name.length-4)

        var nota = Nota(nombre, myData)
        notas.add(nota)

    }

    private fun ubicacion(): File {
        val folder = File(context?.getExternalFilesDir(null),"notas ")
        if(!folder.exists()){
            folder.mkdir()
        }
        return folder
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 123){
            leerNotas()
            adaptador.notifyDataSetChanged()
        }
    }


}