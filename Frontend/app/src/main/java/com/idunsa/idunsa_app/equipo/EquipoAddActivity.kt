package com.idunsa.idunsa_app.equipo

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.EquipoRequest
import com.idunsa.idunsa_app.network.RetrofitClient
import kotlinx.coroutines.launch

class EquipoAddActivity : AppCompatActivity() {

    private lateinit var nombreEquipoInput: EditText
    private lateinit var capitanInput: EditText
    private lateinit var participantesLayout: LinearLayout
    private lateinit var suplentesLayout: LinearLayout
    private lateinit var guardarButton: Button
    private lateinit var atrasButton: Button

    private val participanteInputs = mutableListOf<EditText>()
    private val suplenteInputs = mutableListOf<EditText>()

    private var torneoId: Long = -1
    private var jugadores: Int = 0
    private var suplentes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_add)

        torneoId = intent.getLongExtra("torneo_id", -1)
        jugadores = intent.getIntExtra("deporte_jugadores", 0)
        suplentes = intent.getIntExtra("deporte_suplentes", 0)
        Log.d("EquipoAddActivity", "Jugadores: $jugadores, Suplentes: $suplentes")

        nombreEquipoInput = findViewById(R.id.nombreEquipoInput)
        capitanInput = findViewById(R.id.capitanInput)
        participantesLayout = findViewById(R.id.participantesLayout)
        suplentesLayout = findViewById(R.id.suplentesLayout)
        guardarButton = findViewById(R.id.guardarButton)
        atrasButton = findViewById(R.id.atrasButton)

        for (i in 1 until jugadores) {
            val input = crearCampoEstilizado("Participante $i")
            participantesLayout.addView(input.first)
            participanteInputs.add(input.second)
        }

        for (i in 1..suplentes) {
            val input = crearCampoEstilizado("Suplente $i (opcional)")
            suplentesLayout.addView(input.first)
            suplenteInputs.add(input.second)
        }

        guardarButton.setOnClickListener {
            val nombreEquipo = nombreEquipoInput.text.toString().trim()
            val capitan = capitanInput.text.toString().toIntOrNull()

            if (nombreEquipo.isEmpty() || capitan == null) {
                Toast.makeText(this, "Completa nombre y capitán", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val participantes = participanteInputs.mapNotNull {
                val text = it.text.toString()
                if (text.isNotEmpty()) text.toIntOrNull() else null
            }

            if (participantes.size != jugadores - 1) {
                Toast.makeText(this, "Todos los participantes son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val suplentesList = suplenteInputs.mapNotNull {
                val text = it.text.toString()
                if (text.isNotEmpty()) text.toIntOrNull() else null
            }

            val request = EquipoRequest(
                nombre = nombreEquipo,
                capitanId = capitan,
                integranteIds = participantes,
                torneoId = torneoId
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.crearEquipo(request)
                    if (response.isSuccessful) {
                        Toast.makeText(this@EquipoAddActivity, "Equipo creado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EquipoAddActivity, "Error al crear equipo", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@EquipoAddActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        atrasButton.setOnClickListener {
            finish()
        }
    }

    private fun crearCampoEstilizado(hint: String): Pair<TextInputLayout, EditText> {
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 24, 0, 0)
        }

        val textInputLayout = TextInputLayout(this).apply {
            this.layoutParams = layoutParams
            boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
            boxStrokeColor = getColor(R.color.colorPrimaryLight)
            setBoxCornerRadii(12f, 12f, 12f, 12f)
            boxStrokeWidth = 2
            setBoxBackgroundColorResource(R.color.translucentWhite)
            hintTextColor = getColorStateList(R.color.black)
        }

        val editText = TextInputEditText(this).apply {
            this.hint = hint
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setTextColor(getColor(R.color.black))
        }

        textInputLayout.addView(editText)
        return Pair(textInputLayout, editText)
    }
}
