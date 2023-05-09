package com.example.gamebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.gamebase.databinding.FragmentHomefragmentBinding
import com.example.gamebase.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRegistration.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRegistration : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tosignin=view.findViewById<TextView>(R.id.tosignin)
        val but=view.findViewById<TextView>(R.id.confirmButton)

       tosignin.setOnClickListener(){


            Navigation.findNavController(view).navigate(R.id.action_fragmentRegistration_to_homefragment)

        }

        //binding= FragmentRegistrationBinding.inflate(layoutInflater)
        firebaseAuth=FirebaseAuth.getInstance()


        binding.tosignin.setOnClickListener(){
            val intent= Intent(context,Homefragment::class.java)
            startActivity(intent)




        }


       but.setOnClickListener(){


            val email=binding.username.text.toString().trim()
            val pass=binding.password.text.toString().trim()
            val confpass=binding.passwordrepid.text.toString()



            if(email.isNotEmpty() && pass.isNotEmpty() && confpass.isNotEmpty()) {
                if(pass.equals(confpass)) {

                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{

                        if(it.isSuccessful){

                            Toast.makeText(
                                context,
                                "Aded Succesfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            Navigation.findNavController(view).navigate(R.id.action_fragmentRegistration_to_homefragment)
                            // val intent= Intent(context,Homefragment::class.java)
                            // startActivity(intent)
                        }else{

                            binding.loginText.text="nie dizałą"
                            Toast.makeText(
                                context,
                                it.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }

                }else {

                    Toast.makeText(
                        context,
                        "This passwords are not the same",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }else{
                Toast.makeText(
                    context,
                    "You have some empty field",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }}
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentRegistration.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentRegistration().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}