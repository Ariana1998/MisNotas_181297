package espinoza.ariana.misnotas

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream as FileOutputStream
import android.content.Context

class AgregarNotaActivity : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        val button: Button = findViewById(R.id.btn_guardar)


        button.setOnClickListener{
            guardar_nota()
        }
    }

    fun guardar_nota(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            235)
        }else{
            guardar()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            235 -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    guardar()
                } else {
                    Toast.makeText(this, "Error: Permisos Denegados", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    public fun guardar(){
        val titulo: EditText = findViewById(R.id.et_titulo)
        val cuerpo: EditText = findViewById(R.id.et_contenido)

        if(titulo.text.toString().equals("") || cuerpo.text.toString().equals("")){
            Toast.makeText(this, "Error:campos vacios", Toast.LENGTH_SHORT).show()
        } else {
            try{
                //val archivo= File(ubicacion(), titulo + ".txt")
                //val fos = FileOutputStream(archivo)
                //fos.write(cuerpo.toByteArray())
                //fos.close()
                Toast.makeText(
                    this,
                    "se guardo el archivo en la carpeta publica",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception){
                Toast.makeText(this, "Error: no se guardo el archivo", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun ubicacion(): String{
        val carpeta = File(context?.getExternalFilesDir(null),"notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta.absolutePath
    }





}