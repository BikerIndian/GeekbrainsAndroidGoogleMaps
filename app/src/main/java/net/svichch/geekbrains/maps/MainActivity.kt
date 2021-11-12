package net.svichch.geekbrains.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction









class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment1 = MapsFragment.newInstance()

       // ((TextView) frag1.getView().findViewById(R.id.textView))
        var fragment2 = ListPositionFragment.newInstance()

        navigateTo(fragment1)

        val frag1 = supportFragmentManager.findFragmentById(R.id.container)

        frag1?.view?.findViewById<Button>(R.id.btn_list_position)?.setOnClickListener{
            println("----------------------->")
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment2)
                .commitNow()
        }
    }

    fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.container, fragment)
            .addToBackStack("notes")
            .commit()
    }


}