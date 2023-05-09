package com.example.gamebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.gamebase.databinding.FragmentHomefragmentBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Homefragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Homefragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
private lateinit var firebaseAuth:FirebaseAuth

        private var _binding: FragmentHomefragmentBinding? = null
    private val binding get() = _binding!!



   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
           

           override fun handleOnBackPressed() {}})
    firebaseAuth=FirebaseAuth.getInstance()



    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomefragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val buttonreg=view.findViewById<Button>(R.id.registrationButton)

buttonreg.setOnClickListener(){


    Navigation.findNavController(view).navigate(R.id.action_homefragment_to_fragmentRegistration)

}

        binding.loginButton.setOnClickListener(){



            val email=binding.username.text.toString().trim()
            val pass=binding.password.text.toString().trim()

            if(email.isNotEmpty() && pass.isNotEmpty() ) {


                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{

                    if(it.isSuccessful){

                        Toast.makeText(
                            context,
                            "zalogowano",
                            Toast.LENGTH_SHORT
                        ).show()

                        Navigation.findNavController(view).navigate(R.id.action_homefragment_to_menuFragment)
                      //  val intent= Intent(context,menuFragment::class.java)
                       // startActivity(intent)
                    }else{
                        Toast.makeText(
                            context,
                            it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }






            }else{
                Toast.makeText(
                    context,
                    "You have some empty field",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Homefragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Homefragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}