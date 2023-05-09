package com.example.gamebase

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MyAdapter(private val list: ArrayList<Game>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


private lateinit var dialoginflater: LayoutInflater
            private lateinit var databaseReference: DatabaseReference

private lateinit var builder: AlertDialog.Builder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {












        val inflater:LayoutInflater=LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.game_row,parent,false)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_row, parent, false)

        return MyViewHolder(view)
    }
 public interface OnItemClickListener
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val game = list[holder.adapterPosition]
//        holder.name.text = game.getName1()
//        holder.year.text = game.getYear1()
//        holder.type.text = game.getType1()
//        holder.played.text = game.isPlayed1().toString()
        var user1 = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase

            .getInstance("https://gamebase1-459ff-default-rtdb.firebaseio.com/")

            .getReference("Games")

            .child(user1.uid)



        holder.name.text = game.name
        holder.year.text = game.year
        holder.type.text = game.type
       if(game.played==true){
          holder.played.text = "true"}
       else{
           holder.played.text = "false"}
         holder.editbutton.setOnClickListener(){

showDialog(holder.editbutton.context,game.name.toString(),game.year.toString(),game.type.toString(),holder.played.text.toString(),game)




         }
        holder.delbutton.setOnClickListener(){

builder=AlertDialog.Builder(holder.delbutton.context)
builder.setTitle("Confiramtion")
    .setMessage("Are you sure that you want do delete this record from databse?")
    .setCancelable(true)
    .setPositiveButton("Yes"){DialogInterface,it->
        list.clear()
        databaseReference.child(game.id.toString()).removeValue()
    }
    .setNegativeButton("No"){
        DialogInterface,it->DialogInterface.cancel()
    }
    .show()

          //        databaseReference.child(game.name.toString()).removeValue()
                 


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var name: TextView
        var year: TextView
        var type: TextView
        var played: TextView
        var delbutton=itemView.findViewById<ImageButton>(R.id.imageButton)
        var editbutton=itemView.findViewById<ImageButton>(R.id.editbut)
        init {
            name = itemView.findViewById(R.id.gamename)
            year = itemView.findViewById(R.id.yearproduction)
            type = itemView.findViewById(R.id.typeofgame)
            played = itemView.findViewById(R.id.textView6)
        }
    }


    private fun showDialog(
        con: Context,
        namegame: String,
        yeargame: String,
        typegame: String,
        played: String,
        game: Game
    ) {
        val dialog = Dialog(con)

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.editing_card)

val name=dialog.findViewById<EditText>(R.id.titleInput)
        val year=dialog.findViewById<EditText>(R.id.titleInput2)
        val type=dialog.findViewById<EditText>(R.id.titleInput3)
        val check=dialog.findViewById<CheckBox>(R.id.checkBox2)
        name.setText(namegame)
        year.setText(yeargame)
        type.setText(typegame)
        if(played=="true")
            check.isChecked=true


        val save=dialog.findViewById<Button>(R.id.savebut)
        val noBtn = dialog.findViewById<Button>(R.id.cancelButton)
        save.setOnClickListener {

            list.clear()
            var game2=Game(game.id.toString(),name.text.toString(),year.text.toString(),type.text.toString(),check.isChecked)
            databaseReference.child(game.id.toString()).setValue(game2)



            Toast.makeText(con, "Changed", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}
