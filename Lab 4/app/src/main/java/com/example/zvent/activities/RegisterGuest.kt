package com.example.zvent.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

import com.example.zvent.R
import com.example.zvent.databinding.FragmentRegisterGuestBinding
import com.example.zvent.models.Guest
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register_guest.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterGuest : Fragment() {
    // Guests that are invited
    private val guestList: MutableList<Guest> = mutableListOf(
        Guest(name = "Luis Perez", phone = "4562-8641", email = "lp@gmail.com"),
        Guest(name = "Scott Swango", phone = "6589-1245", email = "Scott@gmail.com"),
        Guest(name = "Mario Hernandez", phone = "4562-4658", email = "mhernandez@gmail.com"),
        Guest(name = "Amber Rodriguez", phone = "7891-5461", email = "ambeer@gmail.com"),
        Guest(name = "Markus Florido", phone = "8899-4568", email = "floridom@gmail.com"),
        Guest(name = "Angel Ortega", phone = "4632-1247", email = "Yung@gmail.com"),
        Guest(name = "Duncan Charl", phone = "2352-9635", email = "duncanx1@gmail.com"),
        Guest(name = "Steve Flores", phone = "7591-4597", email = "steve@gmail.com"),
        Guest(name = "Julio Garrido", phone = "3337-1567", email = "notjulio@gmail.com"),
        Guest(name = "Cristian Escobar", phone = "9847-7548", email = "escoba@gmail.com")
    )

    // Data binding
    private lateinit var binding: FragmentRegisterGuestBinding
    // Guests registered and total amount of guests
    private var guestNumber: Int = 1
    private var registered: Int = 0

    /**
     * Builds the fragment
     * @param inflater reads XML file
     * @param container that contains the fragment
     * @param savedInstanceState saved instances of the fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Clears previous subtitle
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""

        // Clears variables
        guestNumber = 1
        registered = 0

        // Data binding initialization
        binding = DataBindingUtil.inflate<FragmentRegisterGuestBinding>(inflater,
            R.layout.fragment_register_guest, container, false)


        setDescription()        // Sets the first guest
        setHasOptionsMenu(true) // Menu contains register options

        // Refresh
        binding.invalidateAll()

        return binding.root
    }

    /**
     * Generates the menu
     * @param menu
     * @param inflater reads XML
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.register_options, menu)
    }

    /**
     * Adds functionality to the different menu options
     * @param item from the menu
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Item clicked
        when(item.itemId) {
            // Register guest
            R.id.check -> {
                guestList[guestNumber - 1].registered = "si"    // Guest is registered
                registered++    // Adds one more registered guest
                guestNumber++   // Adds one to the number of checked guests
                // Displays that guest was registered
                Toast.makeText(view!!.context, "Registrado", Toast.LENGTH_SHORT).show()
                setDescription()    // Changes displayed guest
            }

            // Do not register
            R.id.clear -> {
                guestNumber++   // Adds one to the number of checked guests
                // Displays that the guest was not registered
                Toast.makeText(view!!.context, "No registrado", Toast.LENGTH_SHORT).show()
                setDescription()    // Changes displayed guest
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Changes the guest that's shown on screen
     * Instances Results fragment once all guests have been checked
     */
    private fun setDescription() {
        // Keeps changing until all guests have been checked
        if(guestNumber <= guestList.size) {
            // Updates appbar title
            (activity as AppCompatActivity).supportActionBar?.title = "Registrando ($guestNumber/10)"

            // Changes guest info.
            binding.guestName.text = guestList[guestNumber - 1].name.toString()
            binding.guestPhone.text = guestList[guestNumber - 1].phone.toString()
            binding.guestEmail.text = guestList[guestNumber - 1].email.toString()
        // Once all guests have been checked
        } else {
            // Daves the state of all guests in a string
            var guestString = ""
            for(guest in guestList) {
                if(guest.phone!= guestList[guestList.size -1].phone) {
                    guestString = guestString + guest.name + ": " + guest.registered + ", "
                } else {
                    // A period is added to the last guest instead of a comma
                    guestString = guestString + guest.name + ": " + guest.registered + "."
                }

            }
            // Instances the Result fragment
            view!!.findNavController().navigate(RegisterGuestDirections.
                actionNavRegisterToResults(guestString, registered, guestList.size))
        }
    }
}
