package com.znerfii.hackathon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.ticket.view.*

var x:Int?=null

class MainActivity : AppCompatActivity() {
    var list=ArrayList<listData>()
    var adaptor: ListAdapter?=null
    var kitchen:Int=2
    var hallway:Int?=2
    var bedroom:Int?=2
    var livingroom:Int?=2
    var fan:Int?=2
    var street:Int?=2
    var address:String?="http://192.168.43.154/hackathon"
    var passUser:String?="BajajHacks"
    var passPassword:String?="hackeverything"
    var passId:Int?=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fetch()


    }

    fun fetch(){
        val url = address + "/get.php?id=2"
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show()

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    list.clear()
                    kitchen = response.getString("Kitchen").toInt()
                    hallway = response.getString("Hallway").toInt()
                    bedroom = response.getString("Bedroom").toInt()
                    livingroom = response.getString("Livingroom").toInt()
                    fan = response.getString("Fan").toInt()
                    street = response.getString("Street").toInt()
                    listIt()
                    //  Toast.makeText(this, kitchen.toString(), Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Sorry Check the connection", Toast.LENGTH_SHORT).show()
                })
        var rq:RequestQueue=Volley.newRequestQueue(this)
        rq.add(request)

//        Handler().postDelayed({
//
//
//        }, 5000)

    }

    fun listIt(){
        list.add(listData("Kitchen",kitchen))
        list.add(listData("Hallway",hallway))
        list.add(listData("Bedroom",bedroom))
        list.add(listData("Livingroom",livingroom))
        list.add(listData("fan",fan))
        list.add(listData("street",street))

        adaptor=ListAdapter(this,list)

        itemList.adapter = adaptor
    }



    fun convert(context: Context,  prevalue:Int?, place:String?){

        var url:String=""
        if (prevalue==0){
            url=address+"/set.php?name="+place+"&status=1&username="+passUser+"&password="+passPassword+"&id="+passId
        }else if (prevalue==1){
            url=address+"/set.php?name="+place+"&status=0&username="+passUser+"&password="+passPassword+"&id="+passId
        }else{
            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
        }
        Fuel.get(url).responseString { _, _, result ->
            result.fold({ _ ->
                Toast.makeText(context, "Changed", Toast.LENGTH_SHORT).show()
            }, { _ ->
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            })
        }

//        Handler().postDelayed({
//
//
//        }, 1000)
        list.clear()
        fetch()
    }

    inner class ListAdapter: BaseAdapter {
        var list=ArrayList<listData>()
        var context:Context?=null

        constructor(context: Context, list:ArrayList<listData>):super(){
            this.list=list
            this.context=context
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val data = list[p0]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var myView= inflater.inflate(R.layout.ticket,null)
            myView.PlaceText.text=data.place
            if(data.status==0){
                myView.StatusButton.text="Off"
            }else if(data.status ==1){
                myView.StatusButton.text="On"
            }else if(data.status == 2){
                myView.StatusButton.text="Checking"
            }

            myView.StatusButton.setOnClickListener{
                convert(this@MainActivity,data.status,data.place)
            }

            return myView
        }

        override fun getItem(p0: Int): Any {
            return list[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }


    }

}


