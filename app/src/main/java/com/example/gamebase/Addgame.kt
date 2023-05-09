package com.example.gamebase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.gamebase.databinding.FragmentAddgameBinding
import com.example.gamebase.databinding.FragmentHomefragmentBinding
import com.example.gamebase.databinding.FragmentRegistrationBinding
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser
    import com.google.firebase.database.DatabaseReference
    import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Addgame.newInstance] factory method to
 * create an instance of this fragment.
 */
class Addgame : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddgameBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseReference: DatabaseReference

    private lateinit var user: FirebaseUser

lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddgameBinding.inflate(inflater, container, false)

        var user1 = FirebaseAuth.getInstance().currentUser!!

        databaseReference = FirebaseDatabase
            .getInstance("https://gamebase1-459ff-default-rtdb.firebaseio.com/")
            .getReference("Games")
            .child(user1.uid)


          binding.stdtb.setOnClickListener(){



              if(binding.editname.text.isNotEmpty()&&binding.edityear.text.isNotEmpty()&&binding.edit.text.isNotEmpty() ) {



              var database=FirebaseDatabase.getInstance().getReference();

              var    name=binding.editname.text.toString();
              var year=binding.edityear.text.toString();
              var type=binding.edit.text.toString();

              var id=database.push().key;
              var played=false
              if(binding.checkBox.isChecked)
                   played=true

              var game=Game(id,name,year,type,played)


              binding.checkBox.isChecked=false





              databaseReference.child(game.id.toString()).setValue(game).addOnCompleteListener(){
                  if(it.isSuccessful)
                  {
                      Toast.makeText(
                          context,
                          "Aded to database",
                          Toast.LENGTH_SHORT
                      ).show()
                  }else{
                      Toast.makeText(
                          context,
                          "Adding failed",
                          Toast.LENGTH_SHORT
                      ).show()
                  }


              }
              } else{
                  Toast.makeText(context, "You have some Empty fields", Toast.LENGTH_SHORT).show()

              }


          }








        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var database=FirebaseDatabase.getInstance().getReference();

        binding.retbut.setOnClickListener(){

            Navigation.findNavController(view).navigate(R.id.action_addgame_to_menuFragment)
        }


//    binding.stdtb.setOnClickListener(){
//
//InsertData()
//
//    }




    }
    fun InsertData(){
        var database=FirebaseDatabase.getInstance().getReference();

        var    name=binding.editname.text.toString();
        var year=binding.edityear.text.toString();
        var type=binding.edit.text.toString();
        var phot=12;
        var id=database.push().key;
        var game=Game1(id,name,year,type,phot)

        if(name.isNotEmpty()&&year.isNotEmpty()&&type.isNotEmpty()){
        database.child("Games").child(id.toString()).setValue(game).addOnCompleteListener(){
            if(it.isSuccessful)
            {
                Toast.makeText(
                    context,
                    "Aded to database",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                    context,
                    "Adding failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }}
        else{
            Toast.makeText(
                context,
                "You have some empty fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Addgame.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Addgame().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}