package com.example.gamebase

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.gamebase.databinding.FragmentAddgameBinding
import com.example.gamebase.databinding.FragmentListGameBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListGame : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isplayed= false;

   lateinit var rv:RecyclerView;
    lateinit var arrayList:ArrayList<Game>;
    lateinit var myadapter:MyAdapter;
    lateinit var dbref:DatabaseReference

    private var _binding: FragmentListGameBinding? = null
    private val binding get() = _binding!!
    private var startdate="0000"
    private var lastdate="2023"
    private var typegame=""
    private var searchname=""
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
        _binding = FragmentListGameBinding.inflate(inflater, container, false)
       // return inflater.inflate(R.layout.fragment_list_game, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



           binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
               androidx.appcompat.widget.SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(p0: String?): Boolean {
                  return false
               }

               override fun onQueryTextChange(p0: String?): Boolean {


                  searchname=p0.toString()
                   getGamedata()
                   return true
               }


           }

           )




        binding.imageButton4.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_listGame_to_menuFragment)





        }

        binding.settingsButton.setOnClickListener(){

            var dialog = Dialog(binding.settingsButton.context)

            dialog.setCancelable(false)
            dialog.setContentView(R.layout.filter_dialog)
            var sbut=dialog.findViewById<MaterialButton>(R.id.savebut)
            var cancelbut=dialog.findViewById<MaterialButton>(R.id.cancelButton)
var check=dialog.findViewById<CheckBox>(R.id.checkBox3)
            var ld=dialog.findViewById<EditText>(R.id.editTextTextPersonName3)
            var sd=dialog.findViewById<EditText>(R.id.editTextTextPersonName2)

            sd.setText(startdate)
            ld.setText(lastdate)


check.isChecked=isplayed
            sbut.setOnClickListener(){

             isplayed=check.isChecked
                lastdate=ld.text.toString()
                startdate=sd.text.toString()


                getGamedata()
          dialog.dismiss()
            }


            cancelbut.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        rv=view.findViewById(R.id.recycleview)
        rv.layoutManager=LinearLayoutManager(this.context)
        rv.setHasFixedSize(true)
        arrayList= arrayListOf<Game>()
        getGamedata()

    }

    private fun getGamedata() {
arrayList.clear()
        var user1 = FirebaseAuth.getInstance().currentUser!!
   dbref=FirebaseDatabase
       .getInstance("https://gamebase1-459ff-default-rtdb.firebaseio.com/")
       .getReference("Games")
       .child(user1.uid)

        dbref.addValueEventListener(object :ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for (gamesnapshot in snapshot.children){
                        val game=gamesnapshot.getValue(Game::class.java)
                       if(game!!.played==isplayed) {


                           if(game!!.year!!.toInt()<=lastdate.toInt() && game!!.year!!.toInt()>=startdate.toInt() ){

                               if(searchname!=""){
                               if(game!!.name!!.contains( searchname )){
                                        arrayList.add(game!!)}
                                }
                               else{
                                   arrayList.add(game!!)
                               }


                       }
                    }}
                    rv.adapter=MyAdapter(arrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListGame.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListGame().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}




